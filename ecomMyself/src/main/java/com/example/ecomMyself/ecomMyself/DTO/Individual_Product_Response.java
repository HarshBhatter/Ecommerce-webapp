package com.example.ecomMyself.ecomMyself.DTO;

import java.math.BigDecimal;
import java.util.List;

public record Individual_Product_Response(
        int id,
        String name,
        String type,
        String fit,
        BigDecimal price,
        String description,
        String gender,
        List<Individual_Product_Colors_Response> color
) {
}
