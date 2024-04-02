package com.example.demo.service;

import com.example.demo.dto.CreateProductDTO;
import com.example.demo.dto.ExpirationDto;
import com.example.demo.dto.InitializeTransactionDTO;
import com.example.demo.models.Product;
import com.example.demo.models.ProductPayment;


import java.io.IOException;


public interface ProductService {
    byte[] createProductAndPayment(CreateProductDTO createProductDTO, int width, int height) throws IOException;

   byte[] generateProductPaymentQRCode(ProductPayment productPayment, int width, int height) throws IOException;
    byte[] generateSubQRCode(ProductPayment productPayment, int width, int height) throws IOException;
    void enableQrCode(long qrCodeId);
  void disableQrCode(long qrCodeId);
    void setExpirationDate(long qrCodeId, ExpirationDto expirationDate);

}
