import React, { useEffect, useState } from 'react';
import "../Cart/CartData";
import './CheckOut.css';
import { useLocation, useNavigate } from 'react-router-dom';
import { CartBody } from "../Cart/CartBody";
import { CartData } from '../Cart/CartData';
import { CheckOutData } from './CheckOutData';
import { Coupons } from '../Coupon/Coupons';

export const CheckOut = () => {
    const ordersummary = CheckOutData();
    const location = useLocation();
    const data = CartData();
    const total = data.reduce((acc, item) => acc + (item.total || 0), 0);
    const details = location.state || JSON.parse(localStorage.getItem("shippingDetails"));
    const navigate = useNavigate();

    const [showCoupons, setShowCoupons] = useState(false);
    const [inputCode, setInputCode] = useState("");

    useEffect(() => {
        if (localStorage.getItem("token") === null) {
            navigate("/login", {
                state: { from: "/Cart" },
            });
        }
    }, []);

    useEffect(() => {
    if (ordersummary?.address === null) {
        navigate('/PlaceOrder');
    }
}, [ordersummary, navigate]);

    const handleRemoveCoupon = async () => {
        try {
            const res = await fetch(
                `${import.meta.env.VITE_API_URL}/RemoveCoupon`,
                {
                    method: "POST",
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem("token")}`
                    }
                }
            );

            const result = await res.text();
            alert(result);
            window.location.reload();
        } catch (err) {
            console.log(err);
            alert("Failed to remove coupon");
        }
    };

    const handleApplyCoupon = async (inputCode) => {
        console.log(inputCode);
        if (inputCode.trim().length==0) {
            alert("Please select a coupon");
            return;
        }

        try {
            const res = await fetch(
                `${import.meta.env.VITE_API_URL}/ApplyCoupon`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${localStorage.getItem("token")}`
                    },
                    body: inputCode
                }
            );

            const result = await res.text();
            alert(result);
            window.location.reload();
        } catch (err) {
            console.log(err);
            alert("Failed to apply coupon");
        }
    };

    console.log(ordersummary);

    return (
        <div className="CheckOut">

            <div className="header">~Summary~</div>
            <div className="shippingDetails">
                <h3>Deliver To :-</h3>
                <br />
                <div>{ordersummary?.address?.street}</div>
                <div>{ordersummary?.address?.city}</div>
                <div>{ordersummary?.address?.state}</div>
                <div>{ordersummary?.address?.pincode}</div>
                <br />
            </div>

            <CartBody data={data} />
            <div className='CouponSection'>
                {
                ordersummary?.coupon ? (
                    <div className="applied-coupon">
                        <span>
                            Coupon Applied : {ordersummary.coupon.code}
                        </span>

                        <button onClick={handleRemoveCoupon}>
                            Remove
                        </button>
                    </div>
                ) : (
                    <div>
                    <div className="coupon-input-container">

                        <div className="input-wrapper">
                            <div>
                                <input
                                    type="text"
                                    placeholder="Enter coupon code"
                                    value={inputCode}
                                    onChange={(e) => setInputCode(e.target.value)}
                                />
                            </div>
                            <button
                                className="dropdown-btn"
                                onClick={() => setShowCoupons(!showCoupons)}
                            >▼</button>

                        </div>

                        <button
                            className="apply-btn"
                            onClick={() => handleApplyCoupon(inputCode)}
                        >
                            Apply
                        </button>
                    </div>
                    {
                        showCoupons && 
                        <Coupons
                            selectable={true}
                            onSelect={(coupon) => {
                                setInputCode(coupon.code);
                                setShowCoupons(false);
                        }}/>
                    }
                    </div>
                )
            }

            </div>
            
            <hr />

            <div>
                {ordersummary?.discountedTotal || total || "Loading..."}
            </div>

            <hr />

            <div className="pay">
                <button
                    onClick={async () => {

                        try {

                            const res = await fetch(
                                `${import.meta.env.VITE_API_URL}/razorpay/payment`,
                                {
                                    method: "POST",
                                    headers: {
                                        "Content-Type": "application/json",
                                        Authorization: `Bearer ${localStorage.getItem("token")}`
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

                                handler: async function (response) {

                                    const res2 = await fetch(
                                        `${import.meta.env.VITE_API_URL}/razorpay/confirm`,
                                        {
                                            method: "POST",
                                            headers: {
                                                "Content-Type": "application/json",
                                                Authorization: `Bearer ${localStorage.getItem("token")}`
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

                            rzp.on("payment.failed", function () {
                                alert("Payment Failed!");
                            });

                            rzp.open();

                        } catch (error) {
                            alert("Something went wrong!");
                            console.error(error);
                        }

                    }}
                >
                    Pay
                </button>
            </div>

        </div>
    );
};