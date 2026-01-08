import React from 'react'
import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';


export const LoginOauth = () => {
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    const token = new URLSearchParams(location.search).get("token");

    if (token) {
      localStorage.setItem("token", token);
      navigate("/", 
        { replace: true });
      // window.location.reload();
    }
  }, [location, navigate]);
  return <div>Redirecting...</div>;
}   
