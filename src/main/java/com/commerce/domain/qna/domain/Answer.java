package com.commerce.domain.qna.domain;

import com.commerce.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "answers")
public class Answer extends BaseEntity {

  @Column(nullable = false)
  private Long questionId;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private String content;

  public Long getQuestionId() {
    return questionId;
  }

  public Long getUserId() {
    return userId;
  }

  public String getContent() {
    return content;
  }
}
