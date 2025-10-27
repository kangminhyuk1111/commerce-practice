package com.commerce.domain.qna.application;

import com.commerce.domain.qna.domain.Answer;
import com.commerce.domain.qna.domain.QnA;
import com.commerce.domain.qna.domain.Question;
import com.commerce.domain.qna.repository.AnswerRepository;
import com.commerce.domain.qna.repository.QuestionRepository;
import com.commerce.support.error.CoreException;
import com.commerce.support.error.ErrorType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QnAService {

  private final QuestionRepository questionRepository;
  private final AnswerRepository answerRepository;

  public QnAService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
    this.questionRepository = questionRepository;
    this.answerRepository = answerRepository;
  }

  public Page<QnA> findQnAs(Long productId, Pageable pageable) {
    final Page<Question> questions = questionRepository.findByProductId(productId, pageable);

    final List<Answer> answers = answerRepository.findByQuestionIdIn(questions.stream().map(Question::getId).toList());

    final Map<Long, Answer> answerMap = answers.stream().collect(Collectors.toMap(Answer::getQuestionId, answer -> answer));

    final List<QnA> qnaList = questions.stream().map(question -> new QnA(question, answerMap.get(question.getId()))).toList();

    return new PageImpl<>(qnaList, pageable, questions.getTotalElements());
  }

  public void addQuestion(Long productId, Long userId, String content) {
    final Question question = new Question(productId, userId, content);

    questionRepository.save(question);
  }

  @Transactional
  public void updateQuestion(Long questionId, Long userId, String content) {
    final Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new CoreException(ErrorType.QUESTION_NOT_FOUND));

    validateQuestionWriter(userId, question);

    question.update(content);
  }

  @Transactional
  public void deleteQuestion(Long questionId, Long userId) {
    final Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new CoreException(ErrorType.QUESTION_NOT_FOUND));

    validateQuestionWriter(userId, question);

    questionRepository.delete(question.getId());
  }

  private void validateQuestionWriter(Long userId, Question question) {
    if(!question.getUserId().equals(userId)) {
      throw new CoreException(ErrorType.QUESTION_WRITER_INCORRECT);
    }
  }

  /**
   * NOTE: 답변은 어드민 쪽 기능임
   * - addAnswer(Long adminId, Long questionId, String content)
   * - updateAnswer(Long adminId, Long answerId, String content)
   * - removeAnswer(Long adminId, Long answerId)
   */
}
