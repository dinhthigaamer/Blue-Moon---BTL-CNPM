package com.example.project.common.exception;

import com.example.project.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>> handleApiException(ApiException ex) {
        ErrorCode code = ex.getErrorCode();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (code == ErrorCode.UNAUTHORIZED || code == ErrorCode.AUTH_INVALID_CREDENTIAL) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (code.name().endsWith("NOT_FOUND")) {
            status = HttpStatus.NOT_FOUND;
        }

        ApiResponse<Object> response = ApiResponse.error(ex.getMessage(), code.name());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ApiResponse<Object> response = ApiResponse.error(message, ErrorCode.VALIDATION_ERROR.name());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
        ex.printStackTrace();
        ApiResponse<Object> response =
                ApiResponse.error("Internal server error", ErrorCode.GLOBAL_ERROR.name());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
