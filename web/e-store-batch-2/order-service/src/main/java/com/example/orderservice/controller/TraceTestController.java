package com.example.orderservice.controller;

import com.example.orderservice.exception.ServiceException;
import com.example.orderservice.service.external.UserServiceClient;
import com.example.orderservice.service.external.UserServiceClientCloud;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController()
@RequestMapping("/trace")
@Slf4j
@AllArgsConstructor
public class TraceTestController {


    private final PaymentService paymentService;
    private final CartService cartService;
    private final UserServiceClient userServiceClient;


    @GetMapping()
    public void index() throws ServiceException {
        log.debug("calling index method.");
        this.paymentService.index();
        userServiceClient.getUserByName("mafei");
    }


   /* @GetMapping()
    public void index() {
        log.debug("calling index method.");
        this.paymentService.index();
        this.cartService.index();
    }*/
}

@Component
@Slf4j
class PaymentService {

    @NewSpan("PaymentService:index")
    public void index() {
        log.debug("PaymentService.index");
    }
}

@Component
@Slf4j
@AllArgsConstructor
class CartService {

    private final DeliveryService deliveryService;

    @NewSpan("CartService:index")
    public void index() {
        log.debug("CartService.index");
        this.deliveryService.index();
    }
}

@Slf4j
@Component
@AllArgsConstructor
class DeliveryService {
    private final Tracer tracer;

    //    @NewSpan("DeliveryService:index")
    public void index() {
        log.debug("DeliveryService.index");

        Span newSpan = this.tracer.nextSpan().name("calculateTax");
        try (Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {
            // ...
            // You can tag a span
            newSpan.tag("name", "mafei");
            newSpan.tag("date", LocalDateTime.now().toString());
            // ...
            // You can log an event on a span
            newSpan.event("taxCalculated");
        } finally {
            // Once done remember to end the span. This will allow collecting
            // the span to send it to a distributed tracing system e.g. Zipkin
            newSpan.end();
        }

    }
}



