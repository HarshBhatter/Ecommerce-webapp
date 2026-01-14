import React, { useEffect } from 'react'
import './Cart.css'
import { CartData } from './CartData'
import { NavLink } from 'react-router-dom';
import { FaMinus } from "react-icons/fa";
import { FaPlus } from "react-icons/fa";
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

export const Cart = () => {

    const location = useLocation();
    const navigate = useNavigate();
    useEffect(() => {
        if (localStorage.getItem('token') === null) {
            navigate('/login',
                {
                    state: { from: location },
                    replace: true
                }
            );
        }
    },);

    const data = CartData();
    console.log(data);

    if (data.length == 0)
        return <div className='header'>Your Cart is Empty</div>

    return (
        <>
            <div className='header'>Your Cart</div>
            <div className="Cart">
                <table >
                    <thead>
                        <tr className='header'>
                            <th className='serial'>Serial No.</th>
                            <th>Product Name</th>
                            <th>Color</th>
                            <th>Size</th>
                            <th>Quantity</th>
                            <th>Total</th>
                        </tr>
                    </thead>

                    {data.map((product, index) => (
                        <tbody>
                            <tr>
                                <td>{index + 1}</td>
                                <td><NavLink to={`/product/${product.productId}`}>{product.name}</NavLink></td>
                                <td>{product.color}</td>
                                <td>{product.size}</td>
                                <td className='quantity'><FaMinus cursor="pointer" onClick={
                                    () => {
                                        fetch(`${import.meta.env.VITE_API_URL}/RemoveFromCart`, {
                                            method: 'POST',
                                            headers: {
                                                'Content-Type': 'application/json',
                                                'Authorization': `Bearer ${localStorage.getItem('token')}`
                                            },
                                            body: JSON.stringify({
                                                productid: product.productId,
                                                color: product.color,
                                                size: product.size,
                                                quantity: 1
                                            })
                                        }).then(() => window.location.reload())
                                            .then(alert("Removed From Cart"))
                                    }
                                } />{product.quantity}<FaPlus cursor="pointer" onClick={
                                    () => {
                                        fetch(`${import.meta.env.VITE_API_URL}/AddToCart`, {
                                            method: 'POST',
                                            headers: {
                                                'Content-Type': 'application/json'
                                                , 'Authorization': `Bearer ${localStorage.getItem('token')}`
                                            },
                                            body: JSON.stringify({
                                                productid: product.productId,
                                                color: product.color,
                                                size: product.size,
                                                quantity: 1
                                            })
                                        }).then(() => window.location.reload())
                                            .then(alert("Added To Cart"))
                                    }} /></td>
                                <td>{product.total}</td>
                            </tr>
                        </tbody>
                    ))}


                </table>
            </div>
            <div className='PlaceOrder' ><button onClick={
                () => {
                    fetch(`${import.meta.env.VITE_API_URL}/PlaceOrder`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${localStorage.getItem('token')}`
                        }
                    }).then(() => window.location.reload())
                }
            }>Place Order</button></div>

        </>
    )
}
