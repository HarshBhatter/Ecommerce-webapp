package com.example.ecomMyself.ecomMyself.Coupons.Categories;

import com.example.ecomMyself.ecomMyself.Coupons.Factory.StrategyFactory;
import com.example.ecomMyself.ecomMyself.Coupons.Model.Coupon;
import com.example.ecomMyself.ecomMyself.Coupons.Strategy.CouponStrategy;
import com.example.ecomMyself.ecomMyself.model.Cart;
import com.example.ecomMyself.ecomMyself.model.Users;
import com.example.ecomMyself.ecomMyself.repository.Orders_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class NewUser extends Category {
    @Autowired
    private Orders_repo ordersRepo;
    @Autowired
    private StrategyFactory strategyFactory;

    @Override
    public boolean isApplicable(Users user, Coupon coupon, List<Cart> cart) {
        if(ordersRepo.findAllByUserId(user.getId()).size()==0)
            return true;
        return false;
    }
}
