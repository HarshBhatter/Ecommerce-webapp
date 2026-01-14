import { useState } from "react";
import { useEffect } from "react";

import React from 'react'

export const CartData = () => {

    const [cart,setProducts]=useState([])

    useEffect(() => {
        fetch(`${import.meta.env.VITE_API_URL}/Cart`,{
            method:'GET',
            headers:{
                'Content-Type':'application/json',
                'Authorization':`Bearer ${localStorage.getItem('token')}`
            }
        })
            .then(res => res.json())
            .then(data => setProducts(data))
            .catch(err => console.log(err))
    }, [])
    
    return cart;
}

