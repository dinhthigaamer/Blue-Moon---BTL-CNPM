package com.example.project.common.exception;

import com.example.project.common.response.ApiResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>> handleApiException(ApiException ex) {
        ErrorCode code = ex.getErrorCode();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (code == ErrorCode.UNAUTHORIZED || code == ErrorCode.AUTH_INVALID_CREDENTIAL) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (code.name().endsWith("_NOT_FOUND")) {
            status = HttpStatus.NOT_FOUND;
        }

        ApiResponse<Object> response = ApiResponse.error(ex.getMessage(), code.name());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        // Lấy tất cả các lỗi vi phạm
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        // Trả về ApiResponse với data là Map chứa danh sách các trường bị lỗi
        return ResponseEntity
                .badRequest()
                .body(new ApiResponse<>(
                        false,
                        errors,
                        "Dữ liệu không hợp lệ",
                        ErrorCode.VALIDATION_ERROR.name()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error("Dữ liệu JSON có trường không hợp lệ hoặc sai kiểu dữ liệu.",
                        ErrorCode.INVALID_JSON_FORMAT.name()));
    }

    // Bắt lỗi sai kiểu param (Long, Integer, Enum, Date,...)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName(); // tên param
        String expectedType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown";
        Object value = ex.getValue(); // giá trị nhận được
        String message = String.format(
                "Parameter '%s' expects type '%s' but received value '%s'",
                paramName, expectedType, value);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(message,
                ErrorCode.PARAM_TYPE_MISMATCH.name()));
    }

    // --- Bean validation (JSR-303) ---
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleBeanValidation(ConstraintViolationException ex) {
        String combinedMessage = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error("Vi phạm ràng buộc dữ liệu: " + combinedMessage,
                        ErrorCode.CONSTRAINT_VIOLATION.name()));
    }

    // --- Database constraint violations (Spring) ---
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = Optional.ofNullable(ex.getMostSpecificCause())
                .map(Throwable::getMessage)
                .orElse(ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error("Vi phạm ràng buộc dữ liệu: " + message,
                        ErrorCode.CONSTRAINT_VIOLATION.name()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
        ex.printStackTrace();
        ApiResponse<Object> response = ApiResponse.error("Internal server error", ErrorCode.GLOBAL_ERROR.name());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
