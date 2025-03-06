package com.concept.talk.dto;

import lombok.Data;


public class PaymentDTO {
	private Double amount;
	private String transactionId;
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
