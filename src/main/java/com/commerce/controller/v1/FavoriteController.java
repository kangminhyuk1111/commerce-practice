package com.commerce.controller.v1;

import com.commerce.controller.dto.request.ApplyFavoriteRequest;
import com.commerce.controller.dto.response.FavoriteResponse;
import com.commerce.controller.dto.request.PageSize;
import com.commerce.controller.dto.response.PageResponse;
import com.commerce.domain.favorite.application.FavoriteService;
import com.commerce.domain.favorite.domain.Favorite;
import com.commerce.support.response.ApiResponse;
import org.hibernate.mapping.Any;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FavoriteController {

  private final FavoriteService favoriteService;

  public FavoriteController(FavoriteService favoriteService) {
    this.favoriteService = favoriteService;
  }

  @GetMapping("/v1/favorites")
  public ApiResponse<PageResponse<FavoriteResponse>> getFavorites(
      Long userId, // TODO 추후 AuthHandler 추가
      @RequestParam(defaultValue = "0") Integer offset,
      @RequestParam(defaultValue = "5") Integer limit,
      @RequestParam(defaultValue = "createdAt,desc") String sort
  ) {
    Page<Favorite> favorites = favoriteService.findFavorites(userId, PageSize.of(offset, limit).toPageable(sort));
    Page<FavoriteResponse> responses = favorites.map(FavoriteResponse::of);
    return ApiResponse.success(PageResponse.of(responses));
  }

  @PostMapping("/v1/favorites/{productId}")
  public ApiResponse<Any> addFavorites(
      Long userId, // TODO 추후 AuthHandler 추가
      @RequestBody ApplyFavoriteRequest request
  ) {
    favoriteService.applyFavorite(userId, request.productId(), request.type());
    return ApiResponse.success();
  }
}
