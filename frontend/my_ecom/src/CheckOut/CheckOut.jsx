import React from 'react'
import "../Cart/CartData"
import './CheckOut.css'
import { useLocation } from 'react-router-dom';
import { CartBody } from "../Cart/CartBody";
import { CartData } from '../Cart/CartData';
import { useEffect } from 'react';

export const CheckOut = () => {

    const location = useLocation();
    const data = CartData();
    const total = data.reduce((acc, item) => acc + (item.total || 0), 0);
    const details=location.state || JSON.parse(localStorage.getItem("shippingDetails"));
    
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
        <div className="CheckOut">
            <div className="header">~Summary~</div>
            <div className="shippingDetails">
                <h3>Address :-</h3>
                <br />
                <div>{details.address}</div>
                <div>{details.city}</div>
                <div>{details.state}</div>
                <div>{details.pincode}</div>
                <br></br>
                <h3>Email Id:-</h3>
                <br></br>
                <div>{details.email}</div>
            </div>
            <div><CartBody data={data} /></div>
            <div className='pay'>
            <button 
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
                >Pay</button></div>
        </div>

    )
}
