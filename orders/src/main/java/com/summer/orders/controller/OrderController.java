package com.summer.orders.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import com.summer.orders.exception.OrderNotFoundException;
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

import com.summer.orders.feignclients.ProductFeignClient;
import com.summer.orders.feignclients.UserFeignClient;
import com.summer.orders.model.Orders;
import com.summer.orders.model.Product;
import com.summer.orders.model.User;
import com.summer.orders.service.OrdersService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
	private OrdersService ordersService;
	
    @Autowired(required=true)
    private ProductFeignClient productClient;
    
    @Autowired
    private UserFeignClient userClient;
    
	@GetMapping
	public ResponseEntity<List<Orders>> findAll(){
		
		List<Orders> orders = ordersService.findAll();
		return ResponseEntity.ok(orders);
		
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Orders> findById(@PathVariable Long id) throws OrderNotFoundException {

		Orders order = ordersService.findById(id);
		return ResponseEntity.ok(order);

	}
	
	@PostMapping()
	public ResponseEntity<Orders> createOrder(@RequestHeader(value="api-key") String string,
			@RequestBody Orders order) throws Exception{
		
		User user = new User();
		Product produto = productClient.findById(order.getId_produto()).getBody();
		BigDecimal qtdProdutosInBigDecimal = new BigDecimal(order.getQtd_produtos());
		order.setValor(produto.getPreco().multiply(qtdProdutosInBigDecimal));
		
		try {
			
			user = userClient.findById(order.getUser()).getBody();
			
		}catch(Exception e) {
			
			throw new Exception("Usuário não encontrado");
			
		}
		
		produto.setQuantidade(produto.getQuantidade() - order.getQtd_produtos());
		if(produto.getQuantidade() < 0 || order.getQtd_produtos() <=0) {
			throw new Exception("Produtos Insuficientes no Estoque ou Quantidade pedida menor que 1");
		}else {
			productClient.updateProduct(produto.getId(), produto);
		}
		
		
		ordersService.saveOrder(order);
		URI location = URI.create(String.format("/Orders/%s", order.getId()));
		return ResponseEntity.created(location).body(order);
		
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody Orders newOrder) throws OrderNotFoundException {
		var ordersUpdated= ordersService.updateOrder(id,newOrder);
		return ResponseEntity.ok().body(ordersUpdated);
	}

	//Update Descricao
	@PatchMapping("/{id}/descricao/{newDescricao}")
	public ResponseEntity<Orders> patchOrder(@PathVariable Long id, @PathVariable String newDescricao) throws OrderNotFoundException {
			var order = ordersService.findById(id);
			order.setDescricao(newDescricao);
			Orders orderResult = ordersService.saveOrder(order);
			return ResponseEntity.ok(orderResult);
	}
	
	

	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletWorker(@PathVariable Long id) throws OrderNotFoundException {
		ordersService.deleteOrder(id);
		return ResponseEntity.noContent().build();
	}
    
}
