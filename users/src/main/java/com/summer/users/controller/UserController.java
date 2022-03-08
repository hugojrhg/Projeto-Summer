package com.summer.users.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.summer.users.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.summer.users.feignclients.OrderFeignClient;
import com.summer.users.model.OrderList;
import com.summer.users.model.User;
import com.summer.users.repository.OrdersRepository;
import com.summer.users.service.UserService;

@RestController
@RequestMapping(value = "/usuarios")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrdersRepository orderRepository;
	
	@Autowired
	private OrderFeignClient orderClient;
	
	@GetMapping
	public ResponseEntity<List<User>> findAll(){
		
		orderRepository.saveAll(orderClient.findAll().getBody());
		
		List<User> usuarios = userService.findAll();
		return ResponseEntity.ok(usuarios);
		
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) throws UserNotFoundException {
		
		orderRepository.saveAll(orderClient.findAll().getBody());
		
		User usuario = userService.findById(id);
		return ResponseEntity.ok(usuario);

	}
	
	@PostMapping()
	public ResponseEntity<User> createUser(@Valid @RequestHeader(value="api-key") String string,
			@RequestBody User usuario){
		
		User trueusuario = usuario;
		OrderList orderList = new OrderList();
					
		orderRepository.saveAll(orderClient.findAll().getBody());
		
		orderList.setOrderList(orderClient.findAll().getBody());
		trueusuario.setCompras(orderList.getOrderList());
		
		userService.saveUser(trueusuario);
		URI location = URI.create(String.format("/usuarios/%s", usuario.getId()));
		
		return ResponseEntity.created(location).body(usuario);
		
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newusuario) throws UserNotFoundException {
	
		User oldusuario = userService.findById(id);
		
		oldusuario.setNome(newusuario.getNome());
		oldusuario.setCpf(newusuario.getCpf());
		oldusuario.setEmail(newusuario.getEmail());
		
	
		final User usuarioResult = userService.saveUser(oldusuario);
		return ResponseEntity.ok(usuarioResult);
		
	}
	//Update Nome
	@PatchMapping("/{id}/nome/{newNome}")
	public ResponseEntity<User> patchUser(@PathVariable Long id, @PathVariable String newNome) {
		try {
			User usuario = userService.findById(id);
			usuario.setNome(newNome);

		
			User userResult = userService.saveUser(usuario);

			return ResponseEntity.ok(userResult);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Update CPF
	@PatchMapping("/{id}/cpf/{newCpf}")
	public ResponseEntity<User> patchId(@PathVariable Long id, @PathVariable String newCpf) {
		try {
			User usuario = userService.findById(id);
			usuario.setCpf(newCpf);

			User userResult = userService.saveUser(usuario);

			return ResponseEntity.ok(userResult);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//Update Email
	@PatchMapping("/{id}/email/{newEmail}")
	public ResponseEntity<User> patchEmail(@PathVariable Long id, @PathVariable String newEmail) {
		try {
			User usuario = userService.findById(id);
			usuario.setEmail(newEmail);

			User userResult = userService.saveUser(usuario);

			return ResponseEntity.ok(userResult);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletWorker(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
	
}


	

