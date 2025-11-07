package com.commerce.controller.v1;

import com.commerce.controller.dto.request.AddCartItemRequest;
import com.commerce.controller.dto.request.UpdateCartItemQuantityRequest;
import com.commerce.controller.dto.response.CartResponse;
import com.commerce.domain.cart.application.CartService;
import com.commerce.domain.cart.domain.Cart;
import com.commerce.support.response.ApiResponse;
import org.hibernate.mapping.Any;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @GetMapping("/v1/cart")
  public ApiResponse<CartResponse> getCart(Long userId) {
    Cart cart = cartService.getCart(userId);
    return ApiResponse.success(CartResponse.of(cart));
  }

  @PostMapping("/v1/cart/items")
  public ApiResponse<Any> addCartItem(
      Long userId,
      @RequestBody AddCartItemRequest request) {
    cartService.addCartItem(userId, request.productId(),request.quantity());
    return ApiResponse.success();
  }

  @DeleteMapping("/v1/cart/items/{cartItemId}")
  public ApiResponse<Any> deleteCartItem(Long userId, @PathVariable Long cartItemId) {
    cartService.deleteCartItem(userId, cartItemId);
    return ApiResponse.success();
  }

  @PutMapping("/v1/cart/items/{cartItemId}")
  public ApiResponse<Any> modifyingCartItemQuantity(Long userId, @PathVariable Long cartItemId, @RequestBody UpdateCartItemQuantityRequest request) {
    cartService.modifyCartItemQuantity(userId, cartItemId, request.quantity());
    return ApiResponse.success();
  }
}
