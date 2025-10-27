package com.commerce.domain.qna.repository;

import com.commerce.domain.qna.domain.Question;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepository {
  Optional<Question> findById(Long questionId);
  Page<Question> findByProductId(Long productId, Pageable pageable);
  Question save(Question question);
  void delete(Long questionId);
}
