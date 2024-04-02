package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class QrCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String qrCodeData;
    private boolean enabled = true;
    private LocalDateTime expirationDate;
    @OneToOne(mappedBy = "baseQRCode")
    private Product product;

}