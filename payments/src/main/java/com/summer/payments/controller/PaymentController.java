package com.summer.payments.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

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

import com.summer.payments.enums.Tipos;
import com.summer.payments.model.Payment;
import com.summer.payments.service.PaymentService;

@RestController
@RequestMapping(value = "/pagamentos")
public class PaymentController {
    
    @Autowired
	private PaymentService paymentService;
	
	@GetMapping
	public ResponseEntity<List<Payment>> findAll(){
		
		List<Payment> payments = paymentService.findAll();
		return ResponseEntity.ok(payments);
		
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Payment> findById(@PathVariable Long id) {

		Payment payment = paymentService.findById(id);
		return ResponseEntity.ok(payment);

	}
	
	@PostMapping()
	public ResponseEntity<Payment> createPayment(@RequestHeader(value="api-key") String string,
			@RequestBody Payment payment ) throws Exception { 
		
		
		paymentService.savePayment(payment);
		URI location = URI.create(String.format("/payments/%s", payment.getId()));
		return ResponseEntity.created(location).body(payment);
		
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment newpPayment) throws Exception{
	
		Payment oldpPayment = paymentService.findById(id);
		
		oldpPayment.setDataDePagamento(newpPayment.getDataDePagamento());
		oldpPayment.setOrderId(newpPayment.getOrderId());
		oldpPayment.setFormaPagamento(newpPayment.getFormaPagamento());
		oldpPayment.setValorTotal(newpPayment.getValorTotal());
		oldpPayment.setNomePagante(newpPayment.getNomePagante());
		oldpPayment.setParcelas(newpPayment.getParcelas());
		
		final Payment paymentResult = paymentService.savePayment(oldpPayment);
		return ResponseEntity.ok(paymentResult);
		
	}
	//Update OrdersId
	@PatchMapping("/{id}/orderid/{newOrderId}")
	public ResponseEntity<Payment> patchPayment(@PathVariable Long id, @PathVariable List<Long> newOrderId) {
		try {
			Payment pagamento = paymentService.findById(id);
			pagamento.setOrderId(newOrderId);

			Payment paymentResult = paymentService.savePayment(pagamento);

			return ResponseEntity.ok(paymentResult);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//Update Forma de Pagamento
	@PatchMapping("/{id}/formapagamento/{newFormaPagamento}")
	public ResponseEntity<Payment> patchPayment(@PathVariable Long id, @PathVariable Tipos newFormaPagamento) {
		try {
			Payment pagamento = paymentService.findById(id);
			pagamento.setFormaPagamento(newFormaPagamento);

			Payment paymentResult = paymentService.savePayment(pagamento);

			return ResponseEntity.ok(paymentResult);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//Update ValorTotal
	@PatchMapping("/{id}/valortotal/{newValorTotal}")
	public ResponseEntity<Payment> patchPayment(@PathVariable Long id, @PathVariable BigDecimal newValorTotal) {
		try {
			Payment pagamento = paymentService.findById(id);
			pagamento.setValorTotal(newValorTotal);

			Payment paymentResult = paymentService.savePayment(pagamento);

			return ResponseEntity.ok(paymentResult);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Update NomePagante
		@PatchMapping("/{id}/nomepagante/{newNomePagante}")
		public ResponseEntity<Payment> patchPayment(@PathVariable Long id, @PathVariable String newNomePagante) {
			try {
				Payment pagamento = paymentService.findById(id);
				pagamento.setNomePagante(newNomePagante);

				Payment paymentResult = paymentService.savePayment(pagamento);

				return ResponseEntity.ok(paymentResult);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
		//Update Parcelas
		@PatchMapping("/{id}/parcelas/{newParcelas}")
		public ResponseEntity<Payment> patchPayment(@PathVariable Long id, @PathVariable Integer newParcelas) {
			try {
				Payment pagamento = paymentService.findById(id);
				pagamento.setParcelas(newParcelas);

				Payment paymentResult = paymentService.savePayment(pagamento);

				return ResponseEntity.ok(paymentResult);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletWorker(@PathVariable Long id) {
		paymentService.deletePayment(id);
		return ResponseEntity.noContent().build();
	}
}
