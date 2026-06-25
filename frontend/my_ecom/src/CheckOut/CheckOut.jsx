import React, { useEffect, useState } from 'react';
import "../Cart/CartData";
import './CheckOut.css';
import { useLocation, useNavigate } from 'react-router-dom';
import { CartBody } from "../Cart/CartBody";
import { CartData } from '../Cart/CartData';
import { CheckOutData } from './CheckOutData';
import { Coupons } from '../Coupon/Coupons';
import { FaRupeeSign } from "react-icons/fa";


export const CheckOut = () => {
    const {ordersummary ,summaryloading}= CheckOutData();
    const location = useLocation();
    const {data , loading} = CartData();
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
    
    const [timeLeft, setTimeLeft] = useState("");


useEffect(() => {
    if (!ordersummary?.expiry) return;

    const expiry = new Date(ordersummary.expiry.replace(" ", "T")+"Z");

    const interval = setInterval(() => {
        const diff = expiry - new Date();

        if (diff <= 0) {
            setTimeLeft("Reservation expired!");
            clearInterval(interval);
            setTimeout(() => navigate('/Cart'), 2000);
            return;
        }

        const minutes = Math.floor(diff / 1000 / 60);
        const seconds = Math.floor((diff / 1000) % 60);
        setTimeLeft(`${minutes}:${seconds.toString().padStart(2, '0')}`);
    }, 1000);

    return () => clearInterval(interval);
}, [ordersummary]);

    // console.log(ordersummary);
    // console.log(ordersummary.expiry)
    if(summaryloading)
        return <div className='header'>Loading...</div>

    return (
        <div className="checkOut">
                <div className="header">~Order Summary~</div>
                    <div className='shippinDetailsBlock'>
                        <div className="shippingDetails">
                            <h3>Deliver To :-</h3>
                            <br />
                            <div>{ordersummary?.address?.street}</div>
                            <div>{ordersummary?.address?.city}</div>
                            <div>{ordersummary?.address?.state}</div>
                            <div>{ordersummary?.address?.pincode}</div>
                            <br />
                        </div>
                        <div className='expiry'>{timeLeft}</div>
                    </div>
            <CartBody data={data}  showStockStatus={false}/>
            <div className='CouponSection'>
                {
                (ordersummary?.couponCode?.length)>0 ? (
                    <div>
                        <div className="applied-coupon">
                            <span>
                                Coupon Applied : {ordersummary.couponCode}
                            </span>

                            <button onClick={handleRemoveCoupon}>
                                Remove
                            </button>
                        </div>
                        <div className='discount'>You Saved <FaRupeeSign/> {ordersummary.discount} on this Order!</div>
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
                    </div>
                )
            }

            </div>
             {
                showCoupons && 
                    <Coupons
                        selectable={true}
                        onSelect={(coupon) => {
                            setInputCode(coupon);
                            setShowCoupons(false);
                        }
                    }/>
            }
            
            <hr />
            <hr />
            <div className='finalamount'>
                <div className='left'>Total:</div>
                <div className='right'><FaRupeeSign/> {ordersummary?.discountedTotal || total || "Loading..."}</div>
            </div>
            <hr />
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
                                    },
                                    body:ordersummary?.discountedTotal
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
                                    window.location.href='/';
                                }
                            };

                            const rzp = new window.Razorpay(options);

                            rzp.on("payment.failed", async function (response) {
                                try {
                                    await fetch(`${import.meta.env.VITE_API_URL}/paymentFailed`, {
                                        method: "POST",
                                        headers: {
                                            "Content-Type": "application/json",
                                            Authorization: `Bearer ${localStorage.getItem("token")}`
                                        }
                                    });

                                    alert("Payment Failed!");
                                } catch (e) {
                                    console.error(e);
                                }
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