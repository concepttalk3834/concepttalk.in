package com.concept.talk.service;

import com.concept.talk.dto.PaymentDTO;
import com.concept.talk.entity.Payment;
import com.concept.talk.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PaymentService {
	Payment createPayment(User user, PaymentDTO paymentDTO);
	List<Payment> getPaymentsByUser(User user);
}
