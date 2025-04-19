package com.example.dto;

public class EmailDTO {
	private String invoiceNumber;
    private String to;
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public EmailDTO(String invoiceNumber, String to) {
		super();
		this.invoiceNumber = invoiceNumber;
		this.to = to;
	}
    
    
    

}
