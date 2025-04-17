import React, { useEffect, useState } from 'react';
import Navbar from './Navbar';
import Footer from './Footer';
import './ManageBrands.css';
import axios from 'axios';

const ManageBrands = () => {
  const [brands, setBrands] = useState([]);
  const [brandName, setBrandName] = useState('');
  const [selectedCompany, setSelectedCompany] = useState('');
  const [companyOptions, setCompanyOptions] = useState([]);
  const [groupOptions, setGroupOptions] = useState([]);
  const [filterCompany, setFilterCompany] = useState('');
  const [filterGroup, setFilterGroup] = useState('');

  const [editBrandName, setEditBrandName] = useState('');
  const [editCompany, setEditCompany] = useState('');
  const [editBrandId, setEditBrandId] = useState(null);

  useEffect(() => {
    axios.get('http://localhost:8080/brands/all')
      .then(response => {
        setBrands(response.data);
      })
      .catch(error => {
        console.error('Error fetching brands:', error);
      });

    axios.get('http://localhost:8080/chains/allcompany')
      .then(response => {
        setCompanyOptions(response.data);
      })
      .catch(error => {
        console.error('Error fetching company options:', error);
      });

    axios.get('http://localhost:8080/groups/allgroups')
      .then(response => {
        setGroupOptions(response.data);
      })
      .catch(error => {
        console.error('Error fetching group options:', error);
      });
  }, []);

  const toggleAddPanel = () => {
    document.getElementById('addPanel').classList.toggle('active');
  };

  const toggleEditPanel = (brand) => {
    setEditBrandId(brand.brand_id);
    setEditBrandName(brand.brand_name);
    setEditCompany(brand.chain.company_name);
    document.getElementById('editPanel').classList.toggle('active');
  };

  const handleAddBrand = (e) => {
    e.preventDefault();

    const data = {
      brand_name: brandName,
      chain: selectedCompany
    };

    axios.post('http://localhost:8080/brands/add', data, {
      headers: { 'Content-Type': 'application/json' }
    })
      .then(response => {
        alert(response.data);
        setBrandName('');
        setSelectedCompany('');
        toggleAddPanel();
        return axios.get('http://localhost:8080/brands/all');
      })
      .then(response => {
        setBrands(response.data);
      })
      .catch(error => {
        console.error('Error adding brand:', error);
        alert('Failed to add brand.');
      });
  };

  const handleUpdateBrand = (e) => {
    e.preventDefault();

    const data = {
      brand_id: editBrandId,
      brand_name: editBrandName,
      chain: editCompany
    };

    axios.patch('http://localhost:8080/brands/update', data, {
      headers: { 'Content-Type': 'application/json' }
    })
      .then(response => {
        alert(response.data);
        document.getElementById('editPanel').classList.remove('active');
        return axios.get('http://localhost:8080/brands/all');
      })
      .then(response => {
        setBrands(response.data);
      })
      .catch(error => {
        console.error('Error updating brand:', error);
        alert('Failed to update brand.');
      });
  };

  const handleDeleteBrand = (brandId) => {
    axios.delete(`http://localhost:8080/brands/delete/${brandId}`)
      .then(response => {
        alert(response.data);
        return axios.get('http://localhost:8080/brands/all');
      })
      .then(response => {
        setBrands(response.data);
      })
      .catch(error => {
        console.error('Error deleting brand:', error);
        alert('Failed to delete brand.');
      });
  };

  const filteredBrands = brands.filter(brand => {
    const matchCompany = filterCompany ? brand.chain.company_name === filterCompany : true;
    const matchGroup = filterGroup ? brand.chain.group.group_name === filterGroup : true;
    return matchCompany && matchGroup;
  });

  return (
    <div>
      <Navbar />
      <div className="container my-4">
        <h3 className="mb-4 text-info">Manage Brand Section</h3>

        <div className="row g-4 mb-4">
          <div className="col-md-4">
            <div className="card brand-stat-card p-3">
              <h5>Total Groups</h5>
              <h2>{[...new Set(brands.map(b => b.chain.group.group_name))].length}</h2>
            </div>
          </div>
          <div className="col-md-4">
            <div className="card brand-stat-card p-3">
              <h5>Total Chains</h5>
              <h2>{[...new Set(brands.map(b => b.chain.company_name))].length}</h2>
            </div>
          </div>
          <div className="col-md-4">
            <div className="card brand-stat-card p-3">
              <h5>Total Brands</h5>
              <h2>{brands.length}</h2>
            </div>
          </div>
        </div>

        <div className="d-flex justify-content-between align-items-center mb-3">
          <div className="icon-action" onClick={toggleAddPanel}>
            <i className="bi bi-plus-circle"></i> Add Brand
          </div>
          <div className="d-flex gap-2">
            <select
              className="form-select bg-dark text-light border-info"
              value={filterCompany}
              onChange={(e) => setFilterCompany(e.target.value)}
            >
              <option value="">All Companies</option>
              {companyOptions.map((company, index) => (
                <option key={index} value={company}>{company}</option>
              ))}
            </select>
            <select
              className="form-select bg-dark text-light border-info"
              value={filterGroup}
              onChange={(e) => setFilterGroup(e.target.value)}
            >
              <option value="">All Groups</option>
              {groupOptions.map((group, index) => (
                <option key={index} value={group}>{group}</option>
              ))}
            </select>
          </div>
        </div>

        <div className="table-responsive">
          <table className="table table-dark table-hover manage-brand-table">
            <thead>
              <tr>
                <th>Sr.No</th>
                <th>Group</th>
                <th>Company</th>
                <th>Brand</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredBrands.map((brand, index) => (
                <tr key={brand.brand_id}>
                  <td>{index + 1}</td>
                  <td>{brand.chain.group.group_name}</td>
                  <td>{brand.chain.company_name}</td>
                  <td>{brand.brand_name}</td>
                  <td>
                    <i className="bi bi-pencil-square icon-action" onClick={() => toggleEditPanel(brand)}></i>
                    <i className="bi bi-trash icon-action" onClick={() => handleDeleteBrand(brand.brand_id)}></i>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {/* Add Panel */}
      <div id="addPanel" className="brand-slide-panel">
        <button className="close-panel" onClick={toggleAddPanel}>&times;</button>
        <h5 className="mb-4">Add Brand</h5>
        <form onSubmit={handleAddBrand}>
          <div className="mb-3">
            <label className="form-label text-info">Brand Name</label>
            <input
              type="text"
              className="form-control bg-dark text-light border-info"
              value={brandName}
              onChange={(e) => setBrandName(e.target.value)}
              required
            />
          </div>
          <div className="mb-3">
            <label className="form-label text-info">Select Company</label>
            <select
              className="form-select bg-dark text-light border-info"
              value={selectedCompany}
              onChange={(e) => setSelectedCompany(e.target.value)}
              required
            >
              <option value="">-- Select --</option>
              {companyOptions.map((company, index) => (
                <option key={index} value={company}>{company}</option>
              ))}
            </select>
          </div>
          <button type="submit" className="btn btn-add-brand w-100">Add Brand</button>
        </form>
      </div>

      {/* Edit Panel */}
      <div id="editPanel" className="brand-slide-panel">
        <button className="close-panel" onClick={() => document.getElementById('editPanel').classList.remove('active')}>&times;</button>
        <h5 className="mb-4">Edit Brand</h5>
        <form onSubmit={handleUpdateBrand}>
          <div className="mb-3">
            <label className="form-label text-info">Brand Name</label>
            <input
              type="text"
              className="form-control bg-dark text-light border-info"
              value={editBrandName}
              onChange={(e) => setEditBrandName(e.target.value)}
              required
            />
          </div>
          <div className="mb-3">
            <label className="form-label text-info">Select Company</label>
            <select
              className="form-select bg-dark text-light border-info"
              value={editCompany}
              onChange={(e) => setEditCompany(e.target.value)}
              required
            >
              <option value="">-- Select --</option>
              {companyOptions.map((company, index) => (
                <option key={index} value={company}>{company}</option>
              ))}
            </select>
          </div>
          <button type="submit" className="btn btn-add-brand w-100">Update Brand</button>
        </form>
      </div>

      <Footer />
    </div>
  );
};

export default ManageBrands;
