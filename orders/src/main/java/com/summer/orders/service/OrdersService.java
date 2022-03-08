package com.summer.orders.service;

import java.util.List;
import java.util.Optional;

import com.summer.orders.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.summer.orders.model.Orders;
import com.summer.orders.repository.OrderRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class OrdersService {
	
    @Autowired
    private OrderRepository orderRepository;
    
    public List<Orders> findAll(){
		List<Orders> produtos = orderRepository.findAll();
		return produtos;
	}
	
	public Orders findById(Long id) throws OrderNotFoundException{
		return orderRepository.findById(id).orElseThrow(
				( ) -> new OrderNotFoundException("Order de id "+ id +" não encontrada"));
	}
	
	public Orders saveOrder(Orders order) {
		var newOrder = orderRepository.save(order);
		return newOrder;
	}

	public Orders updateOrder(@PathVariable Long id, @RequestBody Orders newOrder) throws OrderNotFoundException {

		Orders oldOrder = orderRepository.findById(id).orElseThrow(
				( ) -> new OrderNotFoundException("Order de id "+ id +" não encontrada para alteração."));

		oldOrder.setDescricao(newOrder.getDescricao());
		oldOrder.setId_produto(newOrder.getId_produto());
		oldOrder.setQtd_produtos(newOrder.getQtd_produtos());
		oldOrder.setValor(newOrder.getValor());

		return saveOrder(oldOrder);
	}
	
	public void deleteOrder(Long id) throws OrderNotFoundException {
		var order = orderRepository.findById(id).orElseThrow(
				() -> new OrderNotFoundException("Order de id "+ id +" não encontrada"));
		orderRepository.delete(order);
	}
    
}
