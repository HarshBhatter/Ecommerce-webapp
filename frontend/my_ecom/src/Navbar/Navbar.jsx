import React, { useState, useEffect, useRef } from 'react'
import './Navbar.css'
import { FaCartShopping } from "react-icons/fa6";
import { Link, NavLink, useLocation } from 'react-router-dom';
import { FaCircleUser } from "react-icons/fa6";
import { useNavigate } from 'react-router-dom';
// import { useLocation } from 'react-router-dom';
import { RiShoppingBag4Fill } from "react-icons/ri";

export const Navbar = () => {
  const [showLogout, setShowLogout] = useState(false);
  const dropdownRef = useRef(null);
  var token = localStorage.getItem('token');
  const navigate = useNavigate();
  const location = useLocation();
  // localStorage.removeItem("token");


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

  useEffect(() => {
    const interval=setInterval(() => {
      const l = localStorage.getItem('logintime');

      if (l && Date.now() - l > 1800000) {
        localStorage.removeItem("token");
        localStorage.removeItem("logintime");
        alert("Session Expired");
        window.location.reload();
      }
    }, 1000);
    return () => clearInterval(interval);
  }, []);

  return (
    <>
      <div className="navbar">
        <div className="left-navbar">
          <div><NavLink to="/"  onClick={() => window.scrollTo({ top: 0, behavior: "smooth" })}><RiShoppingBag4Fill /></NavLink></div>
          <div><NavLink to="/#about-us" >About Us</NavLink><hr></hr></div>
          <div><NavLink to="/#contact-us">Contact Us</NavLink><hr /></div>
        </div>

        <div className="right-navbar">
          <div><NavLink to="/cart"><FaCartShopping /></NavLink><hr /></div>
          <div><NavLink to="/orders">My Orders</NavLink><hr /></div>
          {token ? (<div style={{ position: 'relative' }} ref={dropdownRef}>
            <FaCircleUser style={{ cursor: 'pointer' }} onClick={() => setShowLogout(!showLogout)} />
            <hr />
            {showLogout && (
              <div
                style={{
                  position: "absolute",
                  top: "100%",
                  right: 0,
                  backgroundColor: "white",
                  color: "black",
                  border: "1px solid #ccc",
                  zIndex: 1000,
                  minWidth: "130px"
                }}
              >
                <div
                  style={{
                    padding: "8px 12px",
                    cursor: "pointer",
                    borderBottom: "1px solid #ddd"
                  }}
                  onClick={() => {
                    setShowLogout(false);
                    window.location.href='/Coupons';
                  }}
                >
                  My Coupons
                </div>

                <div
                  style={{
                    padding: "8px 12px",
                    cursor: "pointer"
                  }}
                  onClick={async () => {
                    try {
                      await fetch(`${import.meta.env.VITE_API_URL}/Logout`, {
                        method: "POST",
                        headers: {
                          "Content-Type": "application/json",
                          Authorization: `Bearer ${localStorage.getItem("token")}`
                        }
                      });
                    } catch (error) {
                      console.log(error);
                    }

                    localStorage.removeItem("token");
                    localStorage.removeItem("logintime");
                    window.location.reload();
                  }}
                >
                  Logout
                </div>
              </div>
            )}
          </div>
          ) : (
            <div onClick={() => {
              navigate('/login',
                {
                  state: { from: location },
                  replace: true
                }
              );
            }
            }>Login<hr /></div>
          )}
        </div>
      </div>
    </>
  )
}
