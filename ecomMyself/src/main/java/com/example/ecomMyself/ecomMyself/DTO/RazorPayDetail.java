package com.example.ecomMyself.ecomMyself.DTO;

import java.math.BigDecimal;

public record RazorPayDetail (
    String key,
    String orderId,
    BigDecimal amount
){}
