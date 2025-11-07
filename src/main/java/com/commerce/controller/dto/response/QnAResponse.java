package com.commerce.controller.dto.response;

import com.commerce.domain.qna.domain.QnA;

public record QnAResponse(
    Long questionId,
    String questionContent,
    Long answerId,
    String answerContent
) {
    public static QnAResponse of(QnA qnA) {
      return new QnAResponse(
          qnA.question().getId(),
          qnA.question().getContent(),
          qnA.answer().getId(),
          qnA.answer().getContent()
      );
    }
}
