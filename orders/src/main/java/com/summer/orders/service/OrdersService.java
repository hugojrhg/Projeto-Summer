package com.summer.orders.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.summer.orders.exception.*;
import com.summer.orders.feignclients.ProductFeignClient;
import com.summer.orders.feignclients.UserFeignClient;
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

	@Autowired
	private UserFeignClient userFeignClient;

	@Autowired
	ProductFeignClient productFeignClient;
    
    public List<Orders> findAll(){
		List<Orders> produtos = orderRepository.findAll();
		return produtos;
	}
	
	public Orders findById(Long id) throws OrderNotFoundException{
		return orderRepository.findById(id).orElseThrow(
				( ) -> new OrderNotFoundException("Order de id "+ id +" não encontrada"));
	}
	
	public Orders saveOrder(Orders order) throws InvalidInputException, ProductNotFoundException,
			UserNotFoundException {
		if (order.getQtd_produtos() <=0) {
			throw new InvalidInputException("Quantidade pedida menor que 1");
		}

		try{
			var user=userFeignClient.findById(order.getUser()).getBody();
		}catch (Exception x){
			throw new UserNotFoundException("Usuário não encontrado");
		}

		try{
			var produto = productFeignClient.findById(order.getId_produto()).getBody();
			if (produto.getQuantidade() < 0){ throw new InsufficientProductException("Produtos insuficientes no estoque");}
			//esse método acima pode gerar um nullPointer.

			BigDecimal qtdProdutosInBigDecimal = new BigDecimal(order.getQtd_produtos());
			order.setValor(produto.getPreco().multiply(qtdProdutosInBigDecimal));
			order.setValor(produto.getPreco().multiply(BigDecimal.valueOf(order.getQtd_produtos())));
			produto.setQuantidade(produto.getQuantidade() - order.getQtd_produtos());
			productFeignClient.updateProduct(produto.getId(), produto);
		} catch (Exception x) {
			throw new ProductNotFoundException("Produto não encontrado");
		}

		var newOrder = orderRepository.save(order);
		return newOrder;
	}

	public Orders updateOrder(@PathVariable Long id, @RequestBody Orders newOrder) throws OrderNotFoundException, InvalidInputException, ProductNotFoundException, UserNotFoundException {

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
