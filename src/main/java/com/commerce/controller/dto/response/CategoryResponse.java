package com.commerce.controller.dto.response;

import com.commerce.domain.category.entity.Category;

public record CategoryResponse(Long id, String name) {
  public static CategoryResponse of(Category category) {
    return new CategoryResponse(category.getId(), category.getName());
  }
}
