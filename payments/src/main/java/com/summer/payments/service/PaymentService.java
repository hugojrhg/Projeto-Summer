package com.summer.payments.service;

import java.util.List;
import java.util.Optional;

import com.summer.payments.model.Payment;
import com.summer.payments.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    
    @Autowired
	private PaymentRepository paymentRepository;
	
	public List<Payment> findAll(){
		
		List<Payment> pagamentos = paymentRepository.findAll();
		return pagamentos;
		
	}
	
	public Payment findById(Long id) {
		Optional<Payment> pagamento = paymentRepository.findById(id);
		return payment.get();
	}
	
	public Payment savePayment(Payment payment) {
		Payment newPayment = paymentRepository.save(payment);
		return newPayment;
	}
	
	public void deletePayment(Long id) {

		Optional<Payment> payment = paymentRepository.findById(id);

		paymentRepository.delete(payment.get());
	}
}
