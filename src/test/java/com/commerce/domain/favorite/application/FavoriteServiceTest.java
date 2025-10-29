package com.commerce.domain.favorite.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.commerce.controller.dto.request.PageSize;
import com.commerce.controller.dto.request.type.ApplyFavoriteType;
import com.commerce.domain.favorite.domain.Favorite;
import com.commerce.domain.favorite.repository.FakeFavoriteRepository;
import com.commerce.domain.favorite.repository.FavoriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

class FavoriteServiceTest {

  private FavoriteService favoriteService;
  private FavoriteRepository favoriteRepository;

  @BeforeEach
  void setUp() {
    favoriteRepository = new FakeFavoriteRepository();
    favoriteService = new FavoriteService(favoriteRepository);

    favoriteRepository.save(new Favorite(1L, 1L));
    favoriteRepository.save(new Favorite(1L, 2L));
    favoriteRepository.save(new Favorite(1L, 3L));
    favoriteRepository.save(new Favorite(1L, 4L));
    favoriteRepository.save(new Favorite(2L, 1L));
    favoriteRepository.save(new Favorite(2L, 2L));
    favoriteRepository.save(new Favorite(2L, 3L));
    favoriteRepository.save(new Favorite(2L, 4L));
  }

  @Nested
  class 찜한_상품_조회시 {

    @Test
    void 유저ID를_기준으로_조회한다() {
      // given & when
      Page<Favorite> favorites = favoriteService.findFavorites(1L, PageSize.of(0, 5).toPageable());

      // then
      assertAll(() -> {
        assertThat(favorites.getContent().size()).isEqualTo(4);
        assertThat(favorites.hasNext()).isFalse();
      });
    }

    @Test
    void 존재하지_않는_유저ID를_조회한다() {
      // given & when
      Page<Favorite> favorites = favoriteService.findFavorites(3L, PageSize.of(0, 5).toPageable());

      // then
      assertAll(() -> {
        assertThat(favorites.getContent().size()).isEqualTo(0);
        assertThat(favorites.hasNext()).isFalse();
      });
    }
  }

  @Nested
  class 상품을_찜할시 {

    @Test
    void 찜상태가_아니라면_찜하기가_된다() {
      // given
      Long productId = 5L;

      // when
      favoriteService.applyFavorite(1L, productId, ApplyFavoriteType.FAVORITE);

      // then
      Page<Favorite> favorites = favoriteService.findFavorites(1L, PageSize.of(0, 5).toPageable());
      assertThat(favorites.getContent().size()).isEqualTo(5);
    }

    @Test
    void 찜이_되어있는_상태라면_찜하기가_취소된다() {
      // given
      Long productId = 5L;
      favoriteService.applyFavorite(1L, productId, ApplyFavoriteType.FAVORITE);

      // when
      favoriteService.applyFavorite(1L, productId, ApplyFavoriteType.UNFAVORITE);

      // then
      Page<Favorite> favorites = favoriteService.findFavorites(1L, PageSize.of(0, 5).toPageable());
      assertThat(favorites.getContent().size()).isEqualTo(4);
    }
  }
}