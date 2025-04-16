import React, { useEffect, useState } from 'react'
import './Navbar.css'
import { useNavigate } from 'react-router-dom';

const Navbar = () => {
    const [fullName,setFullName]=useState('');
    const navigate=useNavigate();

    useEffect(()=>{
        const storedName=localStorage.getItem('fullname');
        if (storedName){
            setFullName(storedName);
        }
    },[]);

    const handleLogout=()=>{
        localStorage.clear();
        alert("Logged Out Sucessfully")
        navigate('/',{ replace: true });
    };

  return (
    <div class="navbar">
    <h1>Invoice System</h1>
    <div class="navbar-right">
      <span>Hello, {fullName || 'User'} </span>
      <span class="logout-icon" onClick={handleLogout}>
        <i class="fas fa-sign-out-alt"></i>
      </span>
    </div>
  </div>
  )
}

export default Navbar