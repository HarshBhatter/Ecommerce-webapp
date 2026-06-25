import { useState } from "react";
import { useEffect } from "react";

import React from 'react'

export const CartData = () => {

    const [data,setProducts]=useState([])
    const [loading,setloading]=useState(true);

    useEffect(() => {
        fetch(`${import.meta.env.VITE_API_URL}/Cart`,{
            method:'GET',
            headers:{
                'Content-Type':'application/json',
                'Authorization':`Bearer ${localStorage.getItem('token')}`
            }
        })
            .then(res => res.json())
            .then(data => {setProducts(data),setloading(false)})
            .catch(err => console.log(err))
    }, [])
    
    return {data , loading};
}

