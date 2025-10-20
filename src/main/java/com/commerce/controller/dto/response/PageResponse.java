package com.commerce.controller.dto.response;

import java.util.List;
import org.springframework.data.domain.Page;

public record PageResponse<T>(List<T> content, Boolean hasNext) {

  public static <T> PageResponse<T> of(final Page<T> page) {
    return new PageResponse<>(page.getContent(), page.hasNext());
  }
}