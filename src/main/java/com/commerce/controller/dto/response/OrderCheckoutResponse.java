package com.commerce.controller.dto.response;

import com.commerce.domain.order.domain.OrderRecord;
import com.commerce.domain.point.domain.Point;

public record OrderCheckoutResponse() {
  public static OrderCheckoutResponse of(OrderRecord orderRecord, Point point) {
    return new OrderCheckoutResponse();
  }
}
