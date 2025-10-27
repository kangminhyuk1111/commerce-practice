package com.commerce.domain.qna.application;

import com.commerce.domain.qna.domain.Question;
import com.commerce.support.error.CoreException;
import com.commerce.support.error.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class QnAPolicyValidator {

  public void validateQuestionWriter(Long userId, Question question) {
    if(!question.getUserId().equals(userId)) {
      throw new CoreException(ErrorType.QUESTION_WRITER_INCORRECT);
    }
  }
}
