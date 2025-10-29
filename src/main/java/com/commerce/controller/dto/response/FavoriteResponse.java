package com.commerce.controller.dto.response;

import com.commerce.domain.favorite.domain.Favorite;

public record FavoriteResponse(
    Long id,
    Long productId
) {
    public static FavoriteResponse of(Favorite favorite) {
        return new FavoriteResponse(
            favorite.getId(),
            favorite.getProductId()
        );
    }
}
