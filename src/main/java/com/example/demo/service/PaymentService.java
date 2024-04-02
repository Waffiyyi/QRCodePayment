package com.example.demo.service;

import com.example.demo.dto.InitializeTransactionDTO;
import org.springframework.http.ResponseEntity;


public interface PaymentService {
    ResponseEntity<Object> initializeTransaction(InitializeTransactionDTO transactionDTO);

    ResponseEntity<Object> verifyTransaction(String email);





}
