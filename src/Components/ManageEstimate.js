import React, { useEffect, useState } from 'react';
import './ManageEstimate.css';
import Navbar from './Navbar';
import Footer from './Footer';

const ManageEstimate = () => {
  const [estimates, setEstimates] = useState([]);
  const [groups, setGroups] = useState([]);
  const [brands, setBrands] = useState([]);
  const [chains, setChains] = useState([]);
  const [zones, setZones] = useState([]);

  const [formData, setFormData] = useState({
    estimateId: '',
    groupName: '',
    chainId: '',
    brandName: '',
    zoneName: '',
    service: '',
    quantity: '',
    costPerUnit: '',
    deliveryDate: '',
    deliveryDetails: ''
  });

  const [editMode, setEditMode] = useState(false);

  useEffect(() => {
    fetch('http://localhost:8080/estimate/all')
      .then((res) => res.json())
      .then(setEstimates)
      .catch((err) => console.error('Error fetching estimates:', err));

    fetch('http://localhost:8080/groups/all')
      .then((res) => res.json())
      .then(setGroups);

    fetch('http://localhost:8080/brands/all')
      .then((res) => res.json())
      .then(setBrands);

    fetch('http://localhost:8080/chains/all')
      .then((res) => res.json())
      .then(setChains);

    fetch('http://localhost:8080/zones/all')
      .then((res) => res.json())
      .then(setZones);
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const payload = {
      estimateId: parseInt(formData.estimateId),
      groupName: formData.groupName,
      chainId: parseInt(formData.chainId),
      brandName: formData.brandName,
      zoneName: formData.zoneName,
      service: formData.service,
      quantity: parseFloat(formData.quantity),
      costPerUnit: parseFloat(formData.costPerUnit),
      deliveryDate: formData.deliveryDate,
      deliveryDetails: formData.deliveryDetails
    };

    const url = editMode ? 'http://localhost:8080/estimate/update' : 'http://localhost:8080/estimate/add';
    const method = editMode ? 'PATCH' : 'POST';

    fetch(url, {
      method: method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    })
      .then((res) => res.text())
      .then(() => {
        alert(editMode ? 'Estimate updated successfully!' : 'Estimate added successfully!');
        window.location.reload();
      })
      .catch((err) => console.error('Error saving estimate:', err));
  };

  const handleEditClick = (estimate) => {
    setFormData({
      estimateId: estimate.estimateId,
      groupName: estimate.chain.group.group_name,
      chainId: estimate.chain.chain_id,
      brandName: estimate.brandName,
      zoneName: estimate.zoneName,
      service: estimate.service,
      quantity: estimate.quantity,
      costPerUnit: estimate.costPerUnit,
      deliveryDate: estimate.deliveryDate,
      deliveryDetails: estimate.deliveryDetails
    });
    setEditMode(true);
  };

  const handleDeleteClick = (estimateId) => {
    if (window.confirm('Are you sure you want to delete this estimate?')) {
      fetch(`http://localhost:8080/estimate/delete/${estimateId}`, {
        method: 'DELETE',
      })
        .then(() => {
          alert('Estimate deleted successfully!');
          setEstimates(estimates.filter((estimate) => estimate.estimateId !== estimateId));
        })
        .catch((err) => console.error('Error deleting estimate:', err));
    }
  };

  return (
    <div>
      <Navbar />
      <div className="container py-4">
        <div className="d-flex justify-content-between align-items-center mb-4">
          <h2 className="section-header">Estimate Management</h2>
        </div>

        <div className="row mb-4">
          <div className="col-md-4">
            <div className="neon-card">
              <h5>Total Estimate</h5>
              <h2>{estimates.length}</h2>
            </div>
          </div>
          <div className="col-md-4">
            <select className="form-select neon-select" aria-label="Filter by Group">
              <option selected disabled>Filter by Group</option>
              {groups.map((group) => (
                <option key={group.group_id} value={group.groupName}>
                  {group.groupName}
                </option>
              ))}
            </select>
          </div>
          <div className="col-md-4">
            <select className="form-select neon-select" aria-label="Filter by Brand">
              <option selected disabled>Filter by Brand</option>
              {brands.map((brand) => (
                <option key={brand.brand_id} value={brand.brand_name}>
                  {brand.brand_name}
                </option>
              ))}
            </select>
          </div>
        </div>

        <div className="d-flex justify-content-end mb-3">
          <div
            className="create-estimate-icon"
            data-bs-toggle="offcanvas"
            data-bs-target="#createPanel"
            role="button"
            onClick={() => {
              setEditMode(false);
              setFormData({
                estimateId: '',
                groupName: '',
                chainId: '',
                brandName: '',
                zoneName: '',
                service: '',
                quantity: '',
                costPerUnit: '',
                deliveryDate: '',
                deliveryDetails: ''
              });
            }}
          >
            <i className="bi bi-plus-circle me-2"></i>
            Create Estimate
          </div>
        </div>

        <div className="table-responsive">
          <table className="table table-dark table-striped align-middle">
            <thead>
              <tr>
                <th>Sr.No</th>
                <th>Group</th>
                <th>Chain ID</th>
                <th>Brand</th>
                <th>Zone</th>
                <th>Service Details</th>
                <th>Total Units</th>
                <th>Price Per Unit</th>
                <th>Total</th>
                <th>Edit</th>
                <th>Delete</th>
              </tr>
            </thead>
            <tbody>
              {estimates.map((estimate, index) => (
                <tr key={estimate.estimateId}>
                  <td>{index + 1}</td>
                  <td>{estimate.chain.group.group_name}</td>
                  <td>{estimate.chain.chain_id}</td>
                  <td>{estimate.brandName}</td>
                  <td>{estimate.zoneName}</td>
                  <td>{estimate.service}</td>
                  <td>{estimate.quantity}</td>
                  <td>{estimate.costPerUnit}</td>
                  <td>{estimate.totalCost}</td>
                  <td className="table-action">
                    <i
                      className="bi bi-pencil-square"
                      data-bs-toggle="offcanvas"
                      data-bs-target="#createPanel"
                      onClick={() => handleEditClick(estimate)}
                    ></i>
                  </td>
                  <td className="table-action">
                    <i
                      className="bi bi-trash"
                      onClick={() => handleDeleteClick(estimate.estimateId)}
                    ></i>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {/* Create/Edit Panel */}
      <div className="offcanvas offcanvas-end" tabIndex="-1" id="createPanel">
        <div className="offcanvas-header">
          <h5 className="offcanvas-title">{editMode ? 'Edit Estimate' : 'Create Estimate'}</h5>
          <button type="button" className="btn-close btn-close-white" data-bs-dismiss="offcanvas"></button>
        </div>
        <div className="offcanvas-body">
          <form onSubmit={handleSubmit}>
            <label>Select Group:</label>
            <select className="form-select" name="groupName" value={formData.groupName} onChange={handleChange}>
              <option disabled selected>Select Group</option>
              {groups.map((group) => (
                <option key={group.group_id} value={group.groupName}>
                  {group.groupName}
                </option>
              ))}
            </select>

            <label>Select Chain ID or Company Name:</label>
            <select className="form-select" name="chainId" value={formData.chainId} onChange={handleChange}>
              <option disabled selected>Select Chain</option>
              {chains.map((chain) => (
                <option key={chain.chain_id} value={chain.chain_id}>
                  {chain.company_name}
                </option>
              ))}
            </select>

            <label>Select Brand:</label>
            <select className="form-select" name="brandName" value={formData.brandName} onChange={handleChange}>
              <option disabled selected>Select Brand</option>
              {brands.map((brand) => (
                <option key={brand.brand_id} value={brand.brand_name}>
                  {brand.brand_name}
                </option>
              ))}
            </select>

            <label>Select Zone:</label>
            <select className="form-select" name="zoneName" value={formData.zoneName} onChange={handleChange}>
              <option disabled selected>Select Zone</option>
              {zones.map((zone) => (
                <option key={zone.zone_id} value={zone.zone_name}>
                  {zone.zone_name}
                </option>
              ))}
            </select>

            <label>Service Provided:</label>
            <input type="text" className="form-control" name="service" value={formData.service} onChange={handleChange} />

            <label>Total Quantity:</label>
            <input type="number" className="form-control" name="quantity" value={formData.quantity} onChange={handleChange} />

            <label>Cost Per Quantity:</label>
            <input type="number" className="form-control" name="costPerUnit" value={formData.costPerUnit} onChange={handleChange} />

            <label>Estimated Amount in Rs:</label>
            <input type="number" className="form-control" value={(formData.quantity * formData.costPerUnit || 0).toFixed(2)} disabled />

            <label>Expected Delivery Date:</label>
            <input type="date" className="form-control" name="deliveryDate" value={formData.deliveryDate} onChange={handleChange} />

            <label>Other Delivery Details:</label>
            <textarea className="form-control" rows="3" name="deliveryDetails" value={formData.deliveryDetails} onChange={handleChange}></textarea>

            <button type="submit" className="btn glow-btn mt-3 w-100">{editMode ? 'Update Estimate' : 'Save Estimate'}</button>
          </form>
        </div>
      </div>

      <Footer />
    </div>
  );
};

export default ManageEstimate;
