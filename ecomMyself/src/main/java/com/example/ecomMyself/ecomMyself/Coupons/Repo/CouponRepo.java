package com.example.ecomMyself.ecomMyself.Coupons.Repo;

import com.example.ecomMyself.ecomMyself.Coupons.Model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepo extends JpaRepository<Coupon,Long> {

    Optional<Coupon> findByCode(String couponCode);
}
