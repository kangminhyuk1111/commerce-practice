package com.commerce.domain.review.dto;

import com.commerce.domain.review.entity.Rating;

public record ReviewContent(String title, String description, Rating rating) {
}
