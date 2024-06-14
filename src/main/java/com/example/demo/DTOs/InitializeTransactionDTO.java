package com.example.demo.DTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;



@Getter
@Setter
public class InitializeTransactionDTO {
    private String amount;
    private String email;
    private String currency;
    private String reference;
    private List<String> channels;
}
