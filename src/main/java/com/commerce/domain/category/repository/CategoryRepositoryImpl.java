package com.commerce.domain.category.repository;

import com.commerce.domain.category.entity.Category;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

  private final JpaCategoryRepository jpaCategoryRepository;

  public CategoryRepositoryImpl(final JpaCategoryRepository categoryRepository) {
    this.jpaCategoryRepository = categoryRepository;
  }

  public List<Category> findAll() {
    return jpaCategoryRepository.findAll();
  }
}
