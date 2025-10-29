package com.commerce.controller.dto.request;

import com.commerce.controller.dto.request.type.ApplyFavoriteType;

public record ApplyFavoriteRequest(Long productId, ApplyFavoriteType type) {
}