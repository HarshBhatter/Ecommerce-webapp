import React from 'react'
import './Products.css'
import './Product-cards.css'
import { CiFaceFrown } from "react-icons/ci";
import { ProductsData } from './ProductsData'
import { MensProducts } from './MensData'
import { WomensProducts } from './WomensData'
import { NavLink, useLocation } from 'react-router-dom'
import { FaRupeeSign } from "react-icons/fa";
import { useState, useEffect } from 'react';

export const Products = () => {
    const location = useLocation(); 
    let products = [12]; 
    if (location.pathname === "/products/Mens") 
        { products = MensProducts(); } 
    else if (location.pathname === "/products/Womens")
        { products = WomensProducts(); }
    else 
        { products = ProductsData(); } 
    // console.log(products.length); 
    if (products.length === 0)
        return <div className='header'>No Products Found <br /><CiFaceFrown /></div>

    return (
        <>
            <div>
                <div className='products'>
                    {products.map((product) => (
                        console.log(product),
                        <NavLink to={`/product/${product.id}`} className='product-card' key={product.id}>
                            <div className='image'><img src={`data:image/jpeg;base64,${product?.color?.[0]?.picture}`} alt={product.name} /></div>
                            <div className='product-name'>{product.name}</div>
                            <div className='product-price'><FaRupeeSign />{product.price}</div>
                        </NavLink>
                    ))}
                </div>
                {/* <div className='Pages'>
                    <div className='previous'>&lt;</div>
                    <div className='page-numbers'>
                        <div className='number'>1</div>
                        <div className='number'>2</div>
                        <div className='number'>3</div>
                    </div>
                    <div className='next'>&gt;</div>
                </div> */}
            </div>
        </>
    )
}
