import React from 'react'
import { CouponsData } from './CouponsData'
import './Coupons.css'

export const Coupons =  ({ selectable = false, onSelect }) => {
    const {Coupons,loading}=CouponsData();
    if(loading)
        return <div className='header'>Loading...</div>
    if(Coupons.length ===0)
        return <div className='header'>No Coupons Available!</div>

  return (
    <div className='Coupon-outer-box'>
        {Coupons?.map((Coupon, index) => (
            <div className='CouponBox' key={index}
                onClick={() => {
                    if (selectable) {
                        onSelect(Coupon?.code);
                    }
                }}>
                <div className='CouponCode'>{Coupon?.code}</div>
                <div className='CouponInfo'>{Coupon?.description}</div>
                <div className='ExpiryDate'>Valid till {Coupon?.expiryDate}</div>
            </div>
             
        ))}
    </div>
  )
}
