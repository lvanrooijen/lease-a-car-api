package com.lvr.lease_a_car.exception;

import jakarta.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ExceptionHandler(EntityNotFoundException.class)
  public ProblemDetail handleEntityNotFoundException(Exception e) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
  }

  /** Fall back exception handler, handles all the uncaught exceptions */
  @ExceptionHandler(Exception.class)
  public ProblemDetail handleException(Exception e) {
    log.error("[FALLBACK] " + e.getMessage(), e);
    return ProblemDetail.forStatusAndDetail(
        HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleFailedValidation(MethodArgumentNotValidException e) {
    Map<String, String> errors =
        e.getBindingResult().getFieldErrors().stream()
            .collect(
                Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage,
                    (existing, replacement) -> existing));
    String message = "Invalid Request Body";
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
    problemDetail.setProperty("errors", errors);
    return problemDetail;
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ProblemDetail handleUsernameNotFoundException(Exception e) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(UserAlreadyRegisteredException.class)
  public ProblemDetail handleUserAlreadyRegisteredException(Exception e) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(ExistingCarException.class)
  public ProblemDetail handleExistingCarException(Exception e) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(InvalidUserRoleException.class)
  public ProblemDetail handleInvalidUserRoleException(Exception e) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ProblemDetail handleAccessDeniedException(Exception e) {
    log.warn("[AccessDeniedException] " + e.getMessage(), e);
    return ProblemDetail.forStatusAndDetail(
        HttpStatus.FORBIDDEN, "You dont have permission to access this resource");
  }

  @ExceptionHandler(CustomerNotFoundException.class)
  public ProblemDetail handleCustomerNotFoundException(Exception e) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ProblemDetail handleHttpMessageNotReadableException(Exception e) {
    log.warn("[HttpMessageNotReadableException] " + e.getMessage(), e);
    return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "invalid request body");
  }
}
