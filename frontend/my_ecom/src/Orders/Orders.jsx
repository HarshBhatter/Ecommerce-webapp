import React from 'react'
import './Orders.css'
import { OrdersData } from './OrdersData'
import { Link,NavLink } from 'react-router-dom';
import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { FaRegBell, FaRupeeSign } from "react-icons/fa";



export const Orders = () => {

  const location=useLocation();
    const navigate=useNavigate();
    useEffect(() => {
        if(localStorage.getItem('token')===null){
            navigate('/login',
                {state: {from: location},
                 replace: true }
            );
        }
    },);
  const data=OrdersData();
  console.log(data)
  if(data.length===0){
    return(<div className='header'>No Orders Placed Yet!</div>
    )
  }
  return (
    <>
      <div className='header' >My Orders</div>
      <div className='Cart'><hr />
        <table>
          <thead>
            <tr className='header'>
              <th className='serial'>Serial no.</th>
              <th >Order Id</th>
              <th>Order Date</th>   
              <th >Status</th>   
              <th >Product Names</th>
              <th >Price</th>
            </tr>
          </thead>

          {data.map((order,index)=>(
            <tbody>
              <tr>
                <td>{index + 1}</td>
                <td><NavLink to={`/order/${order.id}`}>{order.orderId}</NavLink></td>
                <td>{(new Date(order.orderDate).toLocaleDateString())}</td>
                <td style={{ color: 'rgb(23, 62, 4)' }}>{order.status}</td>
                <td>{order.items.map((items)=>(
                  <div>{items.productName}</div>
                ))}</td>
                <td><FaRupeeSign />{order.total}</td>
              </tr>
            </tbody>
        ))}
        </table>
        

      </div>
    </>
  )
}
