import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom'; 
import Navbar from './Navbar';
import Footer from './Footer';
import './InvoiceGeneration.css';

const InvoiceGeneration = () => {
  const { estimateId } = useParams(); 
  const navigate = useNavigate(); 
  const [estimate, setEstimate] = useState(null);
  const [email, setEmail] = useState(localStorage.getItem('email') || ''); 
  const [invoiceNo] = useState(Math.floor(1000 + Math.random() * 9000)); 

  useEffect(() => {
    fetch(`http://localhost:8080/estimate/details/${estimateId}`)
      .then((response) => response.json())
      .then((data) => {
        console.log("Fetched estimate:", data); 
        if (Array.isArray(data) && data.length > 0) {
          setEstimate(data[0]);
        } else if (data && typeof data === 'object') {
          setEstimate(data);
        } else {
          console.error("No estimate data found");
        }
      })
      .catch((error) => console.error("Error fetching estimate:", error));
  }, [estimateId]);

  if (!estimate) {
    return <div>Loading...</div>;
  }

  const {
    chainId,
    groupName,
    brandName,
    zoneName,
    service,
    quantity,
    costPerUnit,
    totalCost,
    deliveryDate,
    deliveryDetails,
  } = estimate;

  const amountPayable = totalCost;
  const amountPaid = totalCost;
  const balance = amountPayable - amountPaid;

  const handleGenerateInvoice = (e) => {
    e.preventDefault();
    console.log("Generate Invoice clicked. Email:", email);

    
    const invoiceData = {
      invoiceNo: invoiceNo,
      estimateId: estimateId,
      chainId: chainId,
      serviceDetails: service,
      quantity: quantity,
      costPerUnit: costPerUnit,
      amountPayable: amountPayable,
      balance: balance,
      dateOfPayment: new Date().toISOString(),
      dateOfService: deliveryDate,
      deliveryDetails: deliveryDetails,
      emailId: email,
    };

   
    fetch('http://localhost:8080/invoice/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(invoiceData),
    })
      .then((response) => response.text())
      .then((data) => {
        console.log("Response from API:", data);
        alert(data); 

        
        fetch('http://localhost:8080/pdf/sendInvoice', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            recipientEmail: email, 
            subject: 'Invoice Details',
            invoiceNo: invoiceNo,
          }),
        })
          .then((emailResponse) => emailResponse.text())
          .then((emailData) => {
            console.log("Email response:", emailData);
            alert("Invoice sent successfully to " + email); 

            
            navigate('/ManageEstimate');
          })
          .catch((emailError) => {
            console.error("Error sending email:", emailError);
            alert('Failed to send email');
          });
      })
      .catch((error) => {
        console.error("Error adding invoice:", error);
        alert('Failed to generate invoice');
      });

    localStorage.setItem('email', email); 
  };

  return (
    <div>
      <Navbar />
      <div className="container invoice-neon-container">
        <div className="invoice-neon-title">
          <i className="bi bi-receipt-cutoff"></i> Generate Invoice
        </div>
        <form onSubmit={handleGenerateInvoice}>
          <div className="row mb-3">
            <div className="col-md-4">
              <label className="form-label invoice-neon-form-label">Invoice No:</label>
              <input type="text" className="form-control invoice-neon-form-control" value={invoiceNo} disabled />
            </div>
            <div className="col-md-4">
              <label className="form-label invoice-neon-form-label">Estimate ID:</label>
              <input type="text" className="form-control invoice-neon-form-control" value={estimateId} disabled />
            </div>
            <div className="col-md-4">
              <label className="form-label invoice-neon-form-label">Chain ID:</label>
              <input type="text" className="form-control invoice-neon-form-control" value={chainId} disabled />
            </div>
          </div>

          <div className="row mb-3">
            <div className="col-md-6">
              <label className="form-label invoice-neon-form-label">Service Provided:</label>
              <input type="text" className="form-control invoice-neon-form-control" value={service} disabled />
            </div>
            <div className="col-md-3">
              <label className="form-label invoice-neon-form-label">Quantity:</label>
              <input type="text" className="form-control invoice-neon-form-control" value={quantity} disabled />
            </div>
            <div className="col-md-3">
              <label className="form-label invoice-neon-form-label">Cost per Quantity:</label>
              <input type="text" className="form-control invoice-neon-form-control" value={costPerUnit} disabled />
            </div>
          </div>

          <div className="row mb-3">
            <div className="col-md-4">
              <label className="form-label invoice-neon-form-label">Amount Payable in Rs:</label>
              <input type="text" className="form-control invoice-neon-form-control" value={amountPayable} disabled />
            </div>
            <div className="col-md-4">
              <label className="form-label invoice-neon-form-label">Amount Paid in Rs:</label>
              <input type="text" className="form-control invoice-neon-form-control" value={amountPaid} disabled />
            </div>
            <div className="col-md-4">
              <label className="form-label invoice-neon-form-label">Balance in Rs:</label>
              <input type="text" className="form-control invoice-neon-form-control" value={balance} disabled />
            </div>
          </div>

          <div className="row mb-3">
            <div className="col-md-6">
              <label className="form-label invoice-neon-form-label">Delivery Date:</label>
              <input type="text" className="form-control invoice-neon-form-control" value={deliveryDate} disabled />
            </div>
            <div className="col-md-6">
              <label className="form-label invoice-neon-form-label">Other Delivery Details:</label>
              <textarea
                className="form-control invoice-neon-form-control invoice-neon-textarea"
                value={deliveryDetails}
                disabled
              ></textarea>
            </div>
          </div>

          <div className="row mb-4">
            <div className="col-md-12">
              <label className="form-label invoice-neon-form-label">Enter Email ID:</label>
              <input
                type="email"
                className="form-control invoice-neon-form-control"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
          </div>

          <div className="text-center">
            <button type="submit" className="invoice-neon-btn">
              <i className="bi bi-printer"></i> Generate Invoice
            </button>
          </div>
        </form>
      </div>
      <Footer />
    </div>
  );
};

export default InvoiceGeneration;
