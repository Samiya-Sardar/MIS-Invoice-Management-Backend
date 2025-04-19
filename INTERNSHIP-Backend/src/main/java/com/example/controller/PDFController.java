package com.example.controller;


import com.example.dto.PDFDTO;
import com.example.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/pdf")
public class PDFController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/sendInvoice")
    public String sendInvoiceEmail(@RequestBody PDFDTO request) {
        try {
            // Call the service to send the invoice PDF via email
            pdfService.sendInvoiceEmail(request.getRecipientEmail(), request.getSubject(), request.getInvoiceNo());
            return "Invoice sent successfully to " + request.getRecipientEmail();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send invoice email";
        }
    }

}
