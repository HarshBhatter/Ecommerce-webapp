package com.example.ecomMyself.ecomMyself.Coupons.Strategy;

import org.springframework.stereotype.Component;

@Component
public class Percentage_with_cap implements CouponStrategy{
    @Override
    public double calculate(Double amt, Double discount, Double cap) {
        return Math.min(cap,(amt*discount/100));
    }
}
