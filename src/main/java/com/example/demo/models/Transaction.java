package com.example.demo.models;


import com.example.demo.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private String reference;
    private Date createdAt;
    private Date updatedAt;

    @ManyToOne
    private User user;
}
