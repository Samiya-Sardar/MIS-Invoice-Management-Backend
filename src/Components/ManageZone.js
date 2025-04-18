import React, { useEffect, useState } from 'react'; 
import Navbar from './Navbar'; 
import Footer from './Footer'; 
import './ManageZone.css';

const ManageZone = () => { 
  const [zones, setZones] = useState([]); 
  const [brands, setBrands] = useState([]); 
  const [groups, setGroups] = useState([]); 
  const [chains, setChains] = useState([]); 
  const [selectedGroup, setSelectedGroup] = useState(''); 
  const [selectedChain, setSelectedChain] = useState(''); 
  const [selectedBrand, setSelectedBrand] = useState(''); 
  const [editingZoneId, setEditingZoneId] = useState(null); 

  useEffect(() => { 
    fetch('http://localhost:8080/zones/all') 
      .then((res) => res.json()) 
      .then((data) => setZones(data)) 
      .catch((err) => console.error('Failed to fetch zones:', err)); 
  }, []);

  useEffect(() => { 
    fetch('http://localhost:8080/brands/allbrand') 
      .then((res) => res.json()) 
      .then((data) => setBrands(data)) 
      .catch((err) => console.error('Failed to fetch brands:', err));

    fetch('http://localhost:8080/groups/allgroups') 
      .then((res) => res.json()) 
      .then((data) => setGroups(data)) 
      .catch((err) => console.error('Failed to fetch groups:', err));

    fetch('http://localhost:8080/chains/allcompany') 
      .then((res) => res.json()) 
      .then((data) => setChains(data)) 
      .catch((err) => console.error('Failed to fetch chains:', err)); 
  }, []);

  const filteredZones = zones.filter((zone) => { 
    const matchGroup = selectedGroup ? zone.brand?.chain?.group?.group_name === selectedGroup : true; 
    const matchChain = selectedChain ? zone.brand?.chain?.company_name === selectedChain : true; 
    const matchBrand = selectedBrand ? zone.brand?.brand_name === selectedBrand : true; 
    return matchGroup && matchChain && matchBrand; 
  });

  const totalGroups = new Set(filteredZones.map(zone => zone.brand?.chain?.group?.group_name)).size; 
  const totalChains = new Set(filteredZones.map(zone => zone.brand?.chain?.company_name)).size; 
  const totalBrands = new Set(filteredZones.map(zone => zone.brand?.brand_name)).size; 
  const totalZones = filteredZones.length;

  const handleAddZone = (e) => {
    e.preventDefault();
    const zoneName = document.getElementById('addZoneName').value;
    const brand = document.getElementById('addBrandSelect').value;

    fetch('http://localhost:8080/zones/add', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ zone_name: zoneName, brand: brand }),
    })
      .then((res) => res.text())
      .then((data) => {
        alert(data);
        const addZoneSidebar = document.getElementById('addZoneSidebar');
        const bootstrapModal = new window.bootstrap.Offcanvas(addZoneSidebar);
        bootstrapModal.hide();
        window.location.reload();
      })
      .catch((err) => console.error('Failed to add zone:', err));
  };

  const handleUpdateZone = (zoneId) => {
    const zoneName = document.getElementById('editZoneName').value;
    const brand = document.getElementById('editBrandSelect').value;

    fetch('http://localhost:8080/zones/update', {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        zone_id: zoneId,
        zone_name: zoneName,
        brand: brand,
      }),
    })
      .then((res) => res.text())
      .then((data) => {
        alert(data);
        const editZoneSidebar = document.getElementById('editZoneSidebar');
        const bootstrapModal = new window.bootstrap.Offcanvas(editZoneSidebar);
        bootstrapModal.hide();
        window.location.reload();
      })
      .catch((err) => console.error('Failed to update zone:', err));
  };

  const handleDeleteZone = (zoneId) => {
    if (window.confirm("Are you sure you want to delete this zone?")) {
      fetch(`http://localhost:8080/zones/delete/${zoneId}`, { method: 'DELETE' })
        .then((res) => res.text())
        .then((data) => {
          alert(data); // Display the message returned from the API
          setZones(zones.filter((zone) => zone.zone_id !== zoneId)); // Update the state to remove the deleted zone
        })
        .catch((err) => console.error('Failed to delete zone:', err));
    }
  };
  

  return (
    <>
      <Navbar />

      <div className="container py-5">
        <h2 className="neon-text mb-4">Manage Zone Section</h2>

        <div className="stats-section mb-4">
          <div className="neon-box flex-fill">
            <div className="fw-bold">Total Groups</div>
            <div className="display-6">{totalGroups}</div>
          </div>
          <div className="neon-box flex-fill">
            <div className="fw-bold">Total Chains</div>
            <div className="display-6">{totalChains}</div>
          </div>
          <div className="neon-box flex-fill">
            <div className="fw-bold">Total Brands</div>
            <div className="display-6">{totalBrands}</div>
          </div>
          <div className="neon-box flex-fill">
            <div className="fw-bold">Total Zones</div>
            <div className="display-6">{totalZones}</div>
          </div>
        </div>

        <div className="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
          <span className="add-icon neon-hover" data-bs-toggle="offcanvas" data-bs-target="#addZoneSidebar">
            <i className="bi bi-plus-circle-fill"></i> Add Zone
          </span>
          <div className="filter-section d-flex gap-2">
            <select className="form-select" onChange={(e) => setSelectedGroup(e.target.value)} defaultValue="">
              <option disabled value="">Group</option>
              {groups.map((group, index) => (
                <option key={index} value={group}>{group}</option>
              ))}
            </select>
            <select className="form-select" onChange={(e) => setSelectedChain(e.target.value)} defaultValue="">
              <option disabled value="">Company</option>
              {chains.map((chain, index) => (
                <option key={index} value={chain}>{chain}</option>
              ))}
            </select>
            <select className="form-select" onChange={(e) => setSelectedBrand(e.target.value)} defaultValue="">
              <option disabled value="">Brand</option>
              {brands.map((brand, index) => (
                <option key={index} value={brand}>{brand}</option>
              ))}
            </select>
          </div>
        </div>

        <table className="table table-dark table-bordered table-hover align-middle">
          <thead>
            <tr>
              <th>Sr.No</th>
              <th>Zone</th>
              <th>Brand</th>
              <th>Company</th>
              <th>Group</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {filteredZones.map((zone, index) => (
              <tr key={zone.zone_id}>
                <td>{index + 1}</td>
                <td>{zone.zone_name}</td>
                <td>{zone.brand?.brand_name}</td>
                <td>{zone.brand?.chain?.company_name}</td>
                <td>{zone.brand?.chain?.group?.group_name}</td>
                <td>
                  <i className="bi bi-pencil-square me-2" data-bs-toggle="offcanvas" data-bs-target="#editZoneSidebar" style={{ cursor: 'pointer' }} onClick={() => {
                    document.getElementById('editZoneName').value = zone.zone_name;
                    document.getElementById('editBrandSelect').value = zone.brand?.brand_name;
                    setEditingZoneId(zone.zone_id);
                  }}></i>
                  <i className="bi bi-trash-fill" style={{ cursor: 'pointer' }} onClick={() => handleDeleteZone(zone.zone_id)}></i>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="offcanvas offcanvas-end" tabIndex="-1" id="addZoneSidebar">
        <div className="offcanvas-header">
          <h5 className="offcanvas-title">Add Zone</h5>
          <button type="button" className="btn-close btn-close-white" data-bs-dismiss="offcanvas"></button>
        </div>
        <div className="offcanvas-body">
          <form onSubmit={handleAddZone}>
            <div className="mb-3">
              <label htmlFor="addZoneName" className="form-label">Zone Name</label>
              <input type="text" className="form-control" id="addZoneName" placeholder="Enter zone name" required />
            </div>
            <div className="mb-3">
              <label htmlFor="addBrandSelect" className="form-label">Brand</label>
              <select className="form-select" id="addBrandSelect" defaultValue="">
                <option disabled value="">Select Brand</option>
                {brands.map((brand, index) => (
                  <option key={index} value={brand}>{brand}</option>
                ))}
              </select>
            </div>
            <button type="submit" className="btn btn-edit w-100">Save Zone</button>
          </form>
        </div>
      </div>

      <div className="offcanvas offcanvas-end" tabIndex="-1" id="editZoneSidebar">
        <div className="offcanvas-header">
          <h5 className="offcanvas-title">Edit Zone</h5>
          <button type="button" className="btn-close btn-close-white" data-bs-dismiss="offcanvas"></button>
        </div>
        <div className="offcanvas-body">
          <form onSubmit={(e) => {
            e.preventDefault();
            if (editingZoneId !== null) {
              handleUpdateZone(editingZoneId);
            }
          }}>
            <div className="mb-3">
              <label htmlFor="editZoneName" className="form-label">Zone Name</label>
              <input type="text" className="form-control" id="editZoneName" placeholder="Edit zone name" required />
            </div>
            <div className="mb-3">
              <label htmlFor="editBrandSelect" className="form-label">Brand</label>
              <select className="form-select" id="editBrandSelect" defaultValue="">
                <option disabled value="">Select Brand</option>
                {brands.map((brand, index) => (
                  <option key={index} value={brand}>{brand}</option>
                ))}
              </select>
            </div>
            <button className="btn btn-edit w-100">Update Zone</button>
          </form>
        </div>
      </div>

      <Footer />
    </>
  );
};

export default ManageZone;
