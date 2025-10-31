package com.commerce.domain.point.domain;

import com.commerce.support.entity.BaseEntity;
import com.commerce.support.error.CoreException;
import com.commerce.support.error.ErrorType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "points")
public class Point extends BaseEntity {

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false, columnDefinition = "DECIMAL(19,2)")
  private BigDecimal balance;

  public Point() {
  }

  public Point(Long userId, BigDecimal balance) {
    this.userId = userId;
    this.balance = balance;
  }

  public void charge(BigDecimal amount) {
    this.balance = this.balance.add(amount);
  }

  public void use(BigDecimal amount) {
    if (this.balance.compareTo(amount) < 0) {
      throw new CoreException(ErrorType.POINT_BALANCE_LOW);
    }
    this.balance = this.balance.subtract(amount);
  }

  public Long getUserId() {
    return userId;
  }

  public BigDecimal getBalance() {
    return balance;
  }
}
