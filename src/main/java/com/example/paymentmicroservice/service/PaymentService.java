package com.example.paymentmicroservice.service;

import com.example.paymentmicroservice.dto.PaymentDTO;
import com.example.paymentmicroservice.model.Payment;
import com.example.paymentmicroservice.repository.PaymentRepository;
import com.example.paymentmicroservice.request.PayPalPaymentRequest;
import com.example.paymentmicroservice.request.PaymentExecutionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class PaymentService {
    @Value("${go.api.host}")
    private String apiHost;
    @Value("${go.api.port}")
    private String apiPort;
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;
    public ResponseEntity<?> createPayment(PayPalPaymentRequest request) {

        String response = restTemplate.postForEntity(
                "http://"+apiHost+":"+apiPort+"/v1/paypal/create",
                request,
                String.class).getBody();
        return ResponseEntity.ok(response);

    }
    public ResponseEntity<?> executePayment(String paymentId,String PayerID){
        PaymentExecutionRequest request = new PaymentExecutionRequest(PayerID,paymentId);

        PaymentDTO paymentResponse = restTemplate.getForObject("http://"+apiHost+":"+apiPort+"/v1/paypal/execute?paymentId="+paymentId+"&PayerID="+PayerID,
                PaymentDTO.class,request);
        Payment payment = new Payment();
        payment.setPaymentId(paymentResponse.getPaymentId());
        payment.setPayerId(paymentResponse.getPayerId());
        payment.setAmount(paymentResponse.getAmount());
        this.paymentRepository.save(payment);
        return ResponseEntity.ok("succes");
    }

}
