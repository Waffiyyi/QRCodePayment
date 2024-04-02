package com.example.demo.repository;

import com.example.demo.models.ProductPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPaymentRepository extends JpaRepository<ProductPayment, Long> {
}
