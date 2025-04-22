package com.concept.talk.controller;

import com.concept.talk.entity.Payment;
import com.concept.talk.entity.User;
import com.concept.talk.service.impl.AuthenticationServiceImpl;
import com.concept.talk.service.impl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
	@Autowired
	AuthenticationServiceImpl authenticationService;
	@Autowired
	PaymentServiceImpl paymentService;
	
	@GetMapping("/getusers")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<>(authenticationService.getAllUsers(), HttpStatus.OK);
	}
	
	@GetMapping("/get-all-payments")
	public ResponseEntity<List<Payment>> getAllPayments(){
		List<Payment> payments = paymentService.getAllPayments();
		return new ResponseEntity<>(payments,HttpStatus.OK);
	}
}
