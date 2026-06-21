import React from 'react'
import { useState } from 'react'
import { useEffect } from 'react'

export const CouponsData = () => {
  const [coupons,setCoupons]=useState([])
      useEffect(() => {
          fetch(`${import.meta.env.VITE_API_URL}/Coupons`,{
              method:'GET',
              headers:{
                  'Content-Type':'application/json',
                  'Authorization':`Bearer ${localStorage.getItem('token')}`
              }
          })
              .then(res => res.json())
              .then(data => {
                setCoupons(data)
                console.log(data)
                })
              .catch(err => console.log(err))
      }, [])
      
      return coupons;
}
