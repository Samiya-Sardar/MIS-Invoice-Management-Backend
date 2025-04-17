import React, { useState, useEffect } from 'react';
import './ManageChains.css';
import Navbar from './Navbar';
import Footer from './Footer';

const ManageChains = () => {
  const [isPanelActive, setIsPanelActive] = useState(false);
  const [isEditMode, setIsEditMode] = useState(false);
  const [editChainId, setEditChainId] = useState(null);
  const [companyName, setCompanyName] = useState('');
  const [gstNo, setGstNo] = useState('');
  const [group, setGroup] = useState('');
  const [chains, setChains] = useState([]);
  const [groups, setGroups] = useState([]);
  const [filteredChains, setFilteredChains] = useState([]);

  const togglePanel = () => {
    setIsPanelActive(!isPanelActive);
    if (isPanelActive) {
      setCompanyName('');
      setGstNo('');
      setGroup('');
      setIsEditMode(false);
      setEditChainId(null);
    }
  };

  const handleCompanyNameChange = (e) => setCompanyName(e.target.value);
  const handleGstNoChange = (e) => setGstNo(e.target.value);
  const handleGroupChange = (e) => {
    setGroup(e.target.value);
    filterChainsByGroup(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (isEditMode) {
      const updateData = {
        chainId: editChainId,
        companyName: companyName,
        gstNo: gstNo,
        groupName: group,
      };

      fetch('http://localhost:8080/chains/update', {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updateData),
      })
        .then((response) => response.text())
        .then((data) => {
          alert(data);
          if (data.includes('success')) {
            togglePanel();
          }
        })
        .catch((error) => {
          console.error('Error:', error);
          alert('Error updating company.');
        });

    } else {
      const companyData = {
        company_name: companyName,
        gst_no: gstNo,
        group: group,
      };

      fetch('http://localhost:8080/chains/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(companyData),
      })
        .then((response) => response.text())
        .then((data) => {
          alert(data);
          if (data.includes('success')) {
            togglePanel();
          }
        })
        .catch((error) => {
          console.error('Error:', error);
          alert('Error adding company.');
        });
    }
  };

  const filterChainsByGroup = (selectedGroup) => {
    if (selectedGroup === '') {
      setFilteredChains(chains);
    } else {
      const filtered = chains.filter(chain => chain.group.group_name === selectedGroup);
      setFilteredChains(filtered);
    }
  };

  const handleEditClick = (chain) => {
    setCompanyName(chain.company_name);
    setGstNo(chain.gst_no);
    setGroup(chain.group.group_name);
    setEditChainId(chain.chain_id);
    setIsEditMode(true);
    setIsPanelActive(true);
  };

  useEffect(() => {
    fetch('http://localhost:8080/groups/allgroups')
      .then((response) => response.json())
      .then((data) => setGroups(data))
      .catch((error) => console.error('Error fetching groups:', error));

    fetch('http://localhost:8080/chains/all')
      .then((response) => response.json())
      .then((data) => {
        setChains(data);
        setFilteredChains(data);
      })
      .catch((error) => console.error('Error fetching chains:', error));
  }, []);

  return (
    <>
      <Navbar />

      <div className="container my-4">
        <h3 className="mb-4 text-info">Manage Chain Section</h3>

        <div className="row g-4 mb-4">
          <div className="col-md-3">
            <div className="card card-stats p-3">
              <h5>Total Groups</h5>
              <h2>3</h2>
            </div>
          </div>
          <div className="col-md-3">
            <div className="card card-stats p-3">
              <h5>Total Chains</h5>
              <h2>{chains.length}</h2>
            </div>
          </div>
        </div>

        <div className="d-flex justify-content-between align-items-center mb-3">
          <div className="d-flex gap-2">
            <button className="btn btn-neon" onClick={togglePanel}><i className="bi bi-plus-circle"></i> Add Company</button>
            <div className="dropdown">
              <button className="btn btn-neon dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                <i className="bi bi-funnel-fill"></i> Filter by Group
              </button>
              <ul className="dropdown-menu">
                {groups.map((group, index) => (
                  <li key={index}><a className="dropdown-item" href="#" onClick={() => handleGroupChange({ target: { value: group } })}>{group}</a></li>
                ))}
                <li><a className="dropdown-item" href="#" onClick={() => handleGroupChange({ target: { value: '' } })}>All</a></li>
              </ul>
            </div>
          </div>
        </div>

        <div className="table-responsive">
          <table className="table table-dark table-hover">
            <thead>
              <tr>
                <th>Sr.No</th>
                <th>Group Name</th>
                <th>Company</th>
                <th>GSTN</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredChains.map((chain, index) => (
                <tr key={chain.chain_id}>
                  <td>{index + 1}</td>
                  <td>{chain.group.group_name}</td>
                  <td>{chain.company_name}</td>
                  <td>{chain.gst_no}</td>
                  <td>
                    <i className="bi bi-pencil-square action-icon" onClick={() => handleEditClick(chain)}></i>
                    <i
                      className="bi bi-trash action-icon"
                      onClick={() => {
                        if (window.confirm('Are you sure you want to delete this chain?')) {
                          fetch(`http://localhost:8080/chains/delete/${chain.chain_id}`, {
                            method: 'DELETE'
                          })
                            .then((response) => response.text())
                            .then((data) => {
                              alert(data);
                              setChains(prev => prev.filter(c => c.chain_id !== chain.chain_id));
                              setFilteredChains(prev => prev.filter(c => c.chain_id !== chain.chain_id));
                            })
                            .catch((error) => {
                              console.error('Error deleting chain:', error);
                              alert('Error deleting chain.');
                            });
                        }
                      }}
                    ></i>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        
      </div>
      <Footer />

      {/* Add/Edit Company Panel */}
      <div id="slidePanel" className={`slide-panel ${isPanelActive ? 'active' : ''}`}>
        <button className="close-panel" onClick={togglePanel}>&times;</button>
        <h5 className="mb-4">{isEditMode ? 'Edit Company' : 'Add Company'}</h5>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label text-info">Enter Company Name</label>
            <input 
              type="text" 
              className="form-control bg-dark text-light border-info" 
              value={companyName} 
              onChange={handleCompanyNameChange} required
            />
          </div>
          <div className="mb-3">
            <label className="form-label text-info">Enter GSTN Number</label>
            <input 
              type="text" 
              className="form-control bg-dark text-light border-info" 
              value={gstNo} 
              onChange={handleGstNoChange} required
            />
          </div>
          <div className="mb-3">
            <label className="form-label text-info">Select Group</label>
            <select 
              className="form-select bg-dark text-light border-info" 
              value={group} 
              onChange={handleGroupChange}
            >
              <option disabled>Select Group</option>
              {groups.map((group, index) => (
                <option key={index} value={group}>{group}</option>
              ))}
            </select>
          </div>
          <button type="submit" className="btn btn-neon w-100">{isEditMode ? 'Update' : 'Submit'}</button>
        </form>
      </div>
    </>
  );
};

export default ManageChains;
