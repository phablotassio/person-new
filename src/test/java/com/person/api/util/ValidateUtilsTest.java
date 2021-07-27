package com.person.api.util;


import com.person.api.configuration.MessageSourceConfiguration;
import com.person.api.dto.FooRequestDTO;
import com.person.api.exception.AbstractRuntimeException;
import com.person.api.exception.MessageErrorImpl;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {MessageSourceConfiguration.class, ValidateUtils.class})
class ValidateUtilsTest {

    @BeforeEach
    void setup() throws IllegalAccessException {
        FieldUtils.writeStaticField(ValidateUtils.class, "FIELD_NAME", "interpolatedMessage", true);
    }

    @Test
    void validateObjectWithInvalidName() {

        FooRequestDTO fooRequestDTO = ObjectMapperUtil.toObject("{\n" +
                "  \"name\": \"\",\n" +
                "  \"mothersName\": \"Maria da Silva\",\n" +
                "  \"documentNumber\": \"063.677.777-99\",\n" +
                "  \"email\": \"person123@cabal.com.br\",\n" +
                "  \"birthDate\": \"1996-04-23\",\n" +
                "  \"paymentAccountType\": \"JURIDICAL\",\n" +
                "  \"phone\": {\n" +
                "    \"number\": \"9 9999-8888\",\n" +
                "    \"areaCode\": \"61\"\n" +
                "  },\n" +
                "  \"address\": {\n" +
                "    \"street\": \"SCS Quadra 06 Bloco A\",\n" +
                "    \"neighborhood\": \"Setor Comercial Sul\",\n" +
                "    \"complement\": \"50 1o andar\",\n" +
                "    \"number\": \"N/A\",\n" +
                "    \"zipCode\": \"70.000-000\",\n" +
                "    \"uf\": \"DF\",\n" +
                "    \"city\": \"Brasília\"\n" +
                "  },\n" +
                "  \"automaticRelease\": \"false\",\n" +
                "  \"callbackUrl\": \"https://api.cabal.com.br/v1/paymentaccount/abc\"\n" +
                "}", FooRequestDTO.class);


        List<String> errors = ValidateUtils.validateObject(fooRequestDTO);
        assertFalse(errors.isEmpty());
        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("name"));


    }

    @Test
    void getErrorWithInvalidField() throws IllegalAccessException {

        FieldUtils.writeStaticField(ValidateUtils.class, "FIELD_NAME", "foo", true);
        FooRequestDTO fooRequestDTO = ObjectMapperUtil.toObject("{\n" +
                "  \"fullName\": \"João da Silva\",\n" +
                "  \"mothersName\": \"Maria da Silva\",\n" +
                "  \"documentNumber\": \"063.677.777-99\",\n" +
                "  \"email\": \"person123@cabal.com.br\",\n" +
                "  \"birthDate\": \"1996-04-23\",\n" +
                "  \"paymentAccountType\": \"JURIDICAL\",\n" +
                "  \"phone\": {\n" +
                "    \"number\": \"9 9999-8888\",\n" +
                "    \"areaCode\": \"61\"\n" +
                "  },\n" +
                "  \"address\": {\n" +
                "    \"street\": \"SCS Quadra 06 Bloco A\",\n" +
                "    \"neighborhood\": \"Setor Comercial Sul\",\n" +
                "    \"complement\": \"50 1o andar\",\n" +
                "    \"number\": \"N/A\",\n" +
                "    \"zipCode\": \"70.000-000\",\n" +
                "    \"uf\": \"DF\",\n" +
                "    \"city\": \"Brasília\"\n" +
                "  },\n" +
                "  \"automaticRelease\": \"false\",\n" +
                "  \"callbackUrl\": \"https://api.cabal.com.br/v1/paymentaccount/abc\"\n" +
                "}", FooRequestDTO.class);


        AbstractRuntimeException abstractRuntimeException = assertThrows(AbstractRuntimeException.class, () -> ValidateUtils.validateObject(fooRequestDTO));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, abstractRuntimeException.getHttpStatus());
    }

    @Test
    void validateMinAge() {

        final Date date = new Date(java.sql.Date.valueOf(LocalDate.now().minusYears(23)).getTime());

        assertTrue(ValidateUtils.personHaveMinAge(date));
    }

    @Test
    void validateMinAgeWith18Years() {

        final Date date = new Date(java.sql.Date.valueOf(LocalDate.now().minusYears(18)).getTime());

        assertTrue(ValidateUtils.personHaveMinAge(date));
    }

    @Test
    void validateMinAgeWithInvalidAge() {

        final Date date = new Date(java.sql.Date.valueOf(LocalDate.now().minusYears(16)).getTime());

        assertFalse(ValidateUtils.personHaveMinAge(date));
    }

    @Test
    void validNaturalDocumentNumberIfExists() {

        Map<String, Object> fields = new HashMap<>();

        fields.put("documentNumber", "991.241.470-52");

        ValidateUtils.validateDocumentNumberIfExists(fields);

    }

    @Test
    void validJuridicalDocumentNumberIfExists() {

        Map<String, Object> fields = new HashMap<>();

        fields.put("documentNumber", "73.445.753/0001-84");

        ValidateUtils.validateDocumentNumberIfExists(fields);
    }


    @Test
    void validDocumentNumberIfExists() {

        Map<String, Object> fields = new HashMap<>();

        fields.put("documentNumber", "73.445.753/0001-83");


        AbstractRuntimeException abstractRuntimeException = assertThrows(AbstractRuntimeException.class, () -> ValidateUtils.validateDocumentNumberIfExists(fields));

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, abstractRuntimeException.getHttpStatus());
        assertEquals(MessagesUtil.getMessage(MessageErrorImpl.INVALID_DOCUMENT_NUMBER, "73.445.753/0001-83"), abstractRuntimeException.getMessages().get(0));
        assertEquals(MessagesUtil.getMessage(MessageErrorImpl.INVALID_DOCUMENT_NUMBER, "73.445.753/0001-83"), abstractRuntimeException.getMessage());
    }

    @Test
    void validDocumentNumberIfExistsWithoutDocumentNumber() {

        Map<String, Object> fields = new HashMap<>();

        fields.put("property", "value");

        ValidateUtils.validateDocumentNumberIfExists(fields);
    }
}