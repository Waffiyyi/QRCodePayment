package com.example.demo.repository;

import com.example.demo.models.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QrCodeRepository extends JpaRepository<QRCode, Long> {
    Optional<QRCode> findByQrCodeDataAndId(String qrCodeData, Long id);
}
