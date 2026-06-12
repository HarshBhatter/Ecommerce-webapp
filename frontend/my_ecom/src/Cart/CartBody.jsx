import React from 'react'
import { CartData } from './CartData';
import './Cart.css'
import { FaRupeeSign } from "react-icons/fa";
import { FaMinus } from "react-icons/fa";
import { useNavigate } from 'react-router-dom';
import { NavLink } from 'react-router-dom';
import { FaPlus } from "react-icons/fa";

export const CartBody = ({data}) => {
    // const data = CartData();
    // const total = data.reduce((acc, item) => acc + (item.total || 0), 0);

  return (
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
                    <tbody>
                        {data?.map((product, index) => (
                            <tr>
                                <td>{index + 1}</td>
                                <td><NavLink className="nav-link" to={`/product/${product.productId}`}>{product.name}</NavLink></td>
                                <td>{product.color}</td>
                                <td>{product.size}</td>
                                <td className='quantity'><div> <FaMinus cursor="pointer" onClick={
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
                                    }} /></div></td>
                                <td><FaRupeeSign />{product.total}</td>
                            </tr>
                        ))}
                    </tbody>
                    {/* <tfoot>
                        <tr>
                            <td></td>
                            <td> </td>
                            <td> </td>
                            <td> </td>
                            <td> </td>
                            <td className="total"><FaRupeeSign />{total}</td>
                        </tr>
                    </tfoot> */}
                </table>
            </div>
  )
}
