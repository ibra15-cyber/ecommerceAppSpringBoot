package com.ibra.ecommercePractice.utility.payment.paypal;


import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfig {


    @Value("${paypal.client_id}")
    private String clientId;

    @Value("${paypal.client_secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;



//    @Autowired
//    public PayPalConfig(String clientId, String clientSecret, String mode) {
//        this.clientId = clientId;
//        this.clientSecret = clientSecret;
//        this.mode = mode;
//    }


//    @Bean
//    public Map<String, String> paypalSdkConfig() {
//        Map<String, String> configMap = new HashMap<>();
//        configMap.put("mode", "${paypal.mode}");
//        return configMap;
//    }
//
//    @Bean
//    public OAuthTokenCredential oAuthTokenCredential() {
//        return new OAuthTokenCredential(
//                "${paypal.client-id}",
//                "${paypal.client-secret}",
//                paypalSdkConfig()
//        );
//    }

    @Bean
    public APIContext apiContext() {
        return new APIContext(clientId, clientSecret, mode);
    }

//    @PostConstruct
//    public void init() {
//        System.out.println("PayPal Config Loaded: " + clientId + ", " + clientSecret + ", " + mode);
//    }

}
