import React, { useEffect, useState } from 'react';
import Navbar from './Navbar';
import Footer from './Footer';
import './ManageInvoice.css';

const ManageInvoice = () => {
  const [invoices, setInvoices] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedInvoice, setSelectedInvoice] = useState(null);

  useEffect(() => {
    if (searchTerm.trim() === '') {
      // Fetch all invoices when search is empty
      fetch('http://localhost:8080/invoice/all')
        .then(res => res.json())
        .then(data => setInvoices(data))
        .catch(err => console.error('Error fetching invoices:', err));
    } else {
      const invoiceNo = parseInt(searchTerm);
      if (!isNaN(invoiceNo)) {
        fetch(`http://localhost:8080/invoice/search/${invoiceNo}`)
          .then(res => {
            if (res.ok) return res.json();
            else return [];
          })
          .then(data => setInvoices(data))
          .catch(err => {
            console.error('Error fetching invoice by number:', err);
            setInvoices([]);
          });
      }
    }
  }, [searchTerm]);

  const handleSearchChange = (e) => {
    setSearchTerm(e.target.value);
  };

  const handleEditClick = (invoiceNo) => {
    fetch(`http://localhost:8080/invoice/search/${invoiceNo}`)
      .then(res => res.json())
      .then(data => {
        setSelectedInvoice(data[0]); // Assuming one invoice per number
      })
      .catch(err => console.error('Error fetching invoice for editing:', err));
  };

  const handleEmailUpdate = (invoiceNo, newEmail) => {
    fetch('http://localhost:8080/invoice/updateemail', {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        invoiceNo: invoiceNo,
        emailId: newEmail,
      }),
    })
      .then(res => res.text()) // Expecting plain text response
      .then(response => {
        alert(response); // Show response as alert
      })
      .catch(err => {
        console.error('Error updating email:', err);
        alert('Error updating email');
      });
  };

  const handleDelete = (invoiceNo) => {
    if (window.confirm('Are you sure you want to delete this invoice?')) {
      fetch(`http://localhost:8080/invoice/delete/${invoiceNo}`, {
        method: 'DELETE',
      })
        .then(res => res.text())
        .then(response => {
          alert(response); // Show response from the backend
          // Reload invoices after deletion
          setInvoices(invoices.filter(inv => inv.invoiceNo !== invoiceNo));
        })
        .catch(err => {
          console.error('Error deleting invoice:', err);
          alert('Error deleting invoice');
        });
    }
  };

  const handleFormSubmit = (e) => {
    e.preventDefault();
    if (selectedInvoice) {
      const newEmail = e.target.emailId.value;
      handleEmailUpdate(selectedInvoice.invoiceNo, newEmail);
    }
  };

  return (
    <div>
      <Navbar />

      <div className="dashboard-body p-4">
        <div className="d-flex justify-content-start mb-4">
          <div className="dashboard-total-invoice">
            Total Invoice: {invoices.length}
          </div>
        </div>

        <div className="dashboard-search-wrapper">
          <div className="input-group">
            <span className="input-group-text"><i className="fas fa-search"></i></span>
            <input
              type="text"
              className="form-control dashboard-search-bar"
              placeholder="Search with invoice number"
              value={searchTerm}
              onChange={handleSearchChange}
            />
          </div>
        </div>

        <div className="dashboard-table-wrapper">
          <table className="table table-dark dashboard-table">
            <thead>
              <tr>
                <th>#</th>
                <th>Company</th>
                <th>Chain ID</th>
                <th>Estimate ID</th>
                <th>Service</th>
                <th>Qty</th>
                <th>Rate</th>
                <th>Total</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {invoices.map((inv, index) => (
                <tr key={inv.invoiceNo}>
                  <td>{inv.invoiceNo}</td>
                  <td>{inv.chain.company_name}</td>
                  <td>{inv.chain.chain_id}</td>
                  <td>{inv.estimate.estimateId}</td>
                  <td>{inv.serviceDetails}</td>
                  <td>{inv.quantity}</td>
                  <td>₹{inv.costPerUnit}</td>
                  <td>₹{inv.amountPayable}</td>
                  <td>
                    <i
                      className="fas fa-pen edit-icon"
                      data-bs-toggle="offcanvas"
                      data-bs-target="#editPanel"
                      onClick={() => handleEditClick(inv.invoiceNo)}
                    ></i>
                    <i
                      className="fas fa-trash trash-icon"
                      onClick={() => handleDelete(inv.invoiceNo)} // Call handleDelete on click
                    ></i>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Slide Panel */}
        <div className="offcanvas offcanvas-end dashboard-offcanvas" tabIndex="-1" id="editPanel">
          <div className="offcanvas-header">
            <h5>Edit Invoice</h5>
            <button type="button" className="btn-close text-reset bg-white" data-bs-dismiss="offcanvas"></button>
          </div>
          <div className="offcanvas-body">
            <form onSubmit={handleFormSubmit}>
              <div className="row g-3">
                <div className="col-md-4">
                  <label>Invoice No:</label>
                  <input type="text" className="form-control" value={selectedInvoice?.invoiceNo || ''} disabled />
                </div>
                <div className="col-md-4">
                  <label>Estimate ID:</label>
                  <input type="text" className="form-control" value={selectedInvoice?.estimate?.estimateId || ''} disabled />
                </div>
                <div className="col-md-4">
                  <label>Chain ID:</label>
                  <input type="text" className="form-control" value={selectedInvoice?.chain?.chain_id || ''} disabled />
                </div>

                <div className="col-md-6">
                  <label>Service Provided:</label>
                  <input type="text" className="form-control" value={selectedInvoice?.serviceDetails || ''} disabled />
                </div>
                <div className="col-md-3">
                  <label>Quantity:</label>
                  <input type="text" className="form-control" value={selectedInvoice?.quantity || ''} disabled />
                </div>
                <div className="col-md-3">
                  <label>Cost per Quantity:</label>
                  <input type="text" className="form-control" value={selectedInvoice?.costPerUnit || ''} disabled />
                </div>

                <div className="col-md-4">
                  <label>Amount Payable in Rs:</label>
                  <input type="text" className="form-control" value={selectedInvoice?.amountPayable || ''} disabled />
                </div>
                <div className="col-md-4">
                  <label>Amount Paid in Rs:</label>
                  <input type="text" className="form-control" value={selectedInvoice?.amountPayable || ''} disabled />
                </div>
                <div className="col-md-4">
                  <label>Balance in Rs:</label>
                  <input type="text" className="form-control" value={selectedInvoice?.balance || ''} disabled />
                </div>

                <div className="col-md-6">
                  <label>Delivery Date:</label>
                  <input type="text" className="form-control" value={selectedInvoice?.estimate?.deliveryDate || ''} disabled />
                </div>
                <div className="col-md-6">
                  <label>Enter Email ID:</label>
                  <input
                    type="email"
                    className="form-control"
                    name="emailId"
                    defaultValue={selectedInvoice?.emailId || ''}
                  />
                </div>
              </div>

              <button type="submit" className="btn btn-primary mt-3">Update Invoice</button>
            </form>
          </div>
        </div>
      </div>

      <Footer />
    </div>
  );
};

export default ManageInvoice;
