package com.commerce.domain.category.service;

import com.commerce.domain.category.entity.Category;
import com.commerce.domain.category.repository.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(final CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }
}
