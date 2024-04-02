package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String productName;
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductPayment productPayment;

    @OneToOne
    private QrCode baseQRCode;
}
