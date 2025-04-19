package com.example.service;

import com.example.entity.Invoice;
import com.example.repository.InvoiceRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.FontFactory;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private InvoiceRepository invoiceRepository;  // Inject the repository

    public void sendInvoiceEmail(String recipientEmail, String subject, Long invoiceNo) {
        try {
            // Fetch invoice details by invoiceNo using the repository
            Invoice invoice = findInvoiceByInvoiceNo(invoiceNo);
            if (invoice == null) {
                throw new Exception("Invoice not found for invoiceNo: " + invoiceNo);
            }

            // Generate PDF from invoice
            ByteArrayInputStream bis = generateInvoicePdf(invoice);

            // Set up email
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText("Dear Customer,\n\nPlease find your invoice attached.\n\nRegards,\nYour Company");

            // Attach the PDF
            helper.addAttachment("invoice_" + invoice.getInvoiceNo() + ".pdf", () -> bis);

            // Send email
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to fetch the invoice by its invoice number
    private Invoice findInvoiceByInvoiceNo(Long invoiceNo) {
        return invoiceRepository.findByInvoiceNo(invoiceNo);  // Use the repository method to fetch the invoice
    }

    private ByteArrayInputStream generateInvoicePdf(Invoice invoice) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            // Initialize PdfWriter for iText 5
            PdfWriter.getInstance(document, out);
            document.open();

            // Set up font for iText 5
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Invoice Details", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);

            // Add invoice details to the document
            document.add(new Paragraph("Invoice No: " + invoice.getInvoiceNo()));
            document.add(new Paragraph("Chain: " + invoice.getChain().getCompany_name()));
            document.add(new Paragraph("Service: " + invoice.getServiceDetails()));
            document.add(new Paragraph("Quantity: " + invoice.getQuantity()));
            document.add(new Paragraph("Cost/Unit: " + invoice.getCostPerUnit()));
            document.add(new Paragraph("Total: " + invoice.getAmountPayable()));
            document.add(new Paragraph("Balance: " + invoice.getBalance()));
            document.add(new Paragraph("Date of Payment: " + invoice.getDateOfPayment()));
            document.add(new Paragraph("Date of Service: " + invoice.getDateOfService()));
            document.add(new Paragraph("Delivery: " + invoice.getDeliveryDetails()));
            document.add(new Paragraph("Email: " + invoice.getEmailId()));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}


