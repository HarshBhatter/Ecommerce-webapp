package com.example.ecomMyself.ecomMyself.DTO;

import java.util.List;
public record Order_request (
    List<Order_item_request> orderItemRequests
){}
