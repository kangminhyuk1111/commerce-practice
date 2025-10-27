package com.commerce.domain.qna.repository.impl;

import com.commerce.domain.qna.domain.Answer;
import com.commerce.domain.qna.repository.AnswerRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerRepositoryImpl implements AnswerRepository {

  private final JpaAnswerRepository jpaAnswerRepository;

  public AnswerRepositoryImpl(JpaAnswerRepository jpaAnswerRepository) {
    this.jpaAnswerRepository = jpaAnswerRepository;
  }

  @Override
  public List<Answer> findByQuestionIdIn(List<Long> questionIds) {
    return jpaAnswerRepository.findByQuestionIdIn(questionIds);
  }
}
