package com.example.bookapp.controller.rest;

import com.example.bookapp.models.Cart;
import com.example.bookapp.repo.CartRepository;
import com.example.bookapp.service.CartService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PaymentController {
    
    @Autowired
    CartRepository cartRepository;
    
    @Value("${DOMAIN_BASE_URL:https://sk-book-app.herokuapp.com/}")
    private String domainBaseUrl;
    
    @Autowired
    private CartService cartService;
    
    public PaymentController() throws StripeException {
        Stripe.apiKey = "sk_test_51ICfNaCrEm87RQhZA4RNP1mQttH6iDlVc1RA7T90kAF5RcxkrZelGJFGqHiSGtBtw3xl8qabcBk6jRXZ6Io5lMQM00j6SkwZIk";
    }
    
    @PostMapping("/create-checkout-session")
    public Map createCheckoutSession(HttpSession session) throws StripeException {
        
        Optional<Cart> optionalCart = Optional.ofNullable(cartRepository.findBySessionIdAndStatus(session.getId(), 0));
        
        if (optionalCart.isPresent()) {
            final Cart cart = optionalCart.get();
            final long totalCost = cartRepository.findCartTotalByCartId(cart.getId());
            
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl(domainBaseUrl + "/success")
                            .setCancelUrl(domainBaseUrl + "/cancel")
                            
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setQuantity(1L)
                                            .setPriceData(
                                                    SessionCreateParams.LineItem.PriceData.builder()
                                                            .setCurrency("inr")
                                                            .setUnitAmount(totalCost * 100)
                                                            .setProductData(
                                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                            .setName("Book App")
                                                                            .build())
                                                            .build())
                                            .build())
                            .build();
            Session newSession = Session.create(params);
            HashMap<String, String> responseData = new HashMap<String, String>();
            responseData.put("id", newSession.getId());
            return responseData;
        }
        return Collections.emptyMap();
    }
    
    
    @PostMapping("/payment-webhook")
    public void paymentHook(@RequestBody String data, HttpSession session) {
        Stripe.apiKey = "sk_test_51ICfNaCrEm87RQhZA4RNP1mQttH6iDlVc1RA7T90kAF5RcxkrZelGJFGqHiSGtBtw3xl8qabcBk6jRXZ6Io5lMQM00j6SkwZIk";
        System.out.println("Payment successful");
        System.out.println(">>> ");
        System.out.println(data);
        System.out.println("<<< ");
        
        
        // TODO: parse data and get token
        String token = UUID.randomUUID().toString();
        Cart cart = cartRepository.findBySessionIdAndStatus(session.getId(), 0);
        cart.setToken(token);
        
        cartService.completeCheckout(cart);
    }
    
}