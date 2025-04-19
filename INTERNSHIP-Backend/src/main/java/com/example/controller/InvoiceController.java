package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.InvoiceDTO;
import com.example.entity.ChainEntity;
import com.example.entity.Invoice;
import com.example.service.ChainService;
import com.example.service.InvoiceService;
import com.example.service.PdfService;

@RestController
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/invoice")
public class InvoiceController {
	@Autowired
    private InvoiceService invoiceService;
	
	@Autowired
    private PdfService pdfService;

    

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/all")
    public List<Invoice> getInvoices() {
        return invoiceService.fetchInvoices();
    }

    
    @PostMapping("/add")
    public ResponseEntity<String> addInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        try {
            invoiceService.addInvoice(invoiceDTO);
            return ResponseEntity.ok("Invoice added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding invoice: " + e.getMessage());
        }
    }
    
    @GetMapping("/search/{invoiceNo}")
    public ResponseEntity<List<Invoice>> getInvoiceDetails(@PathVariable Long invoiceNo) {
        List<Invoice> result = invoiceService.getInvoiceByNumber(invoiceNo);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
    
    @PatchMapping("/updateemail")
    public ResponseEntity<String> updateInvoiceEmail(@RequestBody InvoiceDTO request) {
        String result = invoiceService.updateEmail(request.getInvoiceNo(), request.getEmailId());
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/delete/{invoiceNo}")
    public ResponseEntity<String> deleteInvoice(@PathVariable long invoiceNo) {
        String resultMessage = invoiceService.deleteInvoice(invoiceNo);
        
        if ("Successfully deleted".equals(resultMessage)) {
            return ResponseEntity.ok(resultMessage);
        } else {
            return ResponseEntity.status(404).body(resultMessage);
        }
    }
    }
    

