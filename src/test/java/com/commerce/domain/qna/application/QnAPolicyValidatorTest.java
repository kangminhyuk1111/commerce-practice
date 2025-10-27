package com.commerce.domain.qna.application;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.commerce.domain.qna.domain.Question;
import com.commerce.support.error.CoreException;
import com.commerce.support.error.ErrorType;
import org.junit.jupiter.api.Test;

class QnAPolicyValidatorTest {

  private final QnAPolicyValidator validator = new QnAPolicyValidator();

  @Test
  void 작성자가_일치하면_검증을_통과한다() {
    // given
    Long userId = 100L;
    Question question = new Question(1L, userId, "질문 내용");

    // when & then
    assertThatCode(() -> validator.validateQuestionWriter(userId, question))
        .doesNotThrowAnyException();
  }

  @Test
  void 작성자가_일치하지_않으면_예외가_발생한다() {
    // given
    Question question = new Question(1L, 100L, "질문 내용");
    Long otherUserId = 999L;

    // when & then
    assertThatThrownBy(() -> validator.validateQuestionWriter(otherUserId, question))
        .isInstanceOf(CoreException.class)
        .hasMessage(ErrorType.QUESTION_WRITER_INCORRECT.getMessage());
  }
}