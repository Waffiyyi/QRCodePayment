package com.example.demo.controller;


import com.example.demo.service.PaymentService;
import com.example.demo.service.QRCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.time.LocalDateTime;



@RestController
@RequestMapping("/api/qrcode")
@RequiredArgsConstructor
    public class QRCodeController {
     private final QRCodeService qrCodeService;
     private final PaymentService paymentService;


    @PostMapping("/generate")
    public ResponseEntity<?> generateBaseQRCode(@RequestParam String qrCodeData) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCodeService.generateBaseQRCode(qrCodeData));
    }

    @PostMapping("/generate-sub")
    public ResponseEntity<?> generateSubQRCode(@RequestParam String qrCodeData, @RequestParam Long baseQRCodeId, @RequestParam LocalDateTime expirationDate) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCodeService.generateSubQRCode(qrCodeData, baseQRCodeId, expirationDate));
    }

    @PostMapping("/scan-pay")
    public ResponseEntity<?> scanAndPayWithQRCode(@RequestParam String qrCodeData, @RequestParam Double amount, @RequestParam String transactionPin) {
        return paymentService.scanAndPayWithQRCode(qrCodeData, amount, transactionPin);
    }

    @PostMapping("/sub-qr-pay")
    public ResponseEntity<?> generateSubQRCodePayment(@RequestParam Long subQRCodeId, @RequestParam Double amount, @RequestParam String transactionPin) {
        return paymentService.generateSubQRCodePayment(subQRCodeId, amount, transactionPin);
    }

    @PostMapping("/enable")
    public ResponseEntity<?> enableQRCode(@RequestParam Long qrCodeId) {
        qrCodeService.enableQRCode(qrCodeId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/disable")
    public ResponseEntity<?> disableQRCode(@RequestParam Long qrCodeId) {
        qrCodeService.disableQRCode(qrCodeId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/set-expiration")
    public ResponseEntity<?> setQRCodeExpiration(@RequestParam Long qrCodeId, @RequestParam LocalDateTime expirationDate) {
        qrCodeService.setQRCodeExpiration(qrCodeId, expirationDate);
        return ResponseEntity.ok().build();
    }
}

