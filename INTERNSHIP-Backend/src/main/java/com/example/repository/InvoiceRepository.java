package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.example.entity.Brand;
import com.example.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
	@Query(value = "CALL Displayinvoice()", nativeQuery = true)
    List<Invoice> getAllInvoices();
	
	Invoice findByInvoiceNo(Long invoiceNo);
	
	@Query(value = "CALL SearchInvoiceByNumber(:invoiceNo)", nativeQuery = true)
    List<Invoice> searchInvoiceByNumber(@Param("invoiceNo") Long invoiceNo);
	
	@Procedure(name = "updateInvoiceEmail")
    String updateInvoiceEmail(@Param("p_invoice_no") Long invoiceNo,
                              @Param("p_email_id") String emailId);

}
