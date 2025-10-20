package com.commerce.support.error;

public class ErrorMessage {

  private final String code;
  private final String message;

  public ErrorMessage(final ErrorType errorType) {
    this.code = errorType.getCode().name();
    this.message = errorType.getMessage();
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
