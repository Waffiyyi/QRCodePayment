package com.example.demo.dto;

import com.example.demo.models.Product;
import com.example.demo.models.ProductPayment;
import lombok.Data;

@Data
public class CreateProductDTO {
    private Product product;
    private ProductPayment productPayment;

}
