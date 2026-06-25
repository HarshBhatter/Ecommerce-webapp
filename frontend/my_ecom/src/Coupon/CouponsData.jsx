import React from 'react'
import { useState } from 'react'
import { useEffect } from 'react'

export const CouponsData = () => {
  const [Coupons,setCoupons]=useState([])
  const [loading,setLoading]=useState(true);

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
                setCoupons(data),
                setLoading(false)
                })
              .catch(err => console.log(err))
      }, [])
      
      return {Coupons,loading};
}
