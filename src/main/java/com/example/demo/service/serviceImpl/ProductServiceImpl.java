package com.example.demo.service.serviceImpl;

import com.example.demo.dto.CreateProductDTO;
import com.example.demo.dto.ExpirationDto;
import com.example.demo.models.Product;
import com.example.demo.models.ProductPayment;
import com.example.demo.models.QrCode;
import com.example.demo.models.User;
import com.example.demo.repository.ProductPaymentRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.QrCodeRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.demo.utils.QRCodeGenerator.generateQRCodeImage;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductPaymentRepository productPaymentRepository;
    private final UserRepository userRepository;
    private final QrCodeRepository qrCodeRepository;

    public byte[] createProductAndPayment(CreateProductDTO createProductDTO, int width, int height) throws IOException {
        //Spring Security should be used here
        User user = new User();
        user.setEmail("fasholawafiyyi@gmail.com");
        userRepository.save(user);

        String reference = UUID.randomUUID().toString();

        // Create product
        Product product = new Product();
        product.setProductName(createProductDTO.getProduct().getProductName());
        productRepository.save(product);

        // Create product payment
        ProductPayment productPayment = new ProductPayment();
        productPayment.setProduct(product);
        productPayment.setAmount(createProductDTO.getProductPayment().getAmount());
        productPayment.setCurrency("NGN");
        productPayment.setReference(reference);
        productPayment.setChannels(List.of("card", "bank_transfer", "ussd"));
        productPayment.setUserData(user);
        productPaymentRepository.save(productPayment);

        // Generate base QR code and link it to the payment product
        QrCode baseQRCode = generateBaseQRCode(productPayment);
        product.setBaseQRCode(baseQRCode);
        productRepository.save(product);

        return generateProductPaymentQRCode(productPayment, width, height);
    }

    private QrCode generateBaseQRCode(ProductPayment productPayment) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String baseQRCodeData = objectMapper.writeValueAsString(Map.of(
                "productId", productPayment.getProduct().getId(),
                "paymentReference", productPayment.getReference()
        ));

        QrCode baseQRCode = new QrCode();
        baseQRCode.setQrCodeData(baseQRCodeData);
        qrCodeRepository.save(baseQRCode);

        return baseQRCode;
    }

    public byte[] generateProductPaymentQRCode(ProductPayment productPayment, int width, int height) throws IOException {
        String paymentData = generatePaymentData(productPayment);
        return generateQRCodeImage(paymentData, width, height);
    }

    private String generatePaymentData(ProductPayment productPayment) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(Map.of(
                "amount", productPayment.getAmount(),
                "email", productPayment.getUserData().getEmail(),
                "currency", productPayment.getCurrency(),
                "reference", productPayment.getReference(),
                "channels", productPayment.getChannels(),
                "productName", productPayment.getProduct().getProductName()
        ));
    }

    @Override
    public byte[] generateSubQRCode(ProductPayment productPayment, int width, int height) throws IOException {
        return generateProductPaymentQRCode(productPayment, width, height);
    }
   @Override
    @Transactional
    public void enableQrCode(long qrCodeId) {
        QrCode qrCode = qrCodeRepository.findById(qrCodeId)
                .orElseThrow(() -> new RuntimeException("QR Code not found"));

        qrCode.setEnabled(true);
        qrCodeRepository.save(qrCode);
    }
  @Override
    @Transactional
    public void disableQrCode(long qrCodeId) {
        QrCode qrCode = qrCodeRepository.findById(qrCodeId)
                .orElseThrow(() -> new RuntimeException("QR Code not found"));

        qrCode.setEnabled(false);
        qrCodeRepository.save(qrCode);
    }

    @Override
    @Transactional
    public void setExpirationDate(long qrCodeId, ExpirationDto expirationDate) {
        QrCode qrCode = qrCodeRepository.findById(qrCodeId)
                .orElseThrow(() -> new RuntimeException("QR Code not found"));

        qrCode.setExpirationDate(expirationDate.getExpirationDate());
        qrCodeRepository.save(qrCode);
    }
}