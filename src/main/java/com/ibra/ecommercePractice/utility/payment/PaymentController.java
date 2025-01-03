package com.ibra.ecommercePractice.utility.payment;

import com.ibra.ecommercePractice.dto.Response;
import com.ibra.ecommercePractice.enums.OrderStatus;
import com.ibra.ecommercePractice.model.User;
import com.ibra.ecommercePractice.service.interf.OrderService;
import com.ibra.ecommercePractice.service.interf.UserService;
import com.ibra.ecommercePractice.model.Order;
import com.ibra.ecommercePractice.utility.payment.stripe.StripeService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class PaymentController {


    private final APIContext apiContext;

    private final UserService userService;

    private final OrderService orderService;

    private final StripeService stripeService;

    private Order order;

    @Autowired
    public PaymentController(APIContext apiContext, UserService userService, OrderService orderService, StripeService stripeService) {
        this.apiContext = apiContext;
        this.userService = userService;
        this.orderService = orderService;
        this.stripeService = stripeService;
    }

    @GetMapping("/home")
    public String home() {
        return "index.html";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/pay")
    public ResponseEntity<Response> payment(Model model) {
        User user = userService.getLoginUser(); // Get the user that is signed in from the authentication token


        //user has to be logged in
        if (user != null) {
            List<Order> listOfOrders = user.getOrderList(); // Get the user's order list
            Optional<Order> orderOpt = listOfOrders.stream().findAny(); //to be improved since in a async mode, he could put on diff orders using different payment system

            if (orderOpt.isPresent()) {
                order = orderOpt.get();
//                System.out.print(order.getId());
                BigDecimal amount = order.getTotalPrice(); //if order exist, give us the total price for the order
//                System.out.println("amount....................................." + amount);
                String method = order.getPayMethod(); //also give us the payment method
//                System.out.println("method....................................." + method);


                if ("paypal".equalsIgnoreCase(method)) {
                    return handlePayPalPayment(amount);
//                    return ResponseEntity.status(HttpStatus.OK).body(Response.builder().paymentMethod(method).build());
                } else if ("stripe".equalsIgnoreCase(method)) {
                    return processStripePayment(amount);
                }
            }
//            return "redirect:/";
            return ResponseEntity.status(HttpStatus.OK).body(Response.builder().paymentMethod("NO payment method found").build());

        }

//        return "redirect:/login";
        return ResponseEntity.status(HttpStatus.OK).body(Response.builder().paymentMethod("NO login user found").build());

    }


    private ResponseEntity<Response> handlePayPalPayment(BigDecimal amount) {
        Amount amountDetails = new Amount();
        amountDetails.setCurrency("USD");
        amountDetails.setTotal(String.format("%.2f", amount));

        Transaction transaction = new Transaction();
        transaction.setDescription("Payment description");
        transaction.setAmount(amountDetails);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:2424/cancel");
        redirectUrls.setReturnUrl("http://localhost:2424/success");

        payment.setRedirectUrls(redirectUrls);

        try {
            Payment createdPayment = payment.create(apiContext);
            for (Links link : createdPayment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    System.out.println(link.getHref());
                    //the code stops running here so I guess I have to improvise to get it to update the status
                    //usually it should be after
                    orderService.updateOrderStatus(order.getId(), OrderStatus.CONFIRMED);
//                    return "redirect:" + link.getHref();
                    return ResponseEntity.status(HttpStatus.OK).body(Response.builder().paymentUrl(link.getHref()).build());
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }

//        order.setStatus(OrderStatus.CONFIRMED);
//
//        System.out.println("it didn't get here ........... " + order.getStatus());

//        return "redirect:/";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.builder().paymentUrl("No url found").build());
    }


    private ResponseEntity<Response> processStripePayment(BigDecimal amount) {
        try {
            Session session = stripeService.createCheckoutSession(amount);
            // Return the checkout URL directly
            System.out.println(session.getUrl());
            return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                    .paymentUrl(session.getUrl())
                    .build()
            );

        } catch (StripeException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.builder().paymentUrl("No url found").build());
    }

    @GetMapping("/success")
    public String success(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, Model model) {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        try {
            Payment executedPayment = payment.execute(apiContext, paymentExecution);
            model.addAttribute("payment", executedPayment);
            return "success.html";
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }

        return "redirect:/home";
    }

    @GetMapping("/cancel")
    public String cancel() {
        return "cancel.html";
    }
}