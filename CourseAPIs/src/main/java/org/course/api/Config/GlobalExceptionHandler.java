package org.course.api.Config;

import com.mysql.cj.exceptions.DataTruncationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
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

//        ex.getConstraintViolations().forEach(
//                violation -> {
//                    errors.put(violation.getPropertyPath().toString(), violation.getMessage());
//                }
//
//        );

        ex.getConstraintViolations().forEach(
                violation -> {
                    errors.put(ex.getMessage(), violation.getMessage());
                }

        );

      //  System.out.println(ex.toString());

          return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleEnumViolation(IllegalArgumentException ex) {

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                Map.of("error", "Invalid Course Status" )
        );
    }

    @ExceptionHandler(DataTruncationException.class)
    public ResponseEntity<Map<String, String>> handleEnumViolation(DataTruncationException ex) {

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                Map.of("error", "Invalid Course Status" )
        );
    }

//    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
//    public ResponseEntity<Map<String, String>>  handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
//
//
//        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
//                Map.of("error", "Course with this title already submitted")
//                );
//    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>>  handleDataIntegrityViolationExceptionException(DataIntegrityViolationException ex) {

//        System.out.println(ex.toString());
//        System.out.println(ex.getCause().toString());
        System.out.println(ex.getMostSpecificCause().toString());
//        System.out.println(ex.getLocalizedMessage());

      String errorMessage = ex.getMostSpecificCause().getMessage();

        if (errorMessage.contains("Duplicate entry")) {
            errorMessage = "Course with this title already submitted";
        }else {
            System.out.println("err"+errorMessage);
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                Map.of("error", errorMessage)
        );
    }


}


