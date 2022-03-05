package com.summer.orders.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.summer.orders.model.Product;


@FeignClient(name = "product-service", url = "localhost:8080", path = "/produtos")
public interface ProductFeignClient {

	@GetMapping(value = "/{id}")
	
	ResponseEntity<Product> findById(@PathVariable Long id);
		
	@PutMapping(value="/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product newproduto);
	
}