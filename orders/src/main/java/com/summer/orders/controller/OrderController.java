package com.summer.orders.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import com.summer.orders.model.Order;
import com.summer.orders.service.OrdersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/Orders")
public class OrderController {

    @Autowired
	private OrdersService ordersService;
	
	@GetMapping
	public ResponseEntity<List<Order>> findAll(){
		
		List<Order> orders = ordersService.findAll();
		return ResponseEntity.ok(orders);
		
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id) {

		Order order = ordersService.findById(id);
		return ResponseEntity.ok(order);

	}
	
	@PostMapping()
	public ResponseEntity<Order> createOrder(@RequestHeader(value="api-key") String string,
			@RequestBody Order order){
		
		ordersService.saveOrder(order);
		URI location = URI.create(String.format("/Orders/%s", order.getId()));
		return ResponseEntity.created(location).body(order);
		
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order newOrder){
	
		Order oldOrder = ordersService.findById(id);
		
		oldOrder.setName(newOrder.getNome());
		oldOrder.setPreco(newOrder.getPreco());
		oldOrder.setQuantidade(newOrder.getQuantidade());
		
		final Order ordersResult = ordersService.saveOrder(oldOrder);
		return ResponseEntity.ok(ordersResult);
		
	}
	//Update Nome produto
	@PatchMapping("/{id}/nome/{newNome}")
	public ResponseEntity<Order> patchOrder(@PathVariable Long id, @PathVariable String newNome) {
		try {
			Order order = ordersService.findById(id);
			order.setName(newNome);

			Order orderResult = ordersService.saveOrder(order);

			return ResponseEntity.ok(orderResult);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//Update Preco pedido
	@PatchMapping("/{id}/preco/{newPreco}")
	public ResponseEntity<Order> patchWorker(@PathVariable Long id, @PathVariable BigDecimal newPreco) {
		try {
			Order order = ordersService.findById(id);
			order.setPreco(newPreco);

			Order orderResult = ordersService.saveOrder(order);

			return ResponseEntity.ok(orderResult);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//Update Quantidade
	@PatchMapping("/{id}/quantidade/{newQuantidade}")
	public ResponseEntity<Order> patchWorker(@PathVariable Long id, @PathVariable Integer newQuantidade) {
		try {
			Order order = ordersService.findById(id);
			order.setQuantidade(newQuantidade);

			Order orderResult = ordersService.saveOrder(order);

			return ResponseEntity.ok(orderResult);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletWorker(@PathVariable Long id) {
		ordersService.deleteOrder(id);
		return ResponseEntity.noContent().build();
	}
    
}
