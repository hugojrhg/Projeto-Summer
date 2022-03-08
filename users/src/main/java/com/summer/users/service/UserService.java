package com.summer.users.service;

import java.util.List;
import java.util.Optional;

import com.summer.users.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.summer.users.model.User;
import com.summer.users.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAll(){
		
		List<User> usuarios = userRepository.findAll();
		return usuarios;
		
	}
	
	public User findById(Long id) throws UserNotFoundException {
		User usuario = userRepository.findById(id).orElseThrow(
				() -> new UserNotFoundException("Usuário não encontrado")
		);
		return usuario;
	}
	
	public User saveUser(User user) {
		User newuser = userRepository.save(user);
		return newuser;
	}
	
	public void deleteUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		userRepository.delete(user.get());
	}
	
}
