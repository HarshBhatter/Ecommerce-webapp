package com.example.ecomMyself.ecomMyself.Coupons.Repo;

import com.example.ecomMyself.ecomMyself.Coupons.Model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepo extends JpaRepository<Coupon,Long> {

}
