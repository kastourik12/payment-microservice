package com.example.paymentmicroservice.controller;

import com.example.paymentmicroservice.request.PayPalPaymentRequest;
import com.example.paymentmicroservice.service.PaymentService;
import lombok.RequiredArgsConstructor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment/")
@RequiredArgsConstructor

public class PaymentController {
    private Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;
    @PostMapping("create")
    public ResponseEntity<?> createPayment(@RequestBody PayPalPaymentRequest request) {
        logger.debug(request.toString());
        return this.paymentService.createPayment(request);
    }
    @GetMapping("execute")
    public ResponseEntity<?> executePayment(@RequestParam String paymentId, @RequestParam String PayerID){

        return this.paymentService.executePayment(paymentId,PayerID);
    }

}
