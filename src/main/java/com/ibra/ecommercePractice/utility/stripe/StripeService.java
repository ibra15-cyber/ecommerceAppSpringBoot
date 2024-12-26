package com.ibra.ecommercePractice.utility.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripeService {

    @Value("${stripe.api.key}")
    private String apiKey;

    public Session createCheckoutSession(BigDecimal amount) throws StripeException {
        Stripe.apiKey = apiKey;

        // Define the product and price details for the checkout session
        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName("Product description")
                .build();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount(amount.multiply(BigDecimal.valueOf(100)).longValue()) // Stripe expects amount in cents
                .setProductData(productData)
                .build();

        // Define the line item for the checkout session
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setPriceData(priceData)
                .setQuantity(1L)
                .build();

        // Create the checkout session with the specified parameters
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(lineItem)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:2424/success")
                .setCancelUrl("http://localhost:2424/cancel")
                .build();

        return Session.create(params);
    }
}