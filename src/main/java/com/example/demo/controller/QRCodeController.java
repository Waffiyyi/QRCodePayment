package com.example.demo.controller;


import com.example.demo.dto.ExpirationDto;
import com.example.demo.models.Product;
import com.example.demo.models.ProductPayment;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.time.LocalDateTime;



@RestController
@RequiredArgsConstructor
    @RequestMapping("/qr")
    public class QRCodeController {

    private final ProductService productService;
    private final ProductRepository productRepository;


    @GetMapping("/generate-sub-qr/{productId}")
    public ResponseEntity<byte[]> generateSubQRCode(@PathVariable long productId) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        ProductPayment productPayment = product.getProductPayment();
        byte[] subQRCode = productService.generateSubQRCode(productPayment, 200, 200);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(subQRCode);
    }

    @PostMapping("/qr-code/{qrCodeId}/enable")
    public ResponseEntity<String> enableQrCode(@PathVariable long qrCodeId) {
        productService.enableQrCode(qrCodeId);
        return ResponseEntity.ok("QR Code enabled successfully");
    }

    @PostMapping("/qr-code/{qrCodeId}/disable")
    public ResponseEntity<String> disableQrCode(@PathVariable long qrCodeId) {
        productService.disableQrCode(qrCodeId);
        return ResponseEntity.ok("QR Code disabled successfully");
    }

    @PostMapping("/qr-code/{qrCodeId}/expiration")
    public ResponseEntity<String> setExpirationDate(@PathVariable long qrCodeId, @RequestBody ExpirationDto expirationDate) {
        productService.setExpirationDate(qrCodeId, expirationDate);
        return ResponseEntity.ok("Expiration date set successfully");
    }

}

