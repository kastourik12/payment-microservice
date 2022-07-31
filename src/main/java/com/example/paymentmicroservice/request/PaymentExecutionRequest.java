package com.example.paymentmicroservice.request;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaymentExecutionRequest {
    String PayerID;
    String paymentId;
}
