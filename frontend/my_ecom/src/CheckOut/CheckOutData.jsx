import React from 'react'
import { useState } from 'react'
import { useEffect } from 'react'

export const CheckOutData = () => {
  const [OrderSummary,setOrderSummary]=useState(null)
  
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
              .catch(err => 
                alert(err.message)
              )
      }, [])
      
      return OrderSummary;
}

