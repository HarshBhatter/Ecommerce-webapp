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
                        replace: true
                    }
                );
            }
        },);

    return (
        <div className='overlay'>
            <div className='LoginPage placeorder'>
                <div className='header'>Shipping and checkout details<hr></hr></div>
                <NavLink to={'/cart'} className='cross'><RxCross2 /></NavLink>
                <div className='userinfo'>
                    <form onSubmit={(e) => {
                        e.preventDefault();
                        const shipping = {
                            email: e.target.Email.value,
                            address: e.target.Address.value,
                            city: e.target.City.value,
                            state: e.target.State.value,
                            pincode: e.target.Pincode.value
                        };

                        localStorage.setItem(
                            "shippingDetails",
                            JSON.stringify(shipping)
                        );

                        navigate("/CheckOut", {
                            state: shipping
                        });
                    }}>
                        <label htmlFor="Email">Email Id</label>
                        <input type="email" id="Email" name="Email" required />
                        <label htmlFor="Address">Address</label>
                        <input type="text" id="Address" name="Address" required />
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
