package com.summer.users.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.summer.users.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.summer.users.model.User;
import com.summer.users.service.UserService;

@RestController
@RequestMapping(value = "/usuarios")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@GetMapping
	public ResponseEntity<List<User>> findAll(){
		List<User> usuarios = userService.findAll();
		return ResponseEntity.ok(usuarios);
		
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) throws UserNotFoundException {
		User usuario = userService.findById(id);
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping()
	public ResponseEntity<User> createUser(@Valid @RequestHeader(value="api-key") String string,
			@RequestBody User usuario){
		userService.saveUser(usuario);
		return ResponseEntity.created(URI.create(String.format("/usuarios/%s", usuario.getId())))
				.body(usuario);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newusuario) throws UserNotFoundException {
		var novoUser = userService.putUser(id,newusuario);
		return ResponseEntity.ok(novoUser);
	}
	//Update Nome
	@PatchMapping("/{id}/nome/{newNome}")
	public ResponseEntity<User> patchUser(@PathVariable Long id, @PathVariable String newNome) throws UserNotFoundException {
		var userResult = userService.patchName(id,newNome);
		return ResponseEntity.ok(userResult);
	}
	
	//Update CPF
	@PatchMapping("/{id}/cpf/{newCpf}")
	public ResponseEntity<User> patchId(@PathVariable Long id, @PathVariable String newCpf) throws UserNotFoundException {
		var userResult = userService.patchCpf(id,newCpf);
		return ResponseEntity.ok(userResult);
	}

	//Update Email
	@PatchMapping("/{id}/email/{newEmail}")
	public ResponseEntity<User> patchEmail(@PathVariable Long id, @PathVariable String newEmail) throws UserNotFoundException {
		var userResult = userService.patchEmail(id,newEmail);
		return ResponseEntity.ok(userResult);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletWorker(@PathVariable Long id) throws UserNotFoundException {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
	
}


	

