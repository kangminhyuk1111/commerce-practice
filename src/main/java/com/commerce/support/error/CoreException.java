package com.commerce.support.error;

public class CoreException extends RuntimeException {

  public CoreException(final ErrorType errorType) {
    super(errorType.getMessage());
  }
}
