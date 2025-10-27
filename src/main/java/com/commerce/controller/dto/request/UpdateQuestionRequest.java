package com.commerce.controller.dto.request;

public record UpdateQuestionRequest(
    Long userId,
    String content
) {

}
