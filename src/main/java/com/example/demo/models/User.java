package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String email;

    // Add any other relevant fields and annotations here

//    @OneToMany(mappedBy = "userData")
//    private List<ProductPayment> productPayments;
}
