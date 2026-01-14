package com.example.ecomMyself.ecomMyself.model.DTO;

import java.math.BigDecimal;

public record Cart_response (
        int productId,
        String name,
        String color,
        int size,
        int quantity,
        BigDecimal total
){
}
