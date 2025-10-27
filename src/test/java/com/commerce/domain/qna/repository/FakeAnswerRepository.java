package com.commerce.domain.qna.repository;

import com.commerce.domain.qna.domain.Answer;
import com.commerce.utils.BaseReflectionUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class FakeAnswerRepository implements AnswerRepository {

  private final Map<Long, Answer> store = new HashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(1L);

  @Override
  public List<Answer> findByQuestionIdIn(List<Long> questionIds) {
    return store.values().stream()
        .filter(answer -> questionIds.contains(answer.getQuestionId()))
        .toList();
  }

  public Answer save(Answer answer) {
    if (answer.getId() == null) {
      Long newId = idGenerator.getAndIncrement();
      BaseReflectionUtils.setId(answer, newId);
    }
    store.put(answer.getId(), answer);
    return answer;
  }

  // 테스트 헬퍼 메서드
  public void clear() {
    store.clear();
    idGenerator.set(1L);
  }

  public int size() {
    return store.size();
  }
}