package com.example.ecomMyself.ecomMyself.service;

import com.example.ecomMyself.ecomMyself.Coupons.Service.CouponService;
import com.example.ecomMyself.ecomMyself.model.DTO.AddressEmail;
import com.example.ecomMyself.ecomMyself.model.OrderSummary;
import com.example.ecomMyself.ecomMyself.model.Users;
import com.example.ecomMyself.ecomMyself.repository.OrderSummary_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class OrderSummaryService {
    @Autowired
    private OrderSummary_repo orderSummary_repo;
    @Autowired
    private CouponService couponService;
    public void refresh(Users user)
    {
        if(user.getCartValue().compareTo(BigDecimal.ZERO)==0) {
            delete(user);
            return;
        }

        Optional<OrderSummary> orderSummary =
                orderSummary_repo.findByUser(user);

        if(orderSummary.isPresent()) {
            if(orderSummary.get().getCoupon()!=null)
                couponService.apply(user, orderSummary.get().getCoupon().getId());
        }
    }

    public void delete(Users user) {
        if(orderSummary_repo.existsByUser(user))
            orderSummary_repo.deleteAllByUser(user);
    }
    public OrderSummary getOrderSummary(Users user)
    {
        Optional<OrderSummary> orderSummary =
                orderSummary_repo.findByUser(user);
        if(orderSummary.isPresent())
            return orderSummary.get();

        OrderSummary orderSummary2=new OrderSummary();
        orderSummary2.setUser(user);
        orderSummary2.setTotal(user.getCartValue());
        orderSummary2.setDiscountedTotal(user.getCartValue());
        orderSummary_repo.save(orderSummary2);
        return orderSummary2;
    }
    public void setAddressEmail(Users user, AddressEmail addressEmail)
    {
        OrderSummary orderSummary=orderSummary_repo.findByUser(user).get();
        orderSummary.setStreet(addressEmail.street());
        orderSummary.setCity(addressEmail.city());
        orderSummary.setState(addressEmail.state());
        orderSummary.setPincode(addressEmail.pincode());
        orderSummary.setEmailId(addressEmail.email());
        orderSummary_repo.save(orderSummary);
    }
}
