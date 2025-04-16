import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './ResetPassword.css';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const ResetPassword = () => {
  const [fullName, setFullName] = useState('');
  const [email, setEmail] = useState('');
  const [password1, setPassword1] = useState('');
  const [password2, setPassword2] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
        console.log(email);
        console.log(fullName);
        console.log(password1);
        console.log(password2);
        const parvalue= {
            input_email: email.trim(),
            input_full_name: fullName.trim(),
            new_password1: password1.trim(),
            new_password2: password2.trim(),
          };
          console.log(parvalue);
      const response = await axios.post('http://localhost:8080/pass/resetpassword',parvalue, {
        headers: {
          'Content-Type': 'application/json'
        }
    });

      toast.success(response.data, {
        position: "bottom-right"
      });
    } catch (error) {
      toast.error(
        error.response?.data || "Something went wrong!",
        { position: "bottom-right" }
      );
    }
  };

  return (
    <div className='rp'>
      <ToastContainer />
      <div className="form-card">
        <h2><i className="fas fa-lock"></i> Reset Password</h2>
        <form onSubmit={handleSubmit}>
          <div className="input-group">
          <span className="input-group-text"><i className="fas fa-envelope"></i></span>
          <input type="email" className="form-control" placeholder="Email Address" required value={email} onChange={(e) => setEmail(e.target.value)} />
            
          </div>

          <div className="input-group">
          <span className="input-group-text"><i className="fas fa-user"></i></span>
          <input type="text" className="form-control" placeholder="Full Name" required value={fullName} onChange={(e) => setFullName(e.target.value)} />
          </div>

          <div className="input-group">
            <span className="input-group-text"><i className="fas fa-key"></i></span>
            <input type="password" className="form-control" placeholder="New Password" required value={password1} onChange={(e) => setPassword1(e.target.value)} />
          </div>

          <div className="input-group">
            <span className="input-group-text"><i className="fas fa-check-circle"></i></span>
            <input type="password" className="form-control" placeholder="Confirm Password" required value={password2} onChange={(e) => setPassword2(e.target.value)} />
          </div>

          <button type="submit" className="btn btn-reset mt-3">
            <i className="fas fa-redo-alt"></i> Reset Password
          </button>

          <div className="d-flex justify-content-between mt-4">
            <Link to="/" className="glow-link">Login <i className="fas fa-arrow-right"></i></Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ResetPassword;
