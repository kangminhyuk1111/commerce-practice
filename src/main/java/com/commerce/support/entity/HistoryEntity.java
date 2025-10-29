package com.commerce.support.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@MappedSuperclass
public class HistoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreationTimestamp
  private LocalDateTime transactionAt;;

  public Long getId() {
    return id;
  }

  public LocalDateTime getTransactionAt() {
    return transactionAt;
  }
}
