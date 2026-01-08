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
import { createBrowserRouter,RouterProvider } from "react-router-dom"
import { LoginOauth } from './LoginOauth/loginOauth.jsx'



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
      { path: "/cart", element: <Cart /> },
      { path: "/product/:id", element: <Individualproduct /> },
      { path: "/products/Mens", element: <Products /> },
      { path: "/products/Womens" , element:<Products/> },
      { path: "/order/:id", element: <IndividualOrder /> },
      { path: "/login/Oauth", element: <LoginOauth /> }
    ],
  },
]);

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <RouterProvider router={router}  />
  </StrictMode>
)
