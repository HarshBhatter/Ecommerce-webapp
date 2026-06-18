package com.example.ecomMyself.ecomMyself.Coupons.Categories;

import com.example.ecomMyself.ecomMyself.Coupons.Model.Coupon;
import com.example.ecomMyself.ecomMyself.model.Cart;
import com.example.ecomMyself.ecomMyself.model.Users;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Component
public class Seasonal extends Category {

    @Override
    public boolean isApplicable(Users user, Coupon coupon, List<Cart> cart) {
        if(LocalDate.now().isAfter(coupon.getStartDate()) && LocalDate.now().isBefore(coupon.getExpiryDate()))
            return true;
        return false;
    }
}
