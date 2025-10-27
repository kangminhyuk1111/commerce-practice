package com.commerce.controller.v1;

import com.commerce.controller.dto.DeleteQuestionRequest;
import com.commerce.controller.dto.QnAResponse;
import com.commerce.controller.dto.request.AddQuestionRequest;
import com.commerce.controller.dto.request.PageSize;
import com.commerce.controller.dto.request.UpdateQuestionRequest;
import com.commerce.controller.dto.response.PageResponse;
import com.commerce.domain.qna.application.QnAService;
import com.commerce.domain.qna.domain.QnA;
import com.commerce.support.response.ApiResponse;
import org.hibernate.mapping.Any;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QnAController {

  private final QnAService qnaService;

  public QnAController(QnAService qnaService) {
    this.qnaService = qnaService;
  }

  @GetMapping("/v1/qnas")
  public ApiResponse<PageResponse<QnAResponse>> QnAs(
      @RequestParam Long productId,
      @RequestParam(defaultValue = "0") Integer offset,
      @RequestParam(defaultValue = "5") Integer limit,
      @RequestParam(defaultValue = "createdAt,desc") String sort
  ) {
    final Page<QnA> qnas = qnaService.findQnAs(productId,
        PageSize.of(offset, limit).toPageable(sort));
    final Page<QnAResponse> responses = qnas.map(QnAResponse::of);
    return ApiResponse.success(PageResponse.of(responses));
  }

  @PostMapping("/v1/questions")
  public ApiResponse<Any> createQuestion(
      @RequestBody AddQuestionRequest request
  ) {
    qnaService.addQuestion(request.productId(), request.userId(), request.content());
    return ApiResponse.success();
  }

  @PutMapping("/v1/questions/{questionId}")
  public ApiResponse<Any> updateQuestion(
      @RequestBody UpdateQuestionRequest request,
      @PathVariable Long questionId
  ) {
    qnaService.updateQuestion(questionId, request.userId(), request.content());
    return ApiResponse.success();
  }

  @DeleteMapping("/v1/questions/{questionId}")
  public ApiResponse<Any> deleteQuestion(
      @PathVariable Long questionId,
      @RequestBody DeleteQuestionRequest request
  ) {
    qnaService.deleteQuestion(questionId, request.userId());
    return ApiResponse.success();
  }
}
