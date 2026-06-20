package com.example.ecomMyself.ecomMyself.DTO;

import java.math.BigDecimal;

public record Order_item_response(
        String productName,
        int quantity,
        String color,
        int size,
        BigDecimal total
) {}
