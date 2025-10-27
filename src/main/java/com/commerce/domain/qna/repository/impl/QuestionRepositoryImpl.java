package com.commerce.domain.qna.repository.impl;

import com.commerce.domain.qna.domain.Question;
import com.commerce.domain.qna.repository.QuestionRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

  private final JpaQuestionRepository questionRepository;

  public QuestionRepositoryImpl(JpaQuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  @Override
  public Optional<Question> findById(Long questionId) {
    return questionRepository.findById(questionId);
  }

  @Override
  public Page<Question> findByProductId(Long productId, Pageable pageable) {
    return questionRepository.findByProductId(productId, pageable);
  }

  @Override
  public Question save(Question question) {
    return questionRepository.save(question);
  }

  @Override
  public void delete(Long questionId) {
    questionRepository.deleteById(questionId);
  }
}
