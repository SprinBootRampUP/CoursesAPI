package org.course.api.Config;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(
                violation -> {
                    errors.put(violation.getPropertyPath().toString(), violation.getMessage());
                }

        );
          return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String, String>>  handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {


        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                Map.of("error", "Course with this title already submitted")
                );
    }


}
