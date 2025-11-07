package com.commerce.domain.product.repository;

import com.commerce.domain.product.entity.ProductCategory;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeProductCategoryRepository implements ProductCategoryRepository {

  private final Map<Long, ProductCategory> store = new HashMap<>();
  private long sequence = 0L;

  @Override
  public List<ProductCategory> findByCategoryIdIn(Collection<Long> categoryIds) {
    return store.values().stream()
        .filter(pc -> categoryIds.contains(pc.getCategory().getId()))
        .toList();
  }

  @Override
  public List<ProductCategory> findByProductId(Long productId) {
    return List.of();
  }

  public <S extends ProductCategory> S save(S entity) {
    if (entity.getId() == null) {
      setId(entity, ++sequence);
    }
    store.put(entity.getId(), entity);
    return entity;
  }

  public void clear() {
    store.clear();
    sequence = 0L;
  }

  private <S extends ProductCategory> void setId(S entity, long id) {
    try {
      java.lang.reflect.Field field = entity.getClass().getSuperclass()
          .getDeclaredField("id");
      field.setAccessible(true);
      field.set(entity, id);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}