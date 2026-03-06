package com.example.ecomMyself.ecomMyself.model.DTO;

import java.math.BigDecimal;

public record RazorPayDetail (
    String key,
    String orderId,
    BigDecimal amount
){}
