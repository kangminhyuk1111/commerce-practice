package com.commerce.domain.cart.domain;

import com.commerce.controller.dto.response.CartItemResponse;
import java.util.List;

public record Cart(
    Long userId,
    List<CartItemResponse> items
) {
}
