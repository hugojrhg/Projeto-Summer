package com.summer.orders.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.summer.orders.model.Orders;
import com.summer.orders.repository.OrderRepository;

@Service
public class OrdersService {
	
    @Autowired
    private OrderRepository orderRepository;
    
    public List<Orders> findAll(){
		
		List<Orders> produtos = orderRepository.findAll();
		return produtos;
		
	}
	
	public Orders findById(Long id) {
		Optional<Orders> order = orderRepository.findById(id);
		return order.get();
	}
	
	public Orders saveOrder(Orders order) {
		Orders newOrder = orderRepository.save(order);
		return newOrder;
	}
	
	public void deleteOrder(Long id) {

		Optional<Orders> order = orderRepository.findById(id);

		orderRepository.delete(order.get());
	}
    
}
