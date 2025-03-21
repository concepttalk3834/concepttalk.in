package com.concept.talk.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Order_id;
	@Column(nullable = false)
	private String amount;
	@Column(nullable = true)
	private String transactionId;
	@Column(name = "timestamp", nullable = false)
	private LocalDateTime timestamp = LocalDateTime.now();
	private String paymentId;
	private String status;
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private User user;
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getOrder_id() {
		return Order_id;
	}
	
	public void setOrder_id(Long order_id) {
		Order_id = order_id;
	}
	
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
