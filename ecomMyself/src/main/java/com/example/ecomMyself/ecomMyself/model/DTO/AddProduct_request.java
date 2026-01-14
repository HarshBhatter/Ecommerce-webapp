package com.example.ecomMyself.ecomMyself.model.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public record AddProduct_request(
        String name,
        String Type,
        String fit,
        BigDecimal price,
        String description,
        String gender,
        String color,
        List<AddProductSize_request> addProductSizeRequest
) {
}
