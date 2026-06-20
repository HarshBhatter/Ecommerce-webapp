package com.example.ecomMyself.ecomMyself.Coupons.Strategy;

import org.springframework.stereotype.Component;

public interface CouponStrategy {
    double calculate(Double amt,Double discount,Double cap);
}

