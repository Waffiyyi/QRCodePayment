package com.example.demo.controller;

import com.example.demo.dto.InitializeTransactionDTO;
import com.example.demo.service.PaymentService;
import com.example.demo.service.serviceImpl.PaymentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final PaymentService paymentService;
    private final PaymentServiceImpl paymentServiceImpl;

    @PostMapping("/initialize-transaction")
    public ResponseEntity<Object> initializeTransaction(@RequestParam("qrCodeImage") String base64QRCode) {
        Map<String, String> qrCodeInfo = extractInfoFromQRCode(base64QRCode);

        if (qrCodeInfo == null || qrCodeInfo.isEmpty()) {
            return new ResponseEntity<>("Failed to extract information from QR code", HttpStatus.BAD_REQUEST);
        }

        InitializeTransactionDTO transactionDTO = InitializeTransactionDTO.builder()
                .amount(qrCodeInfo.get("amount"))
                .email(qrCodeInfo.get("email"))
                .build();

        return paymentService.initializeTransaction(transactionDTO);
    }

    private Map<String, String> extractInfoFromQRCode(String base64QRCode) {
        try {
            byte[] decodedBytes = DatatypeConverter.parseBase64Binary(base64QRCode);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBytes);
            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);

            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            hints.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);

            String qrCodeData = result.getText();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(qrCodeData, Map.class);
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/verify-transaction")
    public ResponseEntity<Object> verifyTransaction(@RequestParam String trxref) {
        return paymentService.verifyTransaction(trxref);
    }
}