package com.commerce.domain.qna.domain;

import com.commerce.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "questions")
public class Question extends BaseEntity {

  @Column(nullable = false)
  private Long productId;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private Boolean isSecret = false;

  @Column(nullable = false)
  private Boolean isAnswered = false;

  public Question() {
  }

  public Question(Long productId, Long userId, String content) {
    this.productId = productId;
    this.userId = userId;
    this.content = content;
  }

  public void update(String content) {
    this.content = content;
  }

  public void answered() {
    this.isAnswered = true;
  }

  public void makeSecret() {
    this.isSecret = true;
  }

  public void makePublic() {
    this.isSecret = false;
  }

  public Long getProductId() {
    return productId;
  }

  public Long getUserId() {
    return userId;
  }

  public String getContent() {
    return content;
  }

  public Boolean getSecret() {
    return isSecret;
  }

  public Boolean getAnswered() {
    return isAnswered;
  }
}
