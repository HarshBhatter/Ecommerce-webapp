package com.example.ecomMyself.ecomMyself.Coupons.Strategy;

import org.springframework.stereotype.Component;

@Component
public abstract class CouponStrategy {
    public abstract double calculate(Double amt,Double discount,Double cap);
}

