package com.example.orderservice.controller;

import com.example.orderservice.service.external.PaymentClientService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class TestController {

//    private final RestTemplate restTemplate;
    private final PaymentClientService paymentClientService;

    @GetMapping("/test")
    public Object getDate(HttpServletRequest request) {

        return Data.builder()
                .port(request.getServerPort())
                .time(LocalDateTime.now())
                .build();
    }


    @PostMapping("/place")
    public Object placeOrder(HttpServletRequest request, HttpServletResponse response) {
        //do the process with service
        response.setHeader("X-value", "1");
        //Make the payment.
        {

            /*Map<String, Object> data = new HashMap<>();
            ResponseEntity<Object> entity =
                    this.restTemplate.postForEntity("http://payment-service/payment/make-payment", data, Object.class);
            Object body = entity.getBody();
            System.out.println("body : " + body);*/
        }

        {
            Object makePaymentResponse = this.paymentClientService.makePayment();
            System.out.println("body : " + makePaymentResponse);
        }
        return PlceOrderResponse.builder()
                .port(request.getServerPort())
                .time(LocalDateTime.now())
                .message("Your order has been placed successfully. " + request.getHeader("X-Gateway-In"))
                .orderId(UUID.randomUUID().toString())
                .build();
    }


    @Getter
    @Setter
    @Builder
    public static class Data {
        private Object port;
        private LocalDateTime time;
    }

    @Getter
    @Setter
    @Builder
    public static class PlceOrderResponse {
        private Object port;
        private String message;
        private String orderId;
        private LocalDateTime time;
    }
}
