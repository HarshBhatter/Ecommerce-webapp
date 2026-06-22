import React from 'react'
import { useLocation } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'
import { NavLink } from 'react-router-dom'
import { RxCross2 } from "react-icons/rx";


export const CreateAccount = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const from = location.state?.from?.pathname || '/';
  return (
    <>
        <div className='overlay'>
            <div className='LoginPage'>
                <div className='header'>Create Account</div>
                <NavLink to="/" className='cross'><RxCross2 /></NavLink>
                <form onSubmit={async (e) => {
                    e.preventDefault();
                    try {
                        const response = await fetch(`${import.meta.env.VITE_API_URL}/create_account`, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({
                                username: e.target.username.value,
                                password: e.target.password.value,
                                email:e.target.email.value
                            })
                        });

                        if (response.ok) {
                            const data = await response.json();
                            localStorage.setItem("token", data.token);
                            localStorage.setItem("role", data.role);
                            localStorage.setItem("logintime", Date.now());
                            navigate(from, { replace: true });
                            window.location.reload();
                            console.log(token);
                        }
                        else (
                            alert("Invalid Credentials/Username already exists")
                        )
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
                    <div>
                        <label htmlFor="email">Email</label>
                        <input type="email" id="email" name="email" required />
                    </div>
                    <button type="submit">Create</button>

                </form>
            </div>
        </div>
    </>
  )
}
