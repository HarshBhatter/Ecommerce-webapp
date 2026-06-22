import React from 'react'
import './HomePage.css'
import './AboutUs.css'
import './ContactUs.css'
import './AllProducts.css'
import { FaInstagram } from "react-icons/fa";
import { FaLinkedin } from "react-icons/fa";
import { ImGithub } from "react-icons/im";
import { Products } from '../Products/Products.jsx';
import { Link,NavLink } from 'react-router-dom'

export function Homepage() {
    console.log(localStorage.getItem("token"));
    console.log(localStorage.getItem("logintime"));
    console.log(localStorage.getItem("role"));
    return (
        <>
            <div id="Categories" className='Categories'>
                <NavLink to="/products/Mens" className="men">Men</NavLink>
                <NavLink to="/products/Womens" className="women">Women</NavLink>
            </div>

            <div className='header'>Our Showstoppers</div>

            <div className='Allproducts'><Products /></div>
            
            <div id="about-us" className='AboutUs'>
                <div className='header'>About Us</div>
                <div className='info'>Hello Everyone! <br />My name is Harsh Bhatter and I am the developer of this E-commerce website :)</div>
            </div>

            <div id="contact-us" className='ContactUs'>
                <div className='header'>Contact Us</div>
                <div className='info'>
                    <div>Mail to : <a href="mailto:harshbhatter03@gmail.com">harshbhatter03@gmail.com</a> </div>
                    <div>Call : <a href="tel:7003255926"> 7003255926</a></div>
                    <div className='links'>Find us on : 
                        <a href="https://www.linkedin.com/in/harshbhatter03/"><FaLinkedin /></a>
                        <a href="https://github.com/HarshBhatter"><ImGithub /></a>
                    </div>
                </div>
            </div>
        </>
    )
}
