package com.commerce.domain.point.application;

import com.commerce.domain.point.domain.Point;
import com.commerce.support.error.CoreException;
import com.commerce.support.error.ErrorType;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class PointPolicyValidator {

  private static final BigDecimal MINIMUM_POINT = BigDecimal.valueOf(1000L);

  public void isUsable(Point point) {
    if (point.getBalance().compareTo(MINIMUM_POINT) < 0) {
      throw new CoreException(ErrorType.POINT_BELOW_MINIMUM_THRESHOLD);
    }
  }
}
