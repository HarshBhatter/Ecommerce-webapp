package com.example.ecomMyself.ecomMyself.DTO;

public record Order_item_request (
    int productid,
    String color,
    int size,
    int quantity
){}
