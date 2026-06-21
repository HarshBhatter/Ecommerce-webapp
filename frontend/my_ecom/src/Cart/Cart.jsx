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

    const data = CartData();
    console.log(data);

    if (data.length == 0)
        return <div className='header'>Your Cart is Empty!</div>

    const total = data.reduce((acc, item) => acc + (item.total || 0), 0);

    return (
        <>
            <div className='header'>Your Cart</div>
            <div><CartBody data={data}/></div>
            <div className="PlaceOrder">
                <button onClick={() => navigate('/CheckOut')}>place order</button>
                {/* <button
                    onClick={async () => {

                        try {
                            // 1️⃣ Create Razorpay Order from backend
                            const res = await fetch(
                                `${import.meta.env.VITE_API_URL}/razorpay/payment`,
                                {
                                    method: "POST",
                                    headers: {
                                        "Content-Type": "application/json",
                                        "Authorization": `Bearer ${localStorage.getItem("token")}`
                                    }
                                }
                            );

                            const data = await res.json();

                            const options = {
                                key: data.key,
                                amount: data.amount,
                                currency: "INR",
                                order_id: data.orderId,
                                name: "Your Store",
                                description: "Order Payment",

                                // ✅ SUCCESS HANDLER
                                handler: async function (response) {
                                    // console.log("Full response:", response);

                                    const res2 = await fetch(
                                        `${import.meta.env.VITE_API_URL}/razorpay/confirm`,
                                        {
                                            method: "POST",
                                            headers: {
                                                "Content-Type": "application/json",
                                                "Authorization": `Bearer ${localStorage.getItem("token")}`
                                            },
                                            body: JSON.stringify({
                                                razorpayOrderId: response.razorpay_order_id,
                                                razorpayPaymentId: response.razorpay_payment_id,
                                                razorpaySignature: response.razorpay_signature
                                            })
                                        }
                                    );
                                    const result = await res2.text();
                                    alert(result);
                                    window.location.reload();
                                }
                            };

                            const rzp = new window.Razorpay(options);

                            // ❌ FAILURE HANDLER
                            rzp.on("payment.failed", function () {
                                alert("Payment Failed!");
                            });

                            // 3️⃣ Open popup
                            rzp.open();

                        } catch (error) {
                            alert("Something went wrong!");
                            console.error(error);
                        }

                    }}
                >
                    Place Order
                </button> */}
            </div>

        </>
    )
}
