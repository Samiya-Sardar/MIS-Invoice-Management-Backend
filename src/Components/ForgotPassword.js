import React, { useState } from 'react';
import './ForgotPassword.css';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const ForgotPassword = () => {
  const [fullName, setFullName] = useState('');
  const [email, setEmail] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const fpResponse = await axios.post(`http://localhost:8080/fp`, null, {
        params: {
          fullName: fullName,
          email: email,
        },
      });

      const fpData = fpResponse.data;

      if (
        Array.isArray(fpData) &&
        fpData.length === 1 &&
        fpData[0].message &&
        (fpData[0].message.toLowerCase().includes("not found") ||
         fpData[0].message.toLowerCase().includes("incorrect"))
      ) {
        toast.error(fpData[0].message, {
          position: "bottom-right",
        });
        return;
      }

      const sendMailResponse = await axios.get(`http://localhost:8080/sendmail`, {
        params: {
          fullName: fullName,
          email: email,
        },
      });

      const sendMailData = sendMailResponse.data;

      localStorage.setItem('forgotPasswordData', JSON.stringify(sendMailData));

      toast.success("Email sent successfully!", {
        position: "bottom-right",
      });

    } catch (error) {
      const errorMessage =
        error.response?.data?.message || error.message || "Unknown error";
      toast.error("Something went wrong: " + errorMessage, {
        position: "bottom-right",
      });
      console.error("Error:", error);
    }
  };

  return (
    <div className='all'>
      <div className="form-card">
        <h2><i className="fas fa-envelope-open-text"></i> Send Email</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-3 input-group">
            <span className="input-group-text"><i className="fas fa-user"></i></span>
            <input
              type="text"
              className="form-control"
              placeholder="Full Name"
              required
              value={fullName}
              onChange={(e) => setFullName(e.target.value)}
            />
          </div>
          <div className="mb-4 input-group">
            <span className="input-group-text"><i className="fas fa-envelope"></i></span>
            <input
              type="email"
              className="form-control"
              placeholder="Email Address"
              required
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
          <button type="submit" className="btn btn-send">
            <i className="fas fa-paper-plane"></i> Send Email
          </button>

          {/* This is the aligned row below the button */}
          <div className="d-flex justify-content-between mt-3">
            <Link to="/" className="text-decoration-none">
              <i className="fas fa-arrow-left"></i> Go Back
            </Link>
            <Link to="/resetpassword" className="text-decoration-none">
              Reset Password <i className="fas fa-arrow-right"></i>
            </Link>
          </div>
        </form>
      </div>

      <ToastContainer />
    </div>
  );
};

export default ForgotPassword;
