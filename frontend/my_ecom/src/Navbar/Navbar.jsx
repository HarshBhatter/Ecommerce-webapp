import React, { useState, useEffect, useRef } from 'react'
import './Navbar.css'
import { FaCartShopping } from "react-icons/fa6";
import { Link, NavLink, useLocation } from 'react-router-dom';
import { FaCircleUser } from "react-icons/fa6";
import { useNavigate } from 'react-router-dom';
// import { useLocation } from 'react-router-dom';

export const Navbar = () => {
  const [showLogout, setShowLogout] = useState(false);
  const dropdownRef = useRef(null);
  var token=localStorage.getItem('token');
  const navigate=useNavigate();
  const location = useLocation();



  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setShowLogout(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  useEffect(() => {
    token = localStorage.getItem('token');
  }, [token]);

  return (
    <>
      <div className="navbar">
        <div className="left-navbar">
          <div><NavLink to="/">Logo</NavLink></div>
          <div><NavLink to="/#about-us" >About Us</NavLink><hr></hr></div>
          <div><NavLink to="/#contact-us">Contact Us</NavLink><hr /></div>
        </div>

        <div className="right-navbar">
          <div><NavLink to="/cart"><FaCartShopping /></NavLink><hr /></div>
          <div><NavLink to="/orders">My Orders</NavLink><hr /></div>
          {token ? (
            <div style={{ position: 'relative' }} ref={dropdownRef}>
              <FaCircleUser style={{ cursor: 'pointer' }} onClick={() => setShowLogout(!showLogout)} />
              <hr />
              {showLogout && (
                  <div style={{position: 'absolute', top: '100%', right: 0, backgroundColor: 'white', color: 'black',border: '1px solid #ccc', padding: '5px 10px', cursor: 'pointer', zIndex: 1000
                    }} onClick={async () => {
                      await fetch('http://localhost:8080/Logout', {
                        method: 'POST',
                        headers: {
                          'Content-Type': 'application/json',
                          'Authorization': `Bearer ${localStorage.getItem('token')}`
                        }})
                        .then(Response=>{
                          if(Response.ok)
                          {
                            localStorage.removeItem('token');
                            window.location.reload();
                          }
                        })
                        .catch(error => {
                          console.log(error);
                        });
                    }}>
                    Logout
                  </div>
              )}
            </div>
          ) : (
            <div onClick={()=>
            {
              navigate('/login',
                {state: {from: location},
                 replace: true }
              );
            }
            }>Login<hr /></div>
          )}
        </div>
      </div>
    </>
  )
}
