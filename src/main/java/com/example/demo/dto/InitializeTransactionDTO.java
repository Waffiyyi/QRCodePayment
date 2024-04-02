package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InitializeTransactionDTO {
    private String amount;
    private String email;
    private String currency;
    private String reference;
    private List<String> channels;
}
