package com.example.ecomMyself.ecomMyself.DTO;

import com.example.ecomMyself.ecomMyself.Coupons.Model.Coupon;
import com.example.ecomMyself.ecomMyself.Embedable.Address;
import com.example.ecomMyself.ecomMyself.model.Users;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderSummary_response(
        long id,
        String couponCode,
        Address address,
        BigDecimal total,
        BigDecimal discount,
        BigDecimal discountedTotal,
        LocalDateTime expiry
) {
}
