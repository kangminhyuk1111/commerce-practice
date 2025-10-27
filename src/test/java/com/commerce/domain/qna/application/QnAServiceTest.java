package com.commerce.domain.qna.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.commerce.domain.qna.domain.Answer;
import com.commerce.domain.qna.domain.QnA;
import com.commerce.domain.qna.domain.Question;
import com.commerce.domain.qna.repository.FakeAnswerRepository;
import com.commerce.domain.qna.repository.FakeQuestionRepository;
import com.commerce.support.error.CoreException;
import com.commerce.support.error.ErrorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

class QnAServiceTest {

  private QnAService qnaService;
  private FakeQuestionRepository questionRepository;
  private FakeAnswerRepository answerRepository;

  @BeforeEach
  void setUp() {
    questionRepository = new FakeQuestionRepository();
    answerRepository = new FakeAnswerRepository();
    QnAPolicyValidator qnAPolicyValidator = new QnAPolicyValidator();
    qnaService = new QnAService(questionRepository, answerRepository, qnAPolicyValidator);
  }

  @Nested
  class QnA_목록_조회_시 {

    @Test
    void 질문과_답변을_함께_조회한다() {
      // given
      Long productId = 1L;
      Question question = questionRepository.save(new Question(productId, 100L, "질문 내용"));
      answerRepository.save(new Answer(question.getId(), 200L, "답변 내용"));

      // when
      Page<QnA> result = qnaService.findQnAs(productId, PageRequest.of(0, 5));

      // then
      assertThat(result.getContent()).hasSize(1);
      QnA qna = result.getContent().get(0);
      assertAll(
          () -> assertThat(qna.question().getContent()).isEqualTo("질문 내용"),
          () -> assertThat(qna.answer().getContent()).isEqualTo("답변 내용")
      );
    }

    @Test
    void 답변이_없는_질문도_조회한다() {
      // given
      Long productId = 1L;
      questionRepository.save(new Question(productId, 100L, "답변 없는 질문"));

      // when
      Page<QnA> result = qnaService.findQnAs(productId, PageRequest.of(0, 5));

      // then
      assertAll(
          () -> assertThat(result.getContent()).hasSize(1),
          () -> assertThat(result.getContent().get(0).answer()).isNull()
      );
    }

    @Test
    void 특정_상품의_질문만_조회한다() {
      // given
      questionRepository.save(new Question(1L, 100L, "상품1 질문"));
      questionRepository.save(new Question(2L, 100L, "상품2 질문"));

      // when
      Page<QnA> result = qnaService.findQnAs(1L, PageRequest.of(0, 5));

      // then
      assertAll(
          () -> assertThat(result.getContent()).hasSize(1),
          () -> assertThat(result.getContent().get(0).question().getProductId()).isEqualTo(1L)
      );
    }
  }

  @Nested
  class 질문_생성_시 {

    @Test
    void 질문이_저장된다() {
      // given
      Long productId = 1L;
      Long userId = 100L;
      String content = "새로운 질문";

      // when
      qnaService.addQuestion(productId, userId, content);

      // then
      Question saved = questionRepository.findByProductId(productId, PageRequest.of(0, 5))
          .getContent().get(0);
      assertAll(
          () -> assertThat(questionRepository.size()).isEqualTo(1),
          () -> assertThat(saved.getProductId()).isEqualTo(productId),
          () -> assertThat(saved.getUserId()).isEqualTo(userId),
          () -> assertThat(saved.getContent()).isEqualTo(content)
      );
    }
  }

  @Nested
  class 질문_수정_시 {

    @Test
    void 작성자는_질문을_수정할_수_있다() {
      // given
      Long userId = 100L;
      Question question = questionRepository.save(new Question(1L, userId, "원래 질문"));
      String newContent = "수정된 질문";

      // when
      qnaService.updateQuestion(question.getId(), userId, newContent);

      // then
      Question updated = questionRepository.findById(question.getId()).get();
      assertThat(updated.getContent()).isEqualTo(newContent);
    }

    @Test
    void 작성자가_아니면_수정할_수_없다() {
      // given
      Question question = questionRepository.save(new Question(1L, 100L, "원래 질문"));
      Long otherUserId = 999L;

      // when & then
      assertThatThrownBy(() -> qnaService.updateQuestion(question.getId(), otherUserId, "수정 시도"))
          .isInstanceOf(CoreException.class)
          .hasMessage(ErrorType.QUESTION_WRITER_INCORRECT.getMessage());
    }

    @Test
    void 존재하지_않는_질문은_수정할_수_없다() {
      // given
      Long nonExistentId = 999L;

      // when & then
      assertThatThrownBy(() -> qnaService.updateQuestion(nonExistentId, 100L, "수정 시도"))
          .isInstanceOf(CoreException.class)
          .hasMessage(ErrorType.QUESTION_NOT_FOUND.getMessage());
    }
  }

  @Nested
  class 질문_삭제_시 {

    @Test
    void 작성자는_질문을_삭제할_수_있다() {
      // given
      Long userId = 100L;
      Question question = questionRepository.save(new Question(1L, userId, "질문 내용"));

      // when
      qnaService.deleteQuestion(question.getId(), userId);

      // then
      assertThat(questionRepository.findById(question.getId())).isEmpty();
    }

    @Test
    void 작성자가_아니면_삭제할_수_없다() {
      // given
      Question question = questionRepository.save(new Question(1L, 100L, "질문 내용"));
      Long otherUserId = 999L;

      // when & then
      assertThatThrownBy(() -> qnaService.deleteQuestion(question.getId(), otherUserId))
          .isInstanceOf(CoreException.class)
          .hasMessage(ErrorType.QUESTION_WRITER_INCORRECT.getMessage());
    }

    @Test
    void 존재하지_않는_질문은_삭제할_수_없다() {
      // given
      Long nonExistentId = 999L;

      // when & then
      assertThatThrownBy(() -> qnaService.deleteQuestion(nonExistentId, 100L))
          .isInstanceOf(CoreException.class)
          .hasMessage(ErrorType.QUESTION_NOT_FOUND.getMessage());
    }
  }
}