package com.example.ecomMyself.ecomMyself.DTO;

import java.util.List;

public record Individual_Product_Colors_Response(
        int id,
        String color,
        List<Individual_Product_Size_Response> size,
        String imageurl
) {
}
