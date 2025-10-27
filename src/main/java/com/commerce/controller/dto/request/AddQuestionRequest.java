package com.commerce.controller.dto.request;

public record AddQuestionRequest(
    Long productId,
    Long userId,
    String content
) {

}
