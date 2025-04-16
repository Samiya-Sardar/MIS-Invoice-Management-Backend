import React from 'react'
import './SalesPerson.css'
import Navbar from './Navbar'
import { Link } from 'react-router-dom';
import Footer from './Footer';


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
      <a href="/chains"><i class="fas fa-arrow-circle-right"></i></a>
    </div>
    <div class="card">
      <i class="fas fa-tags"></i>
      <h3>Manage Brands</h3>
      <p>Add or edit product brands within chains.</p>
      <a href="/brands"><i class="fas fa-arrow-circle-right"></i></a>
    </div>
    <div class="card">
      <i class="fas fa-map-marked-alt"></i>
      <h3>Manage SubZones</h3>
      <p>Define sub-regions for brand operations.</p>
      <a href="/subzones"><i class="fas fa-arrow-circle-right"></i></a>
    </div>
    <div class="card">
      <i class="fas fa-file-invoice-dollar"></i>
      <h3>Manage Estimate</h3>
      <p>Generate and handle cost estimations per brand.</p>
      <a href="/estimates"><i class="fas fa-arrow-circle-right"></i></a>
    </div>
    <div class="card">
      <i class="fas fa-file-invoice"></i>
      <h3>Manage Invoices</h3>
      <p>View, create and send invoice records instantly.</p>
      <a href="/invoices"><i class="fas fa-arrow-circle-right"></i></a>
    </div>
  </div>
<Footer />

  </div>
  )
}

export default SalesPersonPage