package com.example.demo.service;

import com.example.demo.models.QRCode;
import com.example.demo.models.SubQRCode;

import java.io.IOException;
import java.time.LocalDateTime;

public interface QRCodeService {
    byte[] generateBaseQRCode(String qrCodeData) throws IOException;
    byte[] generateSubQRCode(String qrCodeData, Long baseQRCodeId, LocalDateTime expirationDate) throws IOException;
    void enableQRCode(Long qrCodeId);
    void disableQRCode(Long qrCodeId);

    void setQRCodeExpiration(Long qrCodeId, LocalDateTime expirationDate);
    QRCode getQRCode(Long qrCodeId);
}
