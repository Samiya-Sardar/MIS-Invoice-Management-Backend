import React, { useRef, useState } from 'react';
import './Login.css'; // ✅ correct path
import { Link } from 'react-router-dom';

const Login = () => {
  const wrapperRef = useRef(null);

  const [loginEmail, setLoginEmail] = useState('');
  const [loginPassword, setLoginPassword] = useState('');
  const [registerName, setRegisterName] = useState('');
  const [registerEmail, setRegisterEmail] = useState('');
  const [registerPassword, setRegisterPassword] = useState('');
  const [registerRole, setRegisterRole] = useState('USER'); // Default role

  const handleRegisterClick = () => {
    wrapperRef.current.classList.add('active');
  };

  const handleLoginClick = () => {
    wrapperRef.current.classList.remove('active');
  };

  const handleLoginSubmit = async (e) => {
    e.preventDefault();

    try {
      // Check if email and password are provided before making the request
      if (!loginEmail || !loginPassword) {
        alert("Please provide both email and password.");
        return;
      }

      const url = new URL('http://localhost:8080/validate');
      url.searchParams.append('email', loginEmail);
      url.searchParams.append('password', loginPassword);

      const res = await fetch(url);

      const data = await res.json();

      if (res.ok) {
        // Store in localStorage
        localStorage.setItem('fullname', data.ufullname);
        localStorage.setItem('email', data.uemail);
        localStorage.setItem('password', data.upass);
        localStorage.setItem('role', data.role);
        localStorage.setItem('userid', data.userid);
        localStorage.setItem('curl', data.curl);

        alert('Login successful!');
        // Navigate to dashboard or reload
        window.location.href = data.curl.startsWith("/") ? data.curl : `/${data.curl}`;
      } else {
        alert(data.message || 'Login failed');
      }
    } catch (err) {
      console.error('Login error:', err);
      alert('Something went wrong!');
    }
  };

  const handleRegisterSubmit = async (e) => {
    e.preventDefault();

    try {
      const res = await fetch('http://localhost:8080/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          fullname: registerName,
          email: registerEmail,
          upassword: registerPassword,
          urole: registerRole
        }),
      });

      const data = await res.json();

      if (res.ok) {
        // Store in localStorage
        localStorage.setItem('fullname', data.ufullname);
        localStorage.setItem('email', data.uemail);
        localStorage.setItem('password', data.upass);
        localStorage.setItem('role', data.role);
        localStorage.setItem('userid', data.userid);
        localStorage.setItem('curl', data.curl);

        alert('Registration successful!');
        handleLoginClick(); // Switch to login view
      } else {
        alert(data.message || 'Registration failed');
      }
    } catch (err) {
      console.error('Register error:', err);
      alert('Something went wrong!');
    }
  };

  return (
    <div className='everything'>
      <div className='style-body'>
        <div className="wrapper" ref={wrapperRef}>
          <span className="bg-animate"></span>
          <span className="bg-animate2"></span>

          {/* Login Form */}
          <div className="form-box login">
            <h2 className="animation" style={{ "--i": 0 }}>Login</h2>
            <form onSubmit={handleLoginSubmit}>
              <div className="input-box animation" style={{ "--i": 1 }}>
                <input type="text" required value={loginEmail} onChange={(e) => setLoginEmail(e.target.value)} />
                <label>Email</label>
                <i className='bx bx-user'></i>
              </div>
              <div className="input-box animation" style={{ "--i": 2 }}>
                <input type="password" required value={loginPassword} onChange={(e) => setLoginPassword(e.target.value)} />
                <label>Password</label>
                <i className='bx bxs-lock'></i>
              </div>
              <button type="submit" className="btn animation" style={{ "--i": 3 }}>Login</button>
              <div className="logreg-link animation" style={{ "--i": 4 }}>
                <p>Don't have an account? <a href="#" className="register-link" onClick={handleRegisterClick}>Sign Up</a>---
                 <Link to="/FP">  Forgot password</Link></p>
              </div>
            </form>
          </div>

          <div className="info-text login">
            <h2 className="animation" style={{ "--i": 0 }}>Welcome Back!</h2>
            <p className="animation" style={{ "--i": 1 }}>
              Enter your credentials to access the system.
            </p>
          </div>

          {/* Registration Form */}
          <div className="form-box register">
            <h2 className="animation" style={{ "--i": 17 }}>Sign Up</h2>
            <form onSubmit={handleRegisterSubmit}>
              <div className="input-box animation" style={{ "--i": 18 }}>
                <input type="text" required value={registerName} onChange={(e) => setRegisterName(e.target.value)} />
                <label>Full Name</label>
                <i className='bx bx-user'></i>
              </div>
              <div className="input-box animation" style={{ "--i": 19 }}>
                <input type="email" required value={registerEmail} onChange={(e) => setRegisterEmail(e.target.value)} />
                <label>Email</label>
                <i className='bx bx-envelope'></i>
              </div>
              <div className="input-box animation" style={{ "--i": 20 }}>
                <input type="password" required value={registerPassword} onChange={(e) => setRegisterPassword(e.target.value)} />
                <label>Password</label>
                <i className='bx bxs-lock'></i>
              </div>
              <button type="submit" className="btn animation" style={{ "--i": 21 }}>Sign-Up</button>
              <div className="logreg-link animation" style={{ "--i": 22 }}>
                <p>Already Have An Account? <a href="#" className="login-link" onClick={handleLoginClick}>Login</a></p>
              </div>
            </form>
          </div>

          <div className="info-text register">
            <h2 className="animation" style={{ "--i": 17, "--j": 0 }}>Welcome</h2>
            <p className="animation" style={{ "--i": 18, "--j": 1 }}>
              Create your account to access the features of the system.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
