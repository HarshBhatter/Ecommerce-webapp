import { useState } from "react";
import { useEffect } from "react";
import React from 'react'


export const IndividualOrderData = (id) => {
    const [data, setData] = useState([])
    useEffect(() =>{
        if(id){
            fetch(`${import.meta.env.VITE_API_URL}/MyOrders/?id=${id}`,{
            method:'GET',
            headers:{
                'Content-Type':'application/json',
                'Authorization':`Bearer ${localStorage.getItem('token')}`
            }
            })
            .then(res => res.json())
            .then(json => setData(json))
        }
    },[id])
    console.log("data : "+data);
    return data;
}