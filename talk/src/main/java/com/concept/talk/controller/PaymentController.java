package com.concept.talk.controller;

import com.concept.talk.dto.PaymentDTO;
import com.concept.talk.entity.Payment;
import com.concept.talk.entity.User;
import com.concept.talk.service.PaymentService;
import com.concept.talk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<Payment> createPayment(@RequestBody PaymentDTO paymentDTO, @RequestParam String email){
		Optional<User> userOptional = userService.findByEmail(email);
		if(userOptional.isEmpty()){
			return ResponseEntity.status(404).build();
		}
		Payment payment = paymentService.createPayment(userOptional.get(),paymentDTO);
		return ResponseEntity.ok(payment);
	}
	
	@GetMapping("/user/{email}")
	public ResponseEntity<List<Payment>> getPaymentsByUser(@PathVariable String email){
		Optional<User> userOptional = userService.findByEmail(email);
        if(userOptional.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        List<Payment> payments = paymentService.getPaymentsByUser(userOptional.get());
        return ResponseEntity.ok(payments);
	}
}
