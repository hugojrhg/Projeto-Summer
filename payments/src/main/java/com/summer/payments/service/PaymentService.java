package com.summer.payments.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.summer.payments.enums.Situacao;
import com.summer.payments.enums.Tipos;
import com.summer.payments.feignclients.OrderFeignClient;
import com.summer.payments.feignclients.UserFeignClient;
import com.summer.payments.model.Orders;
import com.summer.payments.model.Payment;
import com.summer.payments.model.User;
import com.summer.payments.repository.OrdersRepository;
import com.summer.payments.repository.PaymentRepository;

@Service
public class PaymentService {
    
    @Autowired
	private PaymentRepository paymentRepository;
    
    @Autowired
    private UserFeignClient userClient;
    
    @Autowired
    private OrderFeignClient orderClient;
    
    @Autowired
    private OrdersRepository orderRepository;
	
	public List<Payment> findAll(){
		
		List<Payment> pagamentos = paymentRepository.findAll();
		return pagamentos;
		
	}
	
	public Payment findById(Long id) {
		Optional<Payment> pagamento = paymentRepository.findById(id);
		return pagamento.get();
	}
	
	public Payment savePayment(Payment payment) throws Exception {
		
		orderRepository.saveAll(orderClient.findAll().getBody());
		
		payment.setNomePagante(userClient.findById(orderClient.findById(payment.getOrderId().get(0)).getBody().getUser()).getBody().getNome());
		
		Integer calculoValor = 0;
		
		
		//Calcula o valor somando o preço de todos os pedidos
		for(int i = 0; i < payment.getOrderId().size(); i++) {
					
			calculoValor = calculoValor + orderClient.findById(payment.getOrderId().get(i)).getBody().getValor().intValue();
			
			Orders order = orderClient.findById(payment.getOrderId().get(i)).getBody();
			order.setSituacao(Situacao.PAGO);
			orderClient.updateOrder(payment.getOrderId().get(i), order);
			
			System.out.println(orderClient.findById(payment.getOrderId().get(i)).getBody().getSituacao());
			
		}
		BigDecimal calculoValorInBigDecimal = new BigDecimal(calculoValor);
		payment.setValorTotal(calculoValorInBigDecimal);
		BigDecimal porcentagem = new BigDecimal(0.05);
		BigDecimal porcentagemDesc = new BigDecimal(0.1);
		BigDecimal taxaCartao = payment.getValorTotal().multiply(porcentagem);
		User user = userClient.findById(orderClient.findById(payment.getOrderId().get(0)).getBody().getUser()).getBody(); 
		
		//Verifica se existe pedidos a serem pagos
		if(payment.getOrderId().isEmpty()) {
			throw new Exception("Sem pedidos a serem pagos");
		}
		
		//Verifica se a parcela não é menor que 0
		if(payment.getParcelas() < 1) {
			throw new Exception("Sem parcelas!");
		}
		
		//Verifica se não está tentando parcelar sem o uso do cartão de crédito
		if(payment.getParcelas() > 1 && payment.getFormaPagamento() != Tipos.CARTAO_CREDITO) {
			throw new Exception("Só é possível parcelar utilizando um cartão de crédito");
		}
		
		//Acréscimo cartão de crédito
		if(payment.getFormaPagamento() == Tipos.CARTAO_CREDITO ||
				payment.getFormaPagamento() == Tipos.CARTAO_DEBITO) {
			payment.setValorTotal(payment.getValorTotal().add(taxaCartao));
		}
		
		//Desconto Primeira Compra
		if(user.getCompras().isEmpty()) {
			payment.setValorTotal(payment.getValorTotal().subtract(orderClient.findById(payment.getOrderId().get(0)).getBody().getValor().multiply(porcentagemDesc)));
		}
		
		
		Payment newPayment = paymentRepository.save(payment);
		return newPayment;
	}
	
	public void deletePayment(Long id) {

		Optional<Payment> payment = paymentRepository.findById(id);

		paymentRepository.delete(payment.get());
	}
}
