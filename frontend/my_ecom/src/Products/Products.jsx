import React from 'react'
import './Products.css'
import './Product-cards.css'
import { CiFaceFrown } from "react-icons/ci";
import { ProductsData } from './ProductsData'
import { NavLink, useLocation } from 'react-router-dom'
import { FaRupeeSign } from "react-icons/fa";
import { useState, useEffect } from 'react';

export const Products = ({ gender }) => {
    const [page, setPage] = useState(0);
    const { products = [], loading, totalpages = 0 } = ProductsData(page, gender);
    const [timer, setTimer] = useState("00:00:00");

    useEffect(() => {
        let interval;
        if (loading) {
            const startTime = Date.now();
            interval = setInterval(() => {
                const totalSeconds = Math.floor((Date.now() - startTime) / 1000);
                const h = String(Math.floor(totalSeconds / 3600)).padStart(2, '0');
                const m = String(Math.floor((totalSeconds % 3600) / 60)).padStart(2, '0');
                const s = String(totalSeconds % 60).padStart(2, '0');
                setTimer(`${h}:${m}:${s}`);
            }, 1000);
        }
        return () => clearInterval(interval);
    }, [loading]);

    if (loading) {
        return <div className="header">Loading products...<br></br>
            {timer} <br></br>
            <div style={{ fontSize: "50%" }}> (expected waiting time : 3mins)</div>
        </div>;
    }

    if (!loading && products.length === 0) {
        return (
            <div className='header'>
                No Products Found <br />
                <CiFaceFrown />
            </div>
        );
    }

    return (
        <>
            <div>
                <div className='products'>
                    {products.map((product) => (
                        <NavLink to={`/product/${product.id}`} className='product-card' key={product.id}>
                            <div className='image'><img src={import.meta.env.VITE_API_URL + product.imageurl} alt={product.name} /></div>
                            <div className='product-name'>{product.name}</div>
                            <div className='product-price'><FaRupeeSign />{product.price}</div>
                        </NavLink>
                    ))}
                </div>
                {totalpages > 1 && (
                    <div className='Pages'>
                        <div className='previous'>&lt;</div>
                        <div className='page-numbers'>
                            {Array.from({ length: totalpages }, (_, i) => (
                                <div className='number' style={{ backgroundColor: page === i ? 'rgb(144, 168, 187)' : 'rgb(179, 179, 179)' }} key={i + 1} onClick={() => setPage(i)}>{i + 1}</div>
                            ))}
                        </div>
                        <div className='next'>&gt;</div>
                    </div>
                )}

            </div>
        </>
    )
}
