package com.summer.payments.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import com.summer.payments.enums.Tipos;
import com.summer.payments.model.Payment;
import com.summer.payments.service.PaymentService;

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
@RequestMapping(value = "/pagamentos")
public class PaymentController {
    
    @Autowired(required = true)
    private OrderFeingClient orderClient;

    @Autowired(required = true)
    private UserFeingClient userClient;

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
                    //essa parte não tenho a mínima ideia
                
		paymentService.savePayment(payment);
		URI location = URI.create(String.format("/payments/%s", payment.getId()));
		return ResponseEntity.created(location).body(payment);
		
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment newpPayment){
	
		Payment oldpPayment = paymentService.findById(id);
		
		oldpPayment.setNotaFiscal(newpPayment.getNotaFiscal());
		oldpPayment.setValorPagamento(newpPayment.getValorPagamento());
		oldpPayment.setTipo(newpPayment.getTipo());
		
		final Payment paymentResult = paymentService.savePayment(oldpPayment);
		return ResponseEntity.ok(paymentResult);
		
	}
	//Update NotaFiscal
	@PatchMapping("/{id}/notafiscal/{newNotafiscal}")
	public ResponseEntity<Payment> patchPayment(@PathVariable Long id, @PathVariable String newNotafiscal) {
		try {
			Payment pagamento = paymentService.findById(id);
			pagamento.setNotaFiscal(newNotafiscal);

			Payment paymentResult = paymentService.savePayment(pagamento);

			return ResponseEntity.ok(paymentResult);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//Update ValorPagamento
	@PatchMapping("/{id}/valorpagamento/{newValorPagamento}")
	public ResponseEntity<Payment> patchWorker(@PathVariable Long id, @PathVariable BigDecimal newValorPagamento) {
		try {
			Payment pagamento = paymentService.findById(id);
			pagamento.setValorPagamento(newValorPagamento);

			Payment paymentResult = paymentService.savePayment(pagamento);

			return ResponseEntity.ok(paymentResult);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//Update Tipo
	@PatchMapping("/{id}/tipo/{newTipo}")
	public ResponseEntity<Payment> patchWorker(@PathVariable Long id, @PathVariable Tipos newTipo) {
		try {
			Payment pagamento = paymentService.findById(id);
			pagamento.setTipo(newTipo);

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
