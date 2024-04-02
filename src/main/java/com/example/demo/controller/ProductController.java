package com.example.demo.controller;

import com.example.demo.dto.CreateProductDTO;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create-product-qrCode")
    public ResponseEntity<byte[]> createProduct(@RequestBody CreateProductDTO createProductDTO) throws IOException {
       byte[] productQr = productService.createProductAndPayment(createProductDTO, 200, 200);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(productQr);
    }


}
