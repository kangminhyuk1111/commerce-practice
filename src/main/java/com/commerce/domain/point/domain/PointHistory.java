package com.commerce.domain.point.domain;

import com.commerce.support.entity.HistoryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "point_history")
public class PointHistory extends HistoryEntity {

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false, columnDefinition = "DECIMAL(19,2)")
  private BigDecimal pointAmount;

  @Enumerated(EnumType.STRING)
  private PointTransactionType transactionType;

  @Column(columnDefinition = "DECIMAL(19,2)")
  private BigDecimal balanceAfter;

  public PointHistory() {
  }

  public PointHistory(Long userId, BigDecimal pointAmount, PointTransactionType transactionType, BigDecimal balanceAfter) {
    this.userId = userId;
    this.pointAmount = pointAmount;
    this.transactionType = transactionType;
    this.balanceAfter = balanceAfter;
  }

  public Long getUserId() {
    return userId;
  }

  public BigDecimal getPointAmount() {
    return pointAmount;
  }

  public PointTransactionType getTransactionType() {
    return transactionType;
  }

  public BigDecimal getBalanceAfter() {
    return balanceAfter;
  }
}
