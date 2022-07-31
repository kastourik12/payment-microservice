package com.example.paymentmicroservice.dto;

import lombok.Data;

@Data
public class PaymentDTO {
  private String paymentId ;
  private String payerId ;
  private String Amount;
  private String currency;
}
