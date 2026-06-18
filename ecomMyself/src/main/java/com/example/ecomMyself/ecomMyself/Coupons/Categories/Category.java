package com.example.ecomMyself.ecomMyself.Coupons.Categories;

import com.example.ecomMyself.ecomMyself.Coupons.Factory.StrategyFactory;
import com.example.ecomMyself.ecomMyself.Coupons.Model.Coupon;
import com.example.ecomMyself.ecomMyself.Coupons.Strategy.CouponStrategy;
import com.example.ecomMyself.ecomMyself.model.Cart;
import com.example.ecomMyself.ecomMyself.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Component
public abstract class Category {
    @Autowired
    private StrategyFactory strategyFactory;
    public boolean isValid(Users user, Coupon coupon, List<Cart> cart)
    {
        System.out.println(checkExpiry(coupon)+" "+checkActive(coupon)+" "+checkUsage(user,coupon)+" "+isApplicable(user,coupon,cart));
        if(!checkExpiry(coupon) || !checkActive(coupon) || !checkUsage(user,coupon))
            return false;
        return isApplicable(user,coupon,cart);
    }
    public boolean checkExpiry(Coupon coupon)
    {
        if(LocalDate.now().isBefore(coupon.getExpiryDate()))
            return true;
        return false;
    }
    public boolean checkActive(Coupon coupon)
    {
        return coupon.isActive();
    }
    public boolean checkUsage(Users user,Coupon coupon)
    {
        HashMap<Long, Integer> usage=coupon.getUsage();
        return usage.getOrDefault(user.getId(),0)<coupon.getLimitPerUser();
    }

    public abstract boolean isApplicable(Users user, Coupon coupon, List<Cart> cart);
    public double apply(Users user, Coupon coupon)
    {
        CouponStrategy strategy=strategyFactory.getStrategy(coupon.getDiscountType());
        System.out.println(strategy+" "+coupon.getDiscountValue());
        return strategy.calculate(user.getCartValue().doubleValue(),coupon.getDiscountValue(),coupon.getMaxDiscount());
    }
}

