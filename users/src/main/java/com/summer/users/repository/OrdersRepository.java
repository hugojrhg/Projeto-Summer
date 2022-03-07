package com.summer.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.summer.users.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
