package com.example.ecomMyself.ecomMyself.DTO;

import java.math.BigDecimal;

public record Product_Response(
        int id,
        String name,
        BigDecimal price,
        String imageurl
) {
}
