package com.example.ecomMyself.ecomMyself.Coupons.Strategy;

import org.springframework.stereotype.Component;

@Component
public class Percentage extends CouponStrategy{
    @Override
    public double calculate(Double amt, Double discount, Double cap) {
        return (amt*discount/100);
    }
}
