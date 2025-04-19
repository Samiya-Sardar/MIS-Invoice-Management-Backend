package com.example.dto;

public class PDFDTO {
	 private String recipientEmail;
	    private String subject;
	    private Long invoiceNo;
		public String getRecipientEmail() {
			return recipientEmail;
		}
		public void setRecipientEmail(String recipientEmail) {
			this.recipientEmail = recipientEmail;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public Long getInvoiceNo() {
			return invoiceNo;
		}
		public void setInvoiceNo(Long invoiceNo) {
			this.invoiceNo = invoiceNo;
		}

}
