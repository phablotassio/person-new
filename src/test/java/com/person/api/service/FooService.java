package com.person.api.service;

import com.person.api.exception.MessageErrorImpl;
import com.person.api.exception.AbstractRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class FooService {

    public void throwException() {

        throw new AbstractRuntimeException(HttpStatus.SERVICE_UNAVAILABLE, MessageErrorImpl.SERVICE_UNAVAILABLE);
    }


}
