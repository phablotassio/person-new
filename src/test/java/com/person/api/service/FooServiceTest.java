package com.person.api.service;

import com.person.api.exception.MessageErrorImpl;
import com.person.api.exception.AbstractRuntimeException;
import com.person.api.util.MessagesUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FooServiceTest {

    private FooService fooService = new FooService();

    @Test
    void insert() {

        AbstractRuntimeException abstractRuntimeException = assertThrows(AbstractRuntimeException.class, () -> fooService.throwException());

        assertEquals(MessagesUtil.getMessage(MessageErrorImpl.SERVICE_UNAVAILABLE), abstractRuntimeException.getMessage());
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, abstractRuntimeException.getHttpStatus());


    }
}