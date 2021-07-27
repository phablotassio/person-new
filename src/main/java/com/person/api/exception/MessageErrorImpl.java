package com.person.api.exception;

public enum MessageErrorImpl implements MessageError{

    MALFORMED_REQUEST_JSON(1),
    JSON_TO_OBJECT_FAIL(2),
    OBJECT_TO_JSON_FAIL(3),
    SERVICE_UNAVAILABLE(4),
    INVALID_DOCUMENT_NUMBER(5),
    PERSON(6),
    RESOURCE_NOT_FOUND(7),
    RESOURCE_ALREADY_REGISTERED(8),
    EMAIL(9),
    DOCUMENT_NUMBER(10),
    PHONE(11),
    DATE_MUST_BE_PAST(12);

    private Integer code;

    MessageErrorImpl(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
