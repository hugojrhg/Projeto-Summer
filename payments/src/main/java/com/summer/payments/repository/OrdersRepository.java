package com.summer.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.summer.payments.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
