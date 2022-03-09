package com.summer.payments.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.summer.payments.model.User;

@FeignClient(name = "user-service", url = "localhost:8080", path = "/usuarios")
public interface UserFeignClient {

	@GetMapping(value = "/{id}")
	ResponseEntity<User> findById(@PathVariable Long id);
		
	@PutMapping(value="/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newusuario);
	
}
