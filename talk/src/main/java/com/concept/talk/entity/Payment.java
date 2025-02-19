package com.concept.talk.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payments")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;
	
	@Column(nullable = false)
	private Double amount;
	
	@Column(nullable = true)
	private String transactionId;
	
	@Column(name = "timestamp", nullable = false)
	private LocalDateTime timestamp = LocalDateTime.now();
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentStatus paymentStatus = PaymentStatus.Pending;
	
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private User user;
}
