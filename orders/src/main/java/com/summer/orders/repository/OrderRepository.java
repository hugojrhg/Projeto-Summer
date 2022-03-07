package com.summer.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.summer.orders.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    
}
