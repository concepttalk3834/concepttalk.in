package com.concept.talk.service.impl;

import com.concept.talk.dto.PaymentDTO;
import com.concept.talk.entity.Payment;
import com.concept.talk.entity.PaymentStatus;
import com.concept.talk.entity.User;
import com.concept.talk.repository.PaymentRepository;
import com.concept.talk.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	private final PaymentRepository paymentRepository;
	
	public PaymentServiceImpl(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}
	
	@Override
	public Payment createPayment(User user, PaymentDTO paymentDTO) {
		Payment payment = new Payment();
		payment.setUser(user);
		payment.setAmount(paymentDTO.getAmount());
		payment.setTransactionId(paymentDTO.getTransactionId());
		payment.setPaymentStatus(PaymentStatus.Pending);
		return paymentRepository.save(payment);
	}
	
	@Override
	public List<Payment> getPaymentsByUser(User user) {
		return paymentRepository.findByUser(user);
	}
}
