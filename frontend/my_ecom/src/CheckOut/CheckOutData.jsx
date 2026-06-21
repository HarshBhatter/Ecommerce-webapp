import React from 'react'
import { useState } from 'react'
import { useEffect } from 'react'
import { data } from 'react-router-dom'

export const CheckOutData = () => {
  const [OrderSummary,setOrderSummary]=useState({})
  
      useEffect(() => {
          fetch(`${import.meta.env.VITE_API_URL}/OrderSummary`,{
              method:'GET',
              headers:{
                  'Content-Type':'application/json',
                  'Authorization':`Bearer ${localStorage.getItem('token')}`
              }
          })
              .then(res => res.json())
              .then(data=>{
                    console.log(data),
                    setOrderSummary(data)})
              .catch(err => console.log(err))
      }, [])
      
      return OrderSummary;
}

