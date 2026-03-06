import React from 'react'
import { IndividualOrderData } from './IndividualOrderData';
import '../Cart/Cart.css'
import './IndividualOrder.css'
import { useLocation } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import { FaRupeeSign } from "react-icons/fa";


export const IndividualOrder = () => {
    const {id}=useParams();
    const data=IndividualOrderData(id);
    console.log(data);
   return (
     <>
         <div className='header'>Order Id : {data.orderId}</div>
         <div className="Cart">
             <table >
                 <thead>
                 <tr className='header'>
                     <th className='serial'>Serial No.</th>
                     <th>Product Name</th>
                     <th>Color</th>
                     <th>Size</th>
                     <th>Quantity</th>
                     <th>Total</th>
                 </tr>
                 </thead>
 
                 {data?.items?.map((product,index)=>(
                     <tbody>
                     <tr>
                         <td>{index+1}</td>
                         <td>{product.productName}</td>
                         <td>{product.color}</td>
                         <td>{product.size}</td>
                         <td>{product.quantity}</td>
                         <td><FaRupeeSign />{product.total}</td>
                     </tr>
                     </tbody>
                 ))}

                    <tbody>
                     <tr>
                         <td></td>
                         <td></td>
                         <td></td>
                         <td></td>
                         <td></td>
                         <td className='total' ><FaRupeeSign />{data.total}</td>
                     </tr>
                    </tbody>
                
             </table>
         </div>
 
     </>
   )
}
