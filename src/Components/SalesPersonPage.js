import React from 'react'
import './SalesPerson.css'
import Navbar from './Navbar'
import Footer from './Footer';
import { Link } from 'react-router-dom';



const SalesPersonPage = () => {
  return (
   
     
<div className="SP">
<Navbar />

  <div class="dashboard">
    <div class="card">
      <i class="fas fa-users"></i>
      <h3>Manage Groups</h3>
      <p>Create and manage business groups across regions.</p>
      <Link  to="/ManageGroups"><i class="fas fa-arrow-circle-right"></i></Link>
    </div>
    <div class="card">
      <i class="fas fa-link"></i>
      <h3>Manage Chain</h3>
      <p>Link and organize chains under group hierarchies.</p>
      <Link to="/ManageChains"><i class="fas fa-arrow-circle-right"></i></Link>
    </div>
    <div class="card">
      <i class="fas fa-tags"></i>
      <h3>Manage Brands</h3>
      <p>Add or edit product brands within chains.</p>
      <Link to="/ManageBrands"><i class="fas fa-arrow-circle-right"></i></Link>
    </div>
    <div class="card">
      <i class="fas fa-map-marked-alt"></i>
      <h3>Manage SubZones</h3>
      <p>Define sub-regions for brand operations.</p>
      <Link to="/ManageZone"><i class="fas fa-arrow-circle-right"></i></Link>
    </div>
    <div class="card">
      <i class="fas fa-file-invoice-dollar"></i>
      <h3>Manage Estimate</h3>
      <p>Generate and handle cost estimations per brand.</p>
      <Link to="/ManageEstimate"><i class="fas fa-arrow-circle-right"></i></Link>
    </div>
    <div class="card">
      <i class="fas fa-file-invoice"></i>
      <h3>Manage Invoices</h3>
      <p>View, create and send invoice records instantly.</p>
      <Link to="/ManageInvoice"><i class="fas fa-arrow-circle-right"></i></Link>
    </div>
  </div>
<Footer />

  </div>
  )
}

export default SalesPersonPage