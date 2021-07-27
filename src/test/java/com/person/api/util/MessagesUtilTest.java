package com.person.api.util;

import com.person.api.exception.MessageErrorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.validation.FieldError;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessagesUtilTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void getMessageInPortuguese() {

        Locale.setDefault(new Locale("pt", "BR"));

        assertEquals("Json de request mal formatado!", MessagesUtil.getMessage(MessageErrorImpl.MALFORMED_REQUEST_JSON));

    }

    @Test
    void getMessageInEnglish() {

        Locale.setDefault(new Locale("en", "US"));

        assertEquals("Malformed request json!", MessagesUtil.getMessage(MessageErrorImpl.MALFORMED_REQUEST_JSON));

    }


    @Test
    void getMessageInSpanish() {

        Locale.setDefault(new Locale("es", "ES"));


        assertEquals("¡Solicitud malformada json!", MessagesUtil.getMessage(MessageErrorImpl.MALFORMED_REQUEST_JSON));

    }

    @Test
    void getMessageWithResolvable() {

        Locale.setDefault(new Locale("es", "ES"));

        FieldError fieldError = new FieldError("PersonRequestDTO", "name", MessagesUtil.getMessage("javax.validation.constraints.NotBlank.message", "name"));

        assertEquals("name no puede ser vazio", MessagesUtil.getMessage(fieldError));

    }


    @Test
    void testGetMessageInDefaultLocale() {

        assertEquals("Testando Locale", MessagesUtil.getMessage("message.test"));

    }

}