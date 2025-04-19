package com.example.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InvoiceDTO {
	private Long invoiceNo;
    private Long estimateId;
    private Long chainId;
    private String serviceDetails;
    private int quantity;
    private float costPerUnit;
    private float amountPayable;
    private float balance;
    private LocalDateTime dateOfPayment;
    private LocalDate dateOfService;
    private String deliveryDetails;
    private String emailId;
	public Long getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(Long invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public Long getEstimateId() {
		return estimateId;
	}
	public void setEstimateId(Long estimateId) {
		this.estimateId = estimateId;
	}
	public Long getChainId() {
		return chainId;
	}
	public void setChainId(Long chainId) {
		this.chainId = chainId;
	}
	public String getServiceDetails() {
		return serviceDetails;
	}
	public void setServiceDetails(String serviceDetails) {
		this.serviceDetails = serviceDetails;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getCostPerUnit() {
		return costPerUnit;
	}
	public void setCostPerUnit(float costPerUnit) {
		this.costPerUnit = costPerUnit;
	}
	public float getAmountPayable() {
		return amountPayable;
	}
	public void setAmountPayable(float amountPayable) {
		this.amountPayable = amountPayable;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public LocalDateTime getDateOfPayment() {
		return dateOfPayment;
	}
	public void setDateOfPayment(LocalDateTime dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}
	public LocalDate getDateOfService() {
		return dateOfService;
	}
	public void setDateOfService(LocalDate dateOfService) {
		this.dateOfService = dateOfService;
	}
	public String getDeliveryDetails() {
		return deliveryDetails;
	}
	public void setDeliveryDetails(String deliveryDetails) {
		this.deliveryDetails = deliveryDetails;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
    
    

}
