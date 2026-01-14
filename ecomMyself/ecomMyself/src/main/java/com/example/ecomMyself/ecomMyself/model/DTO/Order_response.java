package com.example.ecomMyself.ecomMyself.model.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record Order_response(
    long id,
    String orderId,
    String status,
    LocalDate orderDate,
    List<Order_item_response> items,
    BigDecimal total
){}
