import React from 'react'
import { CouponsData } from './CouponsData'
import './Coupons.css'

export const Coupons =  ({ selectable = false, onSelect }) => {
    const Coupons=CouponsData();
  return (
    <div>
        {Coupons?.map((Coupon, index) => (
            <div className='CouponBox' onClick={() => {
                    if (selectable) {
                        onSelect(Coupon);
                    }
                }}>
                <div className='CouponCode'>{Coupon?.code}</div>
                <div className='CouponInfo'>{Coupon?.description}</div>
                <div>Valid till {Coupon?.expiryDate}</div>
                <hr/>
            </div>
             
        ))}
    </div>
  )
}
