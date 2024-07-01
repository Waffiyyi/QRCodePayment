package com.example.demo.models;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data

public class SubQRCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private byte[] qrCodeImage;
    private LocalDateTime expirationDate;
    private boolean enabled;
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "base_qr_code_id")
    private QRCode baseQRCode;

    @OneToMany(mappedBy = "subQRCode")
    private List<Transaction> transactions;
}
