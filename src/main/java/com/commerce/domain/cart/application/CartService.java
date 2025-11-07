package com.commerce.domain.cart.application;

import com.commerce.controller.dto.response.CartItemResponse;
import com.commerce.domain.cart.domain.Cart;
import com.commerce.domain.cart.domain.CartItem;
import com.commerce.domain.cart.repository.CartItemRepository;
import com.commerce.domain.product.entity.Product;
import com.commerce.domain.product.repository.ProductRepository;
import com.commerce.support.error.CoreException;
import com.commerce.support.error.ErrorType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

  private final CartItemRepository cartItemRepository;
  private final ProductRepository productRepository;

  public CartService(CartItemRepository cartItemRepository, ProductRepository productRepository) {
    this.cartItemRepository = cartItemRepository;
    this.productRepository = productRepository;
  }

  /**
   * 장바구니 상품 조회
   */
  public Cart getCart(Long userId) {
    List<CartItem> items = cartItemRepository.findByUserId(userId);

    List<Product> products = productRepository.findAllById(items.stream().map(CartItem::getProductId).toList());

    Map<Long, Product> productMap = products.stream()
        .collect(Collectors.toMap(Product::getId, p -> p));

    List<CartItemResponse> filteredItems = items.stream()
        .filter(item -> productMap.containsKey(item.getProductId()))
        .map(item -> CartItemResponse.of(item.getId(), productMap.get(item.getProductId()), item.getQuantity()))
        .toList();

    return new Cart(userId, filteredItems);
  }

  /**
   * 장바구니 상품 추가
   */
  @Transactional
  public void addCartItem(Long userId, Long productId, Integer quantity) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new CoreException(ErrorType.PRODUCT_NOT_FOUND));

    CartItem cartItem = new CartItem(
        userId,
        product.getId(),
        quantity,
        LocalDateTime.now(),
        LocalDateTime.now().plusDays(30)
    );

    cartItemRepository.save(cartItem);
  }

  /**
   * 장바구니 상품 삭제
   */
  @Transactional
  public void deleteCartItem(Long userId, Long cartItemId) {
    CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, cartItemId)
        .orElseThrow(() -> new CoreException(ErrorType.QUESTION_NOT_FOUND));

    cartItemRepository.deleteById(cartItem.getId());
  }

  /**
   * 장바구니 상품 수량 수정
   */
  @Transactional
  public void modifyCartItemQuantity(Long userId, Long productId, Integer quantity) {
    CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId)
        .orElseThrow(() -> new CoreException(ErrorType.QUESTION_NOT_FOUND));

    cartItem.modifyingQuantity(quantity);
  }
  
  // TODO 배치를 이용해 만료기간이 지난 장바구니 아이템은 자동 삭제 처리
  public void cleanupExpiredCartItems() {

  }
}
