import React from 'react'
import { CouponsData } from './CouponsData'
import './Coupons.css'

export const Coupons =  ({ selectable = false, onSelect }) => {
    const Coupons=CouponsData();
  return (
    <div className='Coupon-outer-box'>
        {Coupons?.map((Coupon, index) => (
            <div className='CouponBox' onClick={() => {
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
