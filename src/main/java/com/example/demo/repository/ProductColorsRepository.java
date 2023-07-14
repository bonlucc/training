package com.example.demo.repository;

import com.example.demo.entity.ProductColors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductColorsRepository extends JpaRepository<ProductColors, Long> {
}
