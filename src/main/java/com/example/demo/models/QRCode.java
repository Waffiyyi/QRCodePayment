package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class QRCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String qrCodeData;
    private byte[] qrCodeImage;
    private boolean enabled = true;
    private LocalDateTime expirationDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "baseQRCode")
    private List<SubQRCode> subQRCodes;

    @OneToMany(mappedBy = "qrCode")
    private List<Transaction> transactions;

}