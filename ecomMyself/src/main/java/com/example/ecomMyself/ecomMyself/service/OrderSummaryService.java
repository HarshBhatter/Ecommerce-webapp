package com.example.ecomMyself.ecomMyself.service;

import com.example.ecomMyself.ecomMyself.Coupons.Service.CouponService;
import com.example.ecomMyself.ecomMyself.Embedable.Address;
import com.example.ecomMyself.ecomMyself.model.OrderSummary;
import com.example.ecomMyself.ecomMyself.model.Users;
import com.example.ecomMyself.ecomMyself.repository.OrderSummary_repo;
import jakarta.persistence.OptimisticLockException;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Optional;

@Service
public class OrderSummaryService {
    @Autowired
    private OrderSummary_repo orderSummary_repo;
    @Autowired
    private CouponService couponService;
    @Autowired
    private InventoryReservationService inventoryReservationService;
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
                couponService.apply(user, orderSummary.get().getCoupon().getCode());
        }
    }

    public void delete(Users user) {
        if(orderSummary_repo.existsByUser(user))
            orderSummary_repo.deleteAllByUser(user);
    }
    @Transactional
    public OrderSummary getOrderSummary(Users user)
    {
        Optional<OrderSummary> orderSummary =
                orderSummary_repo.findByUser(user);
        if(orderSummary.isPresent() && orderSummary.get().getExpiry().isAfter(LocalDateTime.now(ZoneOffset.UTC)))
            return orderSummary.get();

        for(int i=0;i<5;i++)
        {
            System.out.println(user.getUsername()+" "+i);
            try {
                inventoryReservationService.reserve(user);
                break;
            }catch (ObjectOptimisticLockingFailureException e)
            {
                if(i>=4)
                    throw new RuntimeException(e);
            }catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

        OrderSummary orderSummary2=new OrderSummary();
        orderSummary2.setUser(user);
        orderSummary2.setTotal(user.getCartValue());
        orderSummary2.setDiscountedTotal(user.getCartValue());
        orderSummary_repo.save(orderSummary2);
        System.out.println("got order Summary");
        return orderSummary2;
    }
    public void saveAddress(Users user, Address address)
    {
        OrderSummary orderSummary=orderSummary_repo.findByUser(user).get();
        orderSummary.setAddress(address);
        orderSummary_repo.save(orderSummary);
    }
    public void orderPlaced(Users user)
    {
        OrderSummary orderSummary = orderSummary_repo.findByUser(user).get();
        if(orderSummary.getCoupon()!=null)
            couponService.setCouponUsage(user,orderSummary.getCoupon().getId());

        delete(user);
    }
}
