package com.example.ecomMyself.ecomMyself.Coupons.Factory;

import com.example.ecomMyself.ecomMyself.Coupons.Enums.DiscountType;
import com.example.ecomMyself.ecomMyself.Coupons.Strategy.CouponStrategy;
import com.example.ecomMyself.ecomMyself.Coupons.Strategy.Flat;
import com.example.ecomMyself.ecomMyself.Coupons.Strategy.Percentage;
import com.example.ecomMyself.ecomMyself.Coupons.Strategy.Percentage_with_cap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.stereotype.Component;

@Component
public class StrategyFactory {
    @Autowired
    private Flat flat;
    @Autowired
    private Percentage percentage;
    @Autowired
    private Percentage_with_cap percentage_with_cap;
    public CouponStrategy getStrategy(DiscountType discountType)
    {
        System.out.println(discountType);
        if(discountType==DiscountType.flat)
            return flat;
        if(discountType==DiscountType.percentage)
            return percentage;
        return percentage_with_cap;
    }

}
