package com.commerce.domain.qna.repository;

import com.commerce.domain.qna.domain.Question;
import com.commerce.utils.BaseReflectionUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class FakeQuestionRepository implements QuestionRepository {

  private final Map<Long, Question> store = new HashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(1L);

  @Override
  public Optional<Question> findById(Long questionId) {
    return Optional.ofNullable(store.get(questionId));
  }

  @Override
  public Page<Question> findByProductId(Long productId, Pageable pageable) {
    List<Question> questions = store.values().stream()
        .filter(question -> question.getProductId().equals(productId))
        .skip(pageable.getOffset())
        .limit(pageable.getPageSize())
        .toList();

    long total = store.values().stream()
        .filter(question -> question.getProductId().equals(productId))
        .count();

    return new PageImpl<>(questions, pageable, total);
  }

  @Override
  public Question save(Question question) {
    if (question.getId() == null) {
      Long newId = idGenerator.getAndIncrement();
      BaseReflectionUtils.setId(question, newId);
    }
    store.put(question.getId(), question);
    return question;
  }

  @Override
  public void delete(Long questionId) {
    store.remove(questionId);
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