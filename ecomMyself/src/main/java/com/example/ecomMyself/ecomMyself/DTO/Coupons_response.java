package com.example.ecomMyself.ecomMyself.DTO;

import java.time.LocalDate;

public record Coupons_response(
        long id,
        String code,
        String description,
        LocalDate expiryDate
) {
}
