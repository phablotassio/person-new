package com.person.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    @JsonIgnore
    private HttpStatus httpStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime timestamp;
    private Integer status;
    private String error;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> messages = new ArrayList<>();
    private String path;

    public ApiError() {
    }

    public ApiError(HttpStatus httpStatus, List<String> messages, String path) {
        this.httpStatus = httpStatus;
        this.messages = messages;
        this.status = httpStatus.value();
        this.timestamp = ZonedDateTime.now();
        this.error = httpStatus.getReasonPhrase();
        this.path = path;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getPath() {
        return path;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
