import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './ManageGroup.css';
import Navbar from './Navbar';
import Footer from './Footer';

const ManageGroup = () => {
  const [groups, setGroups] = useState([]);
  const [newGroupName, setNewGroupName] = useState('');
  const [editedGroupName, setEditedGroupName] = useState('');
  const [isEditing, setIsEditing] = useState(null);

  // Fetch all groups
  const fetchGroups = async () => {
    try {
      const response = await axios.get('http://localhost:8080/groups/all');
      setGroups(response.data);
    } catch (error) {
      console.error('Error fetching groups:', error.response?.data || error.message);
    }
  };

  // Add new group
  const handleAddGroup = async () => {
    if (!newGroupName.trim()) {
      alert('Group name cannot be empty');
      return;
    }

    try {
      const response = await axios.post('http://localhost:8080/groups/addgroup', {
        groupName: newGroupName,
      });
      alert(response.data.message || 'Group added successfully');
      setNewGroupName('');
      fetchGroups();
    } catch (error) {
      alert(
        error.response?.data?.message ||
          error.response?.data ||
          'Error adding group'
      );
    }
  };

  // Handle Edit
  const handleEdit = (groupId, groupName) => {
    setIsEditing(groupId);
    setEditedGroupName(groupName);
  };

  // Handle Save
  const handleSave = async (groupId) => {
    if (!editedGroupName.trim()) {
      alert('New group name cannot be empty');
      return;
    }

    try {
      const response = await axios.patch(`http://localhost:8080/groups/update/${groupId}`, {
        newGroupName: editedGroupName,
      });

      alert(response.data.message || 'Group updated successfully');
      setIsEditing(null);
      setEditedGroupName('');
      fetchGroups();
    } catch (error) {
      alert(
        error.response?.data?.message ||
          error.response?.data ||
          'Error updating group'
      );
    }
  };

  // Handle Delete
  const handleDelete = async (groupId) => {
    if (window.confirm('Are you sure you want to delete this group?')) {
      try {
        const response = await axios.delete(`http://localhost:8080/groups/delete/${groupId}`);
        alert(response.data || 'Group deleted successfully');
        fetchGroups(); // Refresh the list after deletion
      } catch (error) {
        alert(
          error.response?.data?.message ||
            error.response?.data ||
            'Error deleting group'
        );
      }
    }
  };

  useEffect(() => {
    fetchGroups();
  }, []);

  return (
    <div className="MG">
      <Navbar />

      <div className="total-groups">Total Groups: {groups.length}</div>

      <div className="add-group-container">
        <h2 className="add-group-title">Add a New Group</h2>
        <input
          type="text"
          className="add-group-input"
          placeholder="Enter group name"
          value={newGroupName}
          onChange={(e) => setNewGroupName(e.target.value)}
        />
        <button className="add-group-btn" onClick={handleAddGroup}>
          <i className="fas fa-plus"></i>
        </button>
      </div>

      <div className="container">
        <table className="table table-dark table-bordered">
          <thead>
            <tr>
              <th>Sr.No</th>
              <th>Group Name</th>
              <th>Date Created</th>
              <th>Updated At</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {groups.map((group, index) => (
              <tr key={group.group_id || index}>
                <td>{index + 1}</td>
                <td>
                  {isEditing === group.group_id ? (
                    <input
                      type="text"
                      value={editedGroupName}
                      onChange={(e) => setEditedGroupName(e.target.value)}
                    />
                  ) : (
                    group.groupName
                  )}
                </td>
                <td>{group.created_at?.split('T')[0] || 'N/A'}</td>
                <td>{group.updated_at?.split('T')[0] || 'N/A'}</td>
                <td>
                  {group.is_active ? (
                    <i className="fas fa-check-circle" style={{ color: 'green' }}></i>
                  ) : (
                    <i className="fas fa-times-circle" style={{ color: 'red' }}></i>
                  )}
                </td>
                <td className="action-icons">
                  {isEditing === group.group_id ? (
                    <i
                      className="fas fa-arrow-right"
                      onClick={() => handleSave(group.group_id)}
                    ></i>
                  ) : (
                    <i
                      className="fas fa-pen-to-square"
                      onClick={() => handleEdit(group.group_id, group.groupName)}
                    ></i>
                  )}
                  <i
                    className="fas fa-trash"
                    onClick={() => handleDelete(group.group_id)}
                  ></i>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <Footer />
    </div>
  );
};

export default ManageGroup;
