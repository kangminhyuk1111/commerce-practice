package com.commerce.controller.v1;

import com.commerce.controller.dto.response.CategoryResponse;
import com.commerce.domain.category.service.CategoryService;
import com.commerce.support.response.ApiResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(final CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/v1/category")
  public ApiResponse<List<CategoryResponse>> findCategories() {
    final List<CategoryResponse> categoryResponses = categoryService.findAll().stream().map(CategoryResponse::of).toList();
    return ApiResponse.success(categoryResponses);
  }
}
