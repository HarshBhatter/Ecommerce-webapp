package com.example.ecomMyself.ecomMyself.Coupons.Categories;

import com.example.ecomMyself.ecomMyself.Coupons.Model.Coupon;
import com.example.ecomMyself.ecomMyself.model.Cart;
import com.example.ecomMyself.ecomMyself.model.Users;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class BulkOrder extends Category{
    @Override
    public boolean isApplicable(Users user, Coupon coupon, List<Cart> cart) {
        if(user.getCartValue().compareTo(coupon.getMinimumCartValue())>=0)
            return true;
        return false;
    }
}
