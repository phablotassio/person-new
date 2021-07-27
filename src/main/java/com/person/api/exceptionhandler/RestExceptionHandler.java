package com.person.api.exceptionhandler;

import com.person.api.exception.AbstractRuntimeException;
import com.person.api.exception.MessageErrorImpl;
import com.person.api.util.MessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final HttpServletRequest request;

    @Autowired
    public RestExceptionHandler(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String messageUser = MessagesUtil.getMessage(MessageErrorImpl.MALFORMED_REQUEST_JSON);

        return buildError(HttpStatus.BAD_REQUEST, singletonList(messageUser));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = createErrorList(ex.getBindingResult());
        return buildError(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {

        return buildError(HttpStatus.NOT_FOUND, Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler({AbstractRuntimeException.class})
    public ResponseEntity<Object> handleAbstractRuntimeException(AbstractRuntimeException ex) {

        return buildError(ex.getHttpStatus(), ex.getMessages());
    }

    @ExceptionHandler({ResourceAccessException.class})
    public ResponseEntity<Object> handleResourceAccessException(ResourceAccessException ex) {


        return buildError(HttpStatus.SERVICE_UNAVAILABLE, singletonList(MessagesUtil.getMessage(MessageErrorImpl.SERVICE_UNAVAILABLE)));
    }

    private List<String> createErrorList(BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String message = MessagesUtil.getMessage(fieldError);
            errors.add(message);
        }

        return errors;
    }

    private ResponseEntity<Object> buildError(HttpStatus httpStatus, List<String> messages) {

        ApiError apiError = new ApiError(httpStatus, messages, request.getRequestURI());

        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }


}
