package com.example.demo.service;

import com.example.demo.DTOs.InitializeTransactionDTO;
import org.springframework.http.ResponseEntity;


public interface PaymentService {
    ResponseEntity<Object> initializeTransaction(InitializeTransactionDTO transactionDTO, Long qrCodeId);

    ResponseEntity<Object> verifyTransaction(String email);

    ResponseEntity<Object> scanAndPayWithQRCode(String qrCodeData, Long qrCodeId, Double amount, String transactionPin);
    ResponseEntity<Object> generateSubQRCodePayment(Long subQRCodeId, Double amount, String transactionPin);




}
