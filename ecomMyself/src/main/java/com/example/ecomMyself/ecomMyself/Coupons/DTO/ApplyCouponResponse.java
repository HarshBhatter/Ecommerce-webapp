package com.example.ecomMyself.ecomMyself.Coupons.DTO;

import java.math.BigDecimal;

public record ApplyCouponResponse(
        String code,
        BigDecimal OriginalCartValue,
        BigDecimal discount,
        BigDecimal FinalCartValue
) {
}
