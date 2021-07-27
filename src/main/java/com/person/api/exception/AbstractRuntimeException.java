package com.person.api.exception;

import com.person.api.util.MessagesUtil;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

public class AbstractRuntimeException extends RuntimeException {

    private HttpStatus httpStatus;
    private List<String> messages;

    public AbstractRuntimeException(List<String> messages, HttpStatus httpStatus) {
        super(messages.stream().map(String::valueOf).collect(Collectors.joining(", ")));
        this.httpStatus = httpStatus;
        this.messages = messages;
    }

    public AbstractRuntimeException(HttpStatus httpStatus, MessageError messageError, String... args) {
        super(MessagesUtil.getMessage(messageError, args));
        this.httpStatus = httpStatus;
        this.messages = singletonList(getMessage());
    }

    public AbstractRuntimeException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public List<String> getMessages() {
        return messages;
    }
}
