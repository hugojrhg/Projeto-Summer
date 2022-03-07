package com.summer.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.summer.products.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
