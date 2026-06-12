import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { Outlet } from 'react-router-dom'
import './index.css'
import App from './App.jsx'
import { Navbar } from './Navbar/Navbar.jsx'
import { Homepage } from './HomePage/HomePage.jsx'
import { LoginPage } from './LoginPage/LoginPage.jsx'
import { Orders } from './Orders/Orders.jsx'
import { Cart } from './Cart/Cart.jsx'
import { Products } from './Products/Products.jsx'
import { Individualproduct } from './Individualproduct/Individualproduct.jsx';
import { IndividualOrder } from './IndividualOrder/IndividualOrder.jsx'
import { createBrowserRouter, RouterProvider, Navigate } from "react-router-dom"
import { LoginOauth } from './LoginOauth/loginOauth.jsx'
import { AddProduct } from './AddProduct/AddProduct.jsx'
import { CreateAccount } from './CreateAccount/CreateAccount.jsx'
import { PlaceOrder} from './PlaceOrder/PlaceOrder.jsx'
import {CheckOut} from './CheckOut/CheckOut.jsx'

const AdminRoute = ({ children }) => {
  const role = localStorage.getItem("role");
  return role === "Admin" ? children : <Navigate to="/" replace />;
};

export const Layout = () => {//L is capital as layout is html tag
  return (
    <>
      <Navbar />
      <App />
      <Outlet />
    </>
  );
};
const router = createBrowserRouter([
  {
    element: <Layout />,
    children: [
      { path: "/", element: <Homepage /> },
      { path: "/login", element: <LoginPage /> },
      { path: "/orders", element: <Orders /> },
      { path:"/PlaceOrder",element:<PlaceOrder />},
      {path :"/Checkout",element:<CheckOut/>},
      { path: "/cart", element: <Cart /> },
      { path: "/product/:id", element: <Individualproduct /> },
      { path: "/products/Mens", element: <div><div className='header'>Mens Category</div><hr color='black'></hr><Products /></div> },
      { path: "/products/Womens", element: <div><div className='header' display='flex' justify-content='center' align-items='center' >Womens Category</div><hr color='black'></hr><Products /></div> },
      { path: "/order/:id", element: <IndividualOrder /> },
      { path: "/login/Oauth", element: <LoginOauth /> },
      { path: "/AddProduct", element: <AdminRoute><AddProduct /></AdminRoute> },
      { path: "/CreateAccount", element: <CreateAccount /> }
    ],
  },
]);

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
)
