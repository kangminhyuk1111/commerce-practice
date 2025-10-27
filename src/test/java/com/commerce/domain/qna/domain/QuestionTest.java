package com.commerce.domain.qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class QuestionTest {

  @Nested
  class 질문_생성_시 {

    @Test
    void 필수_필드가_올바르게_설정된다() {
      // given
      Long productId = 1L;
      Long userId = 100L;
      String content = "이 상품 사이즈가 어떻게 되나요?";

      // when
      Question question = new Question(productId, userId, content);

      // then
      assertAll(() -> {
        assertThat(question.getProductId()).isEqualTo(productId);
        assertThat(question.getUserId()).isEqualTo(userId);
        assertThat(question.getContent()).isEqualTo(content);
        assertThat(question.getSecret()).isFalse();
        assertThat(question.getAnswered()).isFalse();
      });
    }

    @Nested
    class 질문_수정_시 {

      @Test
      void 내용이_변경된다() {
        // given
        Question question = new Question(1L, 100L, "원래 질문");
        String newContent = "수정된 질문";

        // when
        question.update(newContent);

        // then
        assertThat(question.getContent()).isEqualTo(newContent);
      }
    }

    @Nested
    class 질문_상태_변경_시 {

      @Test
      void 답변_완료_상태로_변경된다() {
        // given
        Question question = new Question(1L, 100L, "질문 내용");

        // when
        question.answered();

        // then
        assertThat(question.getAnswered()).isTrue();
      }

      @Test
      void 비밀글_상태로_변경된다() {
        // given
        Question question = new Question(1L, 100L, "질문 내용");

        // when
        question.makeSecret();

        // then
        assertThat(question.getSecret()).isTrue();
      }

      @Test
      void 공개글_상태로_변경된다() {
        // given
        Question question = new Question(1L, 100L, "질문 내용");

        // when
        question.makePublic();

        // then
        assertThat(question.getSecret()).isFalse();
      }
    }
  }
}