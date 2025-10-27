package com.commerce.domain.qna.repository;

import com.commerce.domain.qna.domain.Answer;
import java.util.List;

public interface AnswerRepository {

  List<Answer> findByQuestionIdIn(List<Long> questionIds);
}
