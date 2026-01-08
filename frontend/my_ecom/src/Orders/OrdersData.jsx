import React from 'react'
import { useState } from 'react'
import { useEffect } from 'react'

export const OrdersData = () => {
    const [orders,setOrders]=useState([])

    useEffect(()=>{
        fetch('http://localhost:8080/MyOrders', {
            method:'GET',
            headers:{
                'Content-Type':'application/json',
                'Authorization':`Bearer ${localStorage.getItem('token')}`
            }
        })
        .then(res => res.json())
        .then(data => setOrders(data))
        .catch(err => console.log(err))
    },[])
  return orders
}
