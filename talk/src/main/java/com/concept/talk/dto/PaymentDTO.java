package com.concept.talk.dto;

import lombok.Data;


public class PaymentDTO {
	private String amount;
	private String transactionId;
	
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
}
