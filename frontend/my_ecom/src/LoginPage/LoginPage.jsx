import React from 'react'
import './LoginPage.css'
import { FcGoogle } from "react-icons/fc";
import { RxCross2 } from "react-icons/rx";
import { useLocation } from 'react-router-dom';
import { Link, NavLink, useNavigate } from 'react-router-dom';

export const LoginPage = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const from = '/';

    return (
        <div className='overlay'>
            <div className='LoginPage'>
                <div className='header'>Log in/Sign in<hr></hr></div>
                <NavLink to={from} className='cross'><RxCross2 /></NavLink>
                <form onSubmit={async (e) => {
                    e.preventDefault();
                    try {
                        const response = await fetch(`${import.meta.env.VITE_API_URL}/Login`, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({
                                username: e.target.username.value,
                                password: e.target.password.value
                            })
                        });

                        if (response.ok) {
                            const data=await response.json();
                            const token = data.token;
                            const role=data.role;
                            const logintime = Date.now();
                            localStorage.setItem("token", token);
                            localStorage.setItem("role", role);
                            localStorage.setItem("logintime", logintime);
                            navigate(from, { replace: true });
                            window.location.reload();
                            console.log(token + " " + role);
                        }
                        else {
                            alert("Invalid Credentials")
                        }
                    } catch (error) {
                        console.error("Login failed", error);
                    }
                }}>

                    <div>
                        <label htmlFor="username">Username</label>
                        <input type="text" id="username" name="username" required />
                    </div>
                    <div>
                        <label htmlFor="password">Password</label>
                        <input type="password" id="password" name="password" required />
                    </div>
                    <button type="submit">Login</button>

                </form>

                <div style={{ color: '#09223c' }}>do not have an account?<a href="#" onClick={()=>{
                    navigate('/CreateAccount',
                        {state: {from: from},
                         replace: true }
                    )
                }}>Create Account</a></div>

                <div style={{ color: '#09223c' }}>OR</div>

                <div className='google' onClick={async () => {
                    window.location.href = `${import.meta.env.VITE_API_URL}/oauth2/authorization/google`
                }}><FcGoogle /> Continue with google</div>
            </div>
        </div>
    )
}
