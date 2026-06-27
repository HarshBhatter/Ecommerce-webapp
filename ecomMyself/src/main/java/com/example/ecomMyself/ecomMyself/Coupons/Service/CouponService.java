package com.example.ecomMyself.ecomMyself.Coupons.Service;

import com.example.ecomMyself.ecomMyself.Coupons.Categories.Category;
import com.example.ecomMyself.ecomMyself.Coupons.DTO.ApplyCouponResponse;
import com.example.ecomMyself.ecomMyself.Coupons.Factory.CategoryFactory;
import com.example.ecomMyself.ecomMyself.Coupons.Model.Coupon;
import com.example.ecomMyself.ecomMyself.Coupons.Repo.CouponRepo;
import com.example.ecomMyself.ecomMyself.DTO.Coupons_response;
import com.example.ecomMyself.ecomMyself.model.Cart;
import com.example.ecomMyself.ecomMyself.model.OrderSummary;
import com.example.ecomMyself.ecomMyself.model.Users;
import com.example.ecomMyself.ecomMyself.repository.Cart_repo;
import com.example.ecomMyself.ecomMyself.repository.OrderSummary_repo;
import com.example.ecomMyself.ecomMyself.repository.User_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CouponService {
    @Autowired
    private User_Repo user_repo;
    @Autowired
    private CouponRepo couponRepo;
    @Autowired
    private Cart_repo cart_repo;
    @Autowired
    private CategoryFactory categoryFactory;
    @Autowired
    private OrderSummary_repo orderSummary_repo;
    public List<?> getApplicableCoupons(Users user)
    {
        List<Cart> cart=cart_repo.findAllByUserId(user.getId());

        List<Coupon> coupons=couponRepo.findAll();
        List<Coupons_response> ans=new ArrayList<>();

        for(Coupon c:coupons)
        {
            Category category=categoryFactory.getCategory(c.getCouponCategory());
            if(category.isValid(user,c,cart))
                ans.add(new Coupons_response(c.getId(),c.getCode(),c.getDescription(),c.getExpiryDate()));
        }
        return ans;
    }
    public ApplyCouponResponse apply(Users user,String couponCode)
    {
        Coupon coupon;
        try {
            coupon = couponRepo.findByCode(couponCode).get();
        } catch (Exception e)
        {
            throw new RuntimeException("Invalid Coupon Code");
        }
        Category category=categoryFactory.getCategory(coupon.getCouponCategory());
        List<Cart> cart=cart_repo.findAllByUserId(user.getId());
        OrderSummary orderSummary=orderSummary_repo.findByUser(user).orElse(new OrderSummary());
        orderSummary.setCoupon(null);
        orderSummary.setTotal(user.getCartValue());
        orderSummary.setUser(user);

        BigDecimal amount_to_deduct=BigDecimal.ZERO;

        System.out.println("valid ="+category.isValid(user,coupon,cart));

        if(category.isValid(user,coupon,cart)) {
            amount_to_deduct = BigDecimal.valueOf(category.apply(user, coupon));
            orderSummary.setCoupon(coupon);
        }

        orderSummary.setDiscount(amount_to_deduct);
        orderSummary.setDiscountedTotal(user.getCartValue().subtract(amount_to_deduct));
        orderSummary_repo.save(orderSummary);

        System.out.println(coupon.getDiscountType()+" "+amount_to_deduct);
        return new ApplyCouponResponse(coupon.getCode(),user.getCartValue(),amount_to_deduct,user.getCartValue().subtract(amount_to_deduct));

    }
    public String remove(Users user)
    {
        OrderSummary orderSummary=orderSummary_repo.findByUser(user).orElse(new OrderSummary());
        orderSummary.setCoupon(null);
        orderSummary.setTotal(user.getCartValue());
        orderSummary.setDiscount(BigDecimal.ZERO);
        orderSummary.setDiscountedTotal(user.getCartValue());
        orderSummary_repo.save(orderSummary);

//        System.out.println(coupon.getDiscountType()+" "+amount_to_deduct);
        return "removed";

    }
    public void setCouponUsage(Users user,Long couponId)
    {
        Coupon coupon=couponRepo.findById(couponId).get();
        HashMap<Long,Integer> usage_map=coupon.getUsage();
        usage_map.put((long)user.getId(),usage_map.getOrDefault(user.getId(),0)+1);
        coupon.setUsage(usage_map);
        couponRepo.save(coupon);
    }

    public List<?> getAllCoupons(Users user)
    {
        List<Coupon> coupons=couponRepo.findAll();
        List<Coupons_response> ans=new ArrayList<>();

        for(Coupon c:coupons)
        {
            Category category=categoryFactory.getCategory(c.getCouponCategory());
            if(category.checkExpiry(c) && category.checkActive(c) && category.checkUsage(user,c))
                ans.add(new Coupons_response(c.getId(),c.getCode(),c.getDescription(),c.getExpiryDate()));
        }
        return ans;
    }
}
