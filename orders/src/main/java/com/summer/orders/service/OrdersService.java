package com.summer.orders.service;

import java.util.List;
import java.util.Optional;

import com.summer.orders.model.Order;
import com.summer.orders.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll(){
		
		List<Order> produtos = orderRepository.findAll();
		return produtos;
		
	}
	
	public Order findById(Long id) {
		Optional<Order> order = orderRepository.findById(id);
		return order.get();
	}
	
	public Order saveOrder(Order order) {
		Order newOrder = orderRepository.save(order);
		return newOrder;
	}
	
	public void deleteOrder(Long id) {

		Optional<Order> order = orderRepository.findById(id);

		orderRepository.delete(order.get());
	}
    
}
