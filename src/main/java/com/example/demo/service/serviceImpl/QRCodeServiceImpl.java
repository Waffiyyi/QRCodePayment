package com.example.demo.service.serviceImpl;

import com.example.demo.models.QRCode;
import com.example.demo.models.SubQRCode;
import com.example.demo.models.User;
import com.example.demo.repository.QrCodeRepository;
import com.example.demo.repository.SubQRCodeRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.QRCodeService;
import com.example.demo.utils.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QRCodeServiceImpl implements QRCodeService {
    private final QrCodeRepository qrCodeRepository;
    private final SubQRCodeRepository subQRCodeRepository;
    private final UserRepository userRepository;
@Override
    public byte[] generateBaseQRCode(String qrCodeData) throws IOException {
    User currentUser; // = your microservice API
    User user = new User();
    userRepository.save(user); //You can then pass in currentUser here
        QRCode qrCode = new QRCode();
        qrCode.setQrCodeData(qrCodeData);
        qrCode.setUser(user);
        qrCode.setExpirationDate(null); // No expiration for base QR codes

        byte[] qrCodeImage = QRCodeGenerator.generateQRCodeImage(qrCodeData, 250, 250);
        qrCode.setQrCodeImage(qrCodeImage);

       qrCodeRepository.save(qrCode);
    return qrCodeImage;
}
@Override
    public byte[] generateSubQRCode(String qrCodeData, Long baseQRCodeId, LocalDateTime expirationDate) throws IOException {
        QRCode baseQRCode = qrCodeRepository.findById(baseQRCodeId).orElseThrow(() -> new IllegalArgumentException("Base QR Code not found"));

        SubQRCode subQRCode = new SubQRCode();
        subQRCode.setCode(qrCodeData);
        subQRCode.setBaseQRCode(baseQRCode);
        subQRCode.setExpirationDate(expirationDate);
        subQRCode.setEnabled(true);
        subQRCode.setExpired(false);

        byte[] qrCodeImage = QRCodeGenerator.generateQRCodeImage(qrCodeData, 250, 250);
        subQRCode.setQrCodeImage(qrCodeImage);

        subQRCodeRepository.save(subQRCode);
    return qrCodeImage;
}
@Override
    public void enableQRCode(Long qrCodeId) {
        QRCode qrCode = qrCodeRepository.findById(qrCodeId).orElseThrow(() -> new IllegalArgumentException("QR Code not found"));
        qrCode.setEnabled(true);
        qrCodeRepository.save(qrCode);
    }
@Override
    public void disableQRCode(Long qrCodeId) {
        QRCode qrCode = qrCodeRepository.findById(qrCodeId).orElseThrow(() -> new IllegalArgumentException("QR Code not found"));
        qrCode.setEnabled(false);
        qrCodeRepository.save(qrCode);
    }
@Override
    public void setSubQRCodeExpiration(Long subQrCodeId, LocalDateTime expirationDate) {
        SubQRCode qrCode = subQRCodeRepository.findById(subQrCodeId).orElseThrow(() -> new IllegalArgumentException("QR Code not found"));
        qrCode.setExpirationDate(expirationDate);
        subQRCodeRepository.save(qrCode);
    }
@Override
    public QRCode getQRCode(Long qrCodeId) {
        return qrCodeRepository.findById(qrCodeId).orElseThrow(() -> new IllegalArgumentException("QR Code not found"));
    }
}
