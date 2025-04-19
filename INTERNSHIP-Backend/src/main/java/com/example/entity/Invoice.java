package com.example.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @Column(name = "invoice_no")
    private Long invoiceNo;

    @ManyToOne
    @JoinColumn(name = "estimate_id", nullable = false)
    private Estimate estimate;

    @ManyToOne
    @JoinColumn(name = "chain_id", nullable = false)
    private ChainEntity chain;

    @Column(name = "service_details")
    private String serviceDetails;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "cost_per_unit", nullable = false)
    private float costPerUnit;

    @Column(name = "amount_payable", nullable = false)
    private float amountPayable;

    @Column(name = "balance", nullable = false)
    private float balance;

    @Column(name = "date_of_payment", nullable = false)
    private LocalDateTime dateOfPayment;

    @Column(name = "date_of_service", nullable = false)
    private LocalDate dateOfService;

    @Column(name = "delivery_details", nullable = false, columnDefinition = "TEXT")
    private String deliveryDetails;

    @Column(name = "email_id", nullable = false)
    private String emailId;
    
    public  Invoice() {}

	public Long getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(Long invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Estimate getEstimate() {
		return estimate;
	}

	public void setEstimate(Estimate estimate) {
		this.estimate = estimate;
	}

	public ChainEntity getChain() {
		return chain;
	}

	public void setChain(ChainEntity chain) {
		this.chain = chain;
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

	public Invoice(Long invoiceNo, Estimate estimate, ChainEntity chain, String serviceDetails, int quantity,
			float costPerUnit, float amountPayable, float balance, LocalDateTime dateOfPayment, LocalDate dateOfService,
			String deliveryDetails, String emailId) {
		super();
		this.invoiceNo = invoiceNo;
		this.estimate = estimate;
		this.chain = chain;
		this.serviceDetails = serviceDetails;
		this.quantity = quantity;
		this.costPerUnit = costPerUnit;
		this.amountPayable = amountPayable;
		this.balance = balance;
		this.dateOfPayment = dateOfPayment;
		this.dateOfService = dateOfService;
		this.deliveryDetails = deliveryDetails;
		this.emailId = emailId;
	}
    
    
}

