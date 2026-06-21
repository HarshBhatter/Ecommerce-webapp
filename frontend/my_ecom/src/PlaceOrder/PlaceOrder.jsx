import React from 'react'
import './PlaceOrder.css'
import '../LoginPage/LoginPage.css';
import { RxCross2 } from "react-icons/rx";
import { NavLink } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';

export const PlaceOrder = () => {
    const navigate = useNavigate();
    useEffect(() => {
        if (localStorage.getItem('token') === null) {
            navigate('/login',
                {
                    state: { from: '/Cart' },
                }
            );
        }
    }, []);

    return (
        <div className='overlay'>
            <div className='LoginPage placeorder'>
                <div className='header'>Shipping and checkout details<hr></hr></div>
                <NavLink to={'/cart'} className='cross'><RxCross2 /></NavLink>
                <div className='userinfo'>
                    <form onSubmit={async (e) => {
                        e.preventDefault();
                        try {
                            const response = await fetch(`${import.meta.env.VITE_API_URL}/saveAddress`, {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json',
                                    'Authorization':`Bearer ${localStorage.getItem('token')}`
                                },
                                body: JSON.stringify({
                                    street: e.target.street.value,
                                    city: e.target.City.value,
                                    state: e.target.State.value,
                                    pincode: e.target.Pincode.value
                                })
                            });

                            if (!response.ok) {
                                const msg = await response.text();
                                throw new Error(msg || 'Failed to save address');
                            }

                            navigate('/checkout', { state: { saved: true } });
                        } catch (error) {
                            alert("An Error occured.please try again" + error);
                        }
                    }}>
                        <label htmlFor="street">Street</label>
                        <input type="text" id="street" name="street" required />
                        <label htmlFor="City">City</label>
                        <input type="text" id="City" name="City" required />
                        <label htmlFor="State">State</label>
                        <input type="text" id="State" name="State" required />
                        <label htmlFor="Pincode">Pincode</label>
                        <input type="number" id="Pincode" name="Pincode" required />
                        <button type="submit">Proceed to Payment</button>
                    </form>
                </div>
            </div>

        </div>
    )
}
