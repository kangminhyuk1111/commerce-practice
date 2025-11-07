package com.commerce.controller.dto.response;

import com.commerce.domain.cart.domain.Cart;
import com.commerce.domain.cart.domain.CartItem;
import java.util.List;

public record CartResponse(
  Long userId,
  List<CartItem> items
) {
  public static CartResponse of(Cart cart) {
    return new CartResponse(cart.userId(), cart.items());
  }
}
