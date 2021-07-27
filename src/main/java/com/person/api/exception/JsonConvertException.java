package com.person.api.exception;

import com.person.api.util.MessagesUtil;

public class JsonConvertException extends RuntimeException {

    public JsonConvertException(MessageError messageError) {
        super(MessagesUtil.getMessage(messageError));
    }
}
