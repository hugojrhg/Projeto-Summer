package com.summer.users.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.summer.users.exceptions.UserNotFoundException;
import com.summer.users.feignclients.OrderFeignClient;
import com.summer.users.model.OrderList;
import com.summer.users.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.summer.users.model.User;
import com.summer.users.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final OrdersRepository ordersRepository;
	private final OrderFeignClient ordersFeignClient;

	@Autowired
	public UserService(UserRepository userRepository, OrdersRepository ordersRepository,
					   OrderFeignClient ordersFeignClient) {
		this.userRepository=userRepository;
		this.ordersRepository=ordersRepository;
		this.ordersFeignClient = ordersFeignClient;
	}
	
	public List<User> findAll(){
		return userRepository.findAll();
		
	}
	
	public User findById(Long id) throws UserNotFoundException {
		return userRepository.findById(id).orElseThrow(
				() -> new UserNotFoundException("Usuário não encontrado")
		);
	}
	
	public User saveUser(User usuario) {
		OrderList orderList = new OrderList();

		ordersRepository.saveAll(ordersFeignClient.findAll().getBody());

		orderList.setOrderList(ordersFeignClient.findAll().getBody().stream()
				.filter( x -> x.getUser().equals(usuario.getId()))
				.collect(Collectors.toList()));
		usuario.setCompras(orderList.getOrderList());
		userRepository.save(usuario);
		return usuario;
	}

	public User patchName(Long id, String newName) throws UserNotFoundException {
		var usuario = findById(id);
		usuario.setNome(newName);
		return saveUser(usuario);
	}

	public User patchCpf(Long id, String cpf) throws UserNotFoundException {
		var usuario = findById(id);
		usuario.setCpf(cpf);
		return saveUser(usuario);
	}


	public User patchEmail(Long id, String email) throws UserNotFoundException {
		var usuario = findById(id);
		usuario.setEmail(email);
		return saveUser(usuario);
	}


	public User putUser(Long id,User newUser) throws UserNotFoundException {
		User oldusuario = findById(id);
		oldusuario.setNome(newUser.getNome());
		oldusuario.setCpf(newUser.getCpf());
		oldusuario.setEmail(newUser.getEmail());

		return saveUser(oldusuario);
	}
	
	public void deleteUser(Long id) throws UserNotFoundException {
		var user = findById(id);
		userRepository.delete(user);
	}
	
}
