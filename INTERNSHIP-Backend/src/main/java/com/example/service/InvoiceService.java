package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.example.dto.InvoiceDTO;
import com.example.entity.Invoice;
import com.example.repository.InvoiceRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> fetchInvoices() {
        return invoiceRepository.getAllInvoices();
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addInvoice(InvoiceDTO invoiceDTO) {
        String procedureCall = "{CALL addInvoice(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        jdbcTemplate.update(procedureCall,
                invoiceDTO.getInvoiceNo(),
                invoiceDTO.getEstimateId(),
                invoiceDTO.getChainId(),
                invoiceDTO.getServiceDetails(),
                invoiceDTO.getQuantity(),
                invoiceDTO.getCostPerUnit(),
                invoiceDTO.getAmountPayable(),
                invoiceDTO.getBalance(),
                invoiceDTO.getDateOfPayment(),
                invoiceDTO.getDateOfService(),
                invoiceDTO.getDeliveryDetails(),
                invoiceDTO.getEmailId());
    }
    

    public List<Invoice> getInvoiceByNumber(Long invoiceNo) {
        return invoiceRepository.searchInvoiceByNumber(invoiceNo);
    }
    
    @PersistenceContext
    private EntityManager entityManager;

    public String updateEmail(Long invoiceNo, String emailId) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("updateInvoiceEmail");

        // Register input and output parameters
        query.registerStoredProcedureParameter("p_invoice_no", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_email_id", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_message", String.class, ParameterMode.OUT);

        // Set input values
        query.setParameter("p_invoice_no", invoiceNo);
        query.setParameter("p_email_id", emailId);

        // Execute
        query.execute();

        // Get the output
        return (String) query.getOutputParameterValue("p_message");
    }
    
  

    public String deleteInvoice(long invoiceNo) {
        // Define the stored procedure call
        String sql = "{CALL DeleteInvoice(?, ?)}";
        
        // Execute the stored procedure
        return jdbcTemplate.execute(sql, new CallableStatementCallback<String>() {
            @Override
            public String doInCallableStatement(CallableStatement cs) throws SQLException {
                // Set input parameter
                cs.setLong(1, invoiceNo);
                // Register output parameter
                cs.registerOutParameter(2, java.sql.Types.VARCHAR);
                // Execute the stored procedure
                cs.execute();
                // Retrieve the output parameter (message)
                return cs.getString(2);
            }
        });

}
}
