package com.commerce.support.error;

import org.springframework.http.HttpStatus;

public enum ErrorType {
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500, "알 수 없는 오류가 발생 했습니다. 나중에 다시 시도해주세요."),

  PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, ErrorCode.E404, "상품을 찾을 수 없습니다."),

  REVIEW_ALREADY_EXIST(HttpStatus.BAD_REQUEST, ErrorCode.REVIEW_001, "리뷰를 이미 작성하셨습니다."),
  REVIEW_WRITE_DATE_EXPIRED(HttpStatus.BAD_REQUEST, ErrorCode.REVIEW_002, "리뷰를 작성 가능한 기간이 넘었습니다. 리뷰를 작성하실 수 없습니다."),
  ORDER_NOT_PURCHASED(HttpStatus.BAD_REQUEST, ErrorCode.REVIEW_003, "구매하지 않은 상품에 리뷰를 작성할 수 없습니다"),

  ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, ErrorCode.ORDER_001, "존재하지 않는 주문 입니다.");

  private final HttpStatus status;
  private final ErrorCode code;
  private final String message;

  ErrorType(final HttpStatus status, final ErrorCode code, final String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public ErrorCode getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
