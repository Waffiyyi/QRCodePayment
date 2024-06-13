package com.example.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class SubQRCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private LocalDateTime expirationDate;
    private boolean enabled;
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "base_qr_code_id")
    private QrCode baseQRCode;

    @OneToMany(mappedBy = "subQRCode")
    private List<Transaction> transactions;
}
