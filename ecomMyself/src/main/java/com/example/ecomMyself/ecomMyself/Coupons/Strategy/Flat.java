package com.example.ecomMyself.ecomMyself.Coupons.Strategy;

import org.springframework.stereotype.Component;

@Component
public class Flat extends CouponStrategy {
    @Override
    public double calculate(Double amt, Double discount, Double cap) {
        return Math.min(discount,amt);
    }
}
