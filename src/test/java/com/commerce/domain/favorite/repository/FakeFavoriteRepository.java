package com.commerce.domain.favorite.repository;

import com.commerce.domain.favorite.domain.Favorite;
import com.commerce.utils.BaseReflectionUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class FakeFavoriteRepository implements FavoriteRepository {

  private final List<Favorite> store = new ArrayList<>();
  private Long idSequence = 1L;

  @Override
  public Page<Favorite> findByUserId(Long userId, Pageable pageable) {
    List<Favorite> favorites = store.stream()
        .filter(f -> f.getUserId().equals(userId))
        .collect(Collectors.toList());

    int start = (int) pageable.getOffset();
    int end = Math.min(start + pageable.getPageSize(), favorites.size());

    List<Favorite> pageContent = favorites.subList(start, end);
    return new PageImpl<>(pageContent, pageable, favorites.size());
  }

  @Override
  public Favorite save(Favorite favorite) {
    Optional<Favorite> existing = store.stream()
        .filter(f -> f.getId() != null && f.getId().equals(favorite.getId()))
        .findFirst();

    if (existing.isPresent()) {
      store.remove(existing.get());
      store.add(favorite);
      return favorite;
    }

    BaseReflectionUtils.setId(favorite, idSequence++);
    BaseReflectionUtils.setCreatedAt(favorite, LocalDateTime.now());
    BaseReflectionUtils.setUpdatedAt(favorite, LocalDateTime.now());
    store.add(favorite);
    return favorite;
  }

  @Override
  public Optional<Favorite> findByUserIdAndProductId(Long userId, Long productId) {
    return store.stream()
        .filter(f -> f.getUserId().equals(userId) && f.getProductId().equals(productId))
        .findFirst();
  }

  @Override
  public void deleteByUserIdAndProductId(Long userId, Long productId) {
    store.removeIf(f -> f.getUserId().equals(userId) && f.getProductId().equals(productId));
  }
}