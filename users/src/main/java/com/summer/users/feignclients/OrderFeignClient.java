package com.summer.users.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.summer.users.model.Orders;




@FeignClient(name = "order-service", url = "localhost:8080", path = "/orders")
public interface OrderFeignClient {

	@GetMapping
	public ResponseEntity<List<Orders>> findAll();
	
	@GetMapping(value = "/{id}")
	ResponseEntity<Orders> findById(@PathVariable Long id);
		
	@PutMapping(value="/{id}")
	public ResponseEntity<Orders> updateProduct(@PathVariable Long id, @RequestBody Orders neworder);
	
}