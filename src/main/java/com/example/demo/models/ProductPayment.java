package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class ProductPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String amount;
    private String currency;
    private String reference;
    @Column(length = 1000)
    @ElementCollection
    private List<String> channels;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userData;

}
