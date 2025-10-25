package com.commerce.support.response;

import com.commerce.support.error.ErrorMessage;
import com.commerce.support.error.ErrorType;
import org.hibernate.mapping.Any;

public class ApiResponse<T> {
  private final ResultType result;
  private final T data;
  private final ErrorMessage error;

  private ApiResponse(ResultType result, T data, ErrorMessage error) {
    this.result = result;
    this.data = data;
    this.error = error;
  }

  public static ApiResponse<Any> success() {
    return new ApiResponse<>(ResultType.SUCCESS, null, null);
  }

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(ResultType.SUCCESS, data, null);
  }

  public static <T> ApiResponse<T> error(ErrorType errorType) {
    return new ApiResponse<>(ResultType.ERROR, null, new ErrorMessage(errorType));
  }

  public ResultType getResult() {
    return result;
  }

  public T getData() {
    return data;
  }

  public ErrorMessage getError() {
    return error;
  }
}