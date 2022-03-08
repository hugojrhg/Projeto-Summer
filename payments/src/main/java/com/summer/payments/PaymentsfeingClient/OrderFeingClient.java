package com.summer.payments.PaymentsfeingClient;

import com.summer.payments.model.Payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", url = "localhost:8080", path = "/orders")

public interface PaymentFeignClient {

    @GetMapping(value = "/{id}")

    ResponseEntity<Payment> findById(@PathVariable Long id);
    
    @PutMapping(value="/{id}")
    public ResponseEntity<Orders>updateOrders(@PathVariable Long id, RequestBody Payment neworder);

    

    

