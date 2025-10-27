package com.commerce.domain.qna.repository.impl;

import com.commerce.domain.qna.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaQuestionRepository extends JpaRepository<Question, Long> {

  Page<Question> findByProductId(Long productId, Pageable pageable);
}
