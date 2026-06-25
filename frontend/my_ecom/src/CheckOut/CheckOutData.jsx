import React from 'react'
import { useState } from 'react'
import { useEffect } from 'react'

export const CheckOutData = () => {
  const [ordersummary,setOrderSummary]=useState(null);
  const[summaryloading,setLoading]=useState(true);
  
      useEffect(() => {
          fetch(`${import.meta.env.VITE_API_URL}/OrderSummary`,{
              method:'GET',
              headers:{
                  'Content-Type':'application/json',
                  'Authorization':`Bearer ${localStorage.getItem('token')}`
              }
          })
              .then(res => res.json())
              .then(data=>{setOrderSummary(data),setLoading(false)})
              .catch(err => 
                alert(err.message)
              )
      }, [])
      
      return {ordersummary,summaryloading};
}

