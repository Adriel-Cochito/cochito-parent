package br.edu.infnet.servicos.exceptions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            false,
            HttpStatus.NOT_FOUND.value(),
            "NOT_FOUND",
            ex.getMessage(),
            request.getRequestURI(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleRecursoNaoEncontradoException(RecursoNaoEncontradoException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            false,
            HttpStatus.NOT_FOUND.value(),
            "RESOURCE_NOT_FOUND",
            ex.getMessage(),
            request.getRequestURI(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(RecursoInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleRecursoInvalidoException(RecursoInvalidoException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            false,
            HttpStatus.BAD_REQUEST.value(),
            "INVALID_DATA",
            ex.getMessage(),
            request.getRequestURI(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        
        List<ValidationError> validationErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> new ValidationError(
                error.getField(),
                error.getRejectedValue(),
                error.getDefaultMessage()
            ))
            .collect(Collectors.toList());
        
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
            false,
            HttpStatus.BAD_REQUEST.value(),
            "VALIDATION_ERROR",
            "Dados inválidos fornecidos",
            request.getRequestURI(),
            LocalDateTime.now(),
            validationErrors
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleIntegrityConstraintViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        String mensagem = "Não é possível excluir este recurso pois ele possui dependências. " +
                         "Remova primeiro os registros relacionados antes de tentar excluir novamente.";
        
        ErrorResponse errorResponse = new ErrorResponse(
            false,
            HttpStatus.CONFLICT.value(),
            "INTEGRITY_CONSTRAINT_VIOLATION",
            mensagem,
            request.getRequestURI(),
            LocalDateTime.now()
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(ViolacaoIntegridadeReferencialException.class)
    public ResponseEntity<ErrorResponse> handleViolacaoIntegridadeReferencial(ViolacaoIntegridadeReferencialException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            false,
            HttpStatus.CONFLICT.value(),
            "REFERENTIAL_INTEGRITY_VIOLATION",
            ex.getMessage(),
            request.getRequestURI(),
            LocalDateTime.now()
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }


    public static class ErrorResponse {
        private boolean success;
        private int status;
        private String code;
        private String message;
        private String path;
        private LocalDateTime timestamp;

        public ErrorResponse(boolean success, int status, String code, String message, String path, LocalDateTime timestamp) {
            this.success = success;
            this.status = status;
            this.code = code;
            this.message = message;
            this.path = path;
            this.timestamp = timestamp;
        }

        
        public boolean isSuccess() { return success; }
        public int getStatus() { return status; }
        public String getCode() { return code; }
        public String getMessage() { return message; }
        public String getPath() { return path; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }


    public static class ValidationErrorResponse extends ErrorResponse {
        private List<ValidationError> validationErrors;

        public ValidationErrorResponse(boolean success, int status, String code, String message, String path, LocalDateTime timestamp, List<ValidationError> validationErrors) {
            super(success, status, code, message, path, timestamp);
            this.validationErrors = validationErrors;
        }

        public List<ValidationError> getValidationErrors() { return validationErrors; }
    }


    public static class ValidationError {
        private String field;
        private Object rejectedValue;
        private String message;

        public ValidationError(String field, Object rejectedValue, String message) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.message = message;
        }

        public String getField() { return field; }
        public Object getRejectedValue() { return rejectedValue; }
        public String getMessage() { return message; }
    }
}