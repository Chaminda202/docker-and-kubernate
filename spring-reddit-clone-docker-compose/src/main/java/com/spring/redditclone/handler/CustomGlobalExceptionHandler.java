package com.spring.redditclone.handler;

import com.spring.redditclone.exception.RecordNotExistException;
import com.spring.redditclone.exception.SpringRedditException;
import com.spring.redditclone.exception.TokenExpiredException;
import com.spring.redditclone.model.CustomErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        //Get all field errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        CustomErrorDTO errorResponse = CustomErrorDTO.builder()
                .details(errors)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SpringRedditException.class)
    public ResponseEntity<Object> handleSpringRedditException(Exception ex, WebRequest request) {
        CustomErrorDTO errorResponse = CustomErrorDTO.builder()
                .details(Arrays.asList(ex.getMessage()))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordNotExistException.class)
    public ResponseEntity<Object> handleRecordNotExistException(Exception ex, WebRequest request) {
        CustomErrorDTO errorResponse = CustomErrorDTO.builder()
                .details(Arrays.asList(ex.getMessage()))
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<CustomErrorDTO> handleTokenExpiredException(Exception ex, HttpServletRequest request) {
        CustomErrorDTO errorResponse = CustomErrorDTO.builder()
                .details(Arrays.asList(ex.getMessage()))
                .statusCode(HttpStatus.FORBIDDEN.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    // should be updated
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(
            MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, null, headers, status, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> methodArgumentTypeMismatchException(Exception ex, WebRequest request) {
        CustomErrorDTO errorResponse = CustomErrorDTO.builder()
                .details(Arrays.asList(ex.getMessage()))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle unprocessable json data exception
     * Handel undefine attributes in json payload
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomErrorDTO errorResponse = CustomErrorDTO.builder()
                .details(Arrays.asList(ex.getMessage()))
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(Exception ex, WebRequest request) {

        CustomErrorDTO responseDTO = CustomErrorDTO.builder()
                .details(Arrays.asList(ex.getMessage().split(":")[1].trim()))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.badRequest().body(responseDTO);
    }
}