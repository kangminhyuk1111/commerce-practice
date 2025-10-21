package com.commerce.controller.dto.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public record PageSize(Integer page, Integer size, String sort) {

  public static PageSize of(Integer page, Integer size) {
    return new PageSize(page, size, "createdAt,desc");
  }

  public Pageable toPageable(String sort) {
    if (sort == null || sort.isBlank()) {
      return PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));
    }

    final String[] sortParts = sort.split(",");
    final String property = sortParts[0];
    final Direction direction = Direction.fromString(sortParts[1].toUpperCase());

    return PageRequest.of(page, size, Sort.by(direction, property));
  }
}
