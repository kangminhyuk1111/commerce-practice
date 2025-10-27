package com.commerce.domain.qna.repository.impl;

import com.commerce.domain.qna.domain.Answer;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAnswerRepository extends JpaRepository<Answer, Long> {

  List<Answer> findByQuestionIdIn(Collection<Long> questionIds);
}
