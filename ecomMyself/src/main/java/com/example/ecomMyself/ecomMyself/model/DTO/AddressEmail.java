package com.example.ecomMyself.ecomMyself.model.DTO;

public record AddressEmail(
        String street,
        String city,
        String state,
        String email,
        int pincode
) {
}
