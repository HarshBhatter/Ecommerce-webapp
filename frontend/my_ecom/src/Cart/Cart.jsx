import React, { useEffect, useState } from 'react'
import './Cart.css'
import { CartData } from './CartData'
import { NavLink } from 'react-router-dom';
import { FaMinus } from "react-icons/fa";
import { FaPlus } from "react-icons/fa";
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { FaRupeeSign } from "react-icons/fa";
import { CartBody } from './CartBody';

export const Cart = () => {

    const location = useLocation();
    const navigate = useNavigate();
    useEffect(() => {
        if (localStorage.getItem('token') === null) {
            navigate('/login',
                {
                    state: { from: location },
                }
            );
        }
    },);

    const { data, loading } = CartData();
    console.log(data);

    if(loading)
        return <div className='header'>Loading...</div>

    if (data.length === 0)
        return <div className='header'>Your Cart is Empty!</div>

    const total = data.reduce((acc, item) => acc + (item.total || 0), 0);

    return (
        <>
            <div className='header'>Your Cart</div>
            <div><CartBody data={data} showStockStatus={true}/></div>
            <div className="PlaceOrder">
                <button onClick={() => navigate('/CheckOut')}>place order</button>
            </div>

        </>
    )
}
