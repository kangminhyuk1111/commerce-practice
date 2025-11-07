package com.commerce.controller.dto.request;

public record AddCartItemRequest(
    Long productId,
    Integer quantity
) {
}
