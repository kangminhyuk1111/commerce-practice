package com.commerce.domain.favorite.application;

import com.commerce.controller.dto.request.ApplyFavoriteRequest;
import com.commerce.controller.dto.request.type.ApplyFavoriteType;
import com.commerce.domain.favorite.domain.Favorite;
import com.commerce.domain.favorite.repository.FavoriteRepository;
import com.commerce.support.error.CoreException;
import com.commerce.support.error.ErrorType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteService {

  private final FavoriteRepository favoriteRepository;

  public FavoriteService(FavoriteRepository favoriteRepository) {
    this.favoriteRepository = favoriteRepository;
  }

  public Page<Favorite> findFavorites(Long userId, Pageable pageable) {
    return favoriteRepository.findByUserId(userId, pageable);
  }

  @Transactional
  public void applyFavorite(Long userId, Long productId, ApplyFavoriteType type) {
    switch (type) {
      case FAVORITE -> addFavorite(userId, productId);
      case UNFAVORITE -> removeFavorite(userId, productId);
      default -> throw new CoreException(ErrorType.INTERNAL_SERVER_ERROR);
    }
  }

  private void addFavorite(Long userId, Long productId) {
    final Optional<Favorite> found = favoriteRepository.findByUserIdAndProductId(userId, productId);

    final Favorite favorite = new Favorite(userId, productId);

    if (found.isEmpty()) {
      favoriteRepository.save(favorite);
    }
  }

  private void removeFavorite(Long userId, Long productId) {
    final Optional<Favorite> found = favoriteRepository.findByUserIdAndProductId(userId, productId);

    if (found.isEmpty()) {
      throw new CoreException(ErrorType.QUESTION_NOT_FOUND);
    }

    favoriteRepository.deleteByUserIdAndProductId(userId, productId);
  }
}
