import React, { useState , useEffect} from 'react'
import './IndividualProduct.css'
import { Products } from '../Products/Products'
import { IndividualProductData } from './IndividualProductData'
import { useParams } from 'react-router-dom'
import { FaRupeeSign } from "react-icons/fa";
import { NavLink,Link,Navigate } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'
import { useLocation } from 'react-router-dom'


export const Individualproduct = () => {
  const location=useLocation();
  const navigate=useNavigate();
  const {id}=useParams();
  const data = IndividualProductData(id);
  const [selectedColor, setSelectedColor] = useState(null);
  const [selectedSize, setSelectedSize] = useState(null);
  const [pic,setPic] =useState(data?.color?.[0]?.picture);  
  useEffect(()=>{
      setPic(data?.color?.[0]?.picture)
    },[data.color])
  // console.log(selectedColor)
  return (
    <>
        <div className='OuterBox'>
            <div className='image'>
                <img src={`data:image/jpeg;base64,${pic}`} alt={data.name}/>
            </div>
            <div className='details'>
                <div className='product-name'>{data.name}</div>
                <div className='product-description'>{data.description}</div>
                <div className='product-fit'>Fit : {data.fit}</div>
                <div className='product-type'>Type : {data.type}</div>

                <div className='product-price'><FaRupeeSign margin-top="20%" />{data.price}</div>
              
                <div className='colors'>Colors :
                      {data?.color?.map((c, index) => (
                        <div key={index} 
                             className="color-box" 
                             style={{ backgroundColor: c.color, border: selectedColor === c.color ? '2px solid white' : '1px solid rgb(197, 171, 171)' }} 
                             title={c.color}
                             onClick={() => {setSelectedColor(c.color),setPic(c.picture)}}
                        ></div> 
                      ))}
                </div>
                <div >
                {
                  selectedColor!=null && (
                    data?.color?.map((c, index) => (
                        c.color === selectedColor && (
                            <div className='colors'>Sizes :{
                            c.size.map((s, index) => (
                              <div key={index} 
                              className="size-box" 
                              style={{border : selectedSize === s.size ? '2px solid white':'1px solid rgb(197, 171, 171)'}}
                              onClick={()=>{setSelectedSize(s.size)}}>{s.size}</div>
                            ))
                          }</div>
                      )
                    ))
                  )
                }
                </div>
                {
                  <div className='add-to-cart-button' onClick={
                    () => {
                      if(localStorage.getItem("token")==null)
                      {
                          navigate('/login',
                              {state: {from: location},
                               replace: true }
                          );
                      }
                      else if(!selectedColor || !selectedSize)
                        alert("Select Color and Size")
                      else
                      {
                        selectedColor!=null && selectedSize!=null && localStorage.getItem('token')!=null && (
                        fetch(`${import.meta.env.VITE_API_URL}/AddToCart`, {
                          method: 'POST',
                          headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${localStorage.getItem('token')}`
                          },
                          body: JSON.stringify({
                            productid: data.id,
                            color: selectedColor,
                            size: selectedSize,
                            quantity: 1
                          })})).then(() => window.location.reload())
                      }}
                  }>Add to Cart</div>
                }
            </div>
        </div>
        <div className='header'>More of Such Types</div>
        <div>{<Products />}</div>
    </>
  )
}
