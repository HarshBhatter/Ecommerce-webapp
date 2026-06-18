package com.example.ecomMyself.ecomMyself.Coupons.Categories;

import com.example.ecomMyself.ecomMyself.Coupons.Factory.StrategyFactory;
import com.example.ecomMyself.ecomMyself.Coupons.Model.Coupon;
import com.example.ecomMyself.ecomMyself.Coupons.Strategy.CouponStrategy;
import com.example.ecomMyself.ecomMyself.model.Cart;
import com.example.ecomMyself.ecomMyself.model.Product;
import com.example.ecomMyself.ecomMyself.model.Users;
import com.example.ecomMyself.ecomMyself.repository.Cart_repo;
import com.example.ecomMyself.ecomMyself.repository.Product_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
@Component
public class ProductCategory extends Category{
    @Autowired
    private Product_Repo productRepo;
    @Autowired
    private Cart_repo cart_repo;
    @Autowired
    private StrategyFactory strategyFactory;
    @Override
    public boolean isApplicable(Users user, Coupon coupon, List<Cart> cart) {
        for(Cart item:cart)
        {
            int id=item.getProductId();
            String productGender=productRepo.findById(id).get().getGender();
            if(coupon.getGender().equals(productGender))
                    return true;
        }
        return false;
    }

    @Override
    public double apply(Users user, Coupon coupon) {
        List<Cart> cart=cart_repo.findAllByUserId(user.getId());
        CouponStrategy strategy=strategyFactory.getStrategy(coupon.getDiscountType());
        double ans=0d;
        System.out.println(strategy+" "+coupon.getGender());
        for(Cart item:cart)
        {
            Product product=productRepo.findById(item.getProductId()).get();
            String productGender=product.getGender();
            BigDecimal total=product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            if(coupon.getGender().equals(productGender)){
                ans+=strategy.calculate(total.doubleValue(),coupon.getDiscountValue(), coupon.getMaxDiscount());
            }
        }
        return ans;
    }
}
