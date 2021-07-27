package com.person.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.person.api.dto.FooRequestDTO;
import com.person.api.exception.JsonConvertException;
import com.person.api.exception.MessageErrorImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ObjectMapperUtilTest {

    @Test
    void toObject() {

        FooRequestDTO fooRequestDTO = ObjectMapperUtil.toObject("{\n" +
                "\t\n" +
                "\t\"name\" : \"Foo name\",\n" +
                "\t\"id\" : 27, \n" +
                "\t\"birthDate\" : \"1996-05-22\"\n" +
                "}", FooRequestDTO.class);

        assertEquals(27, fooRequestDTO.getId());
        assertEquals("Foo name", fooRequestDTO.getName());
        assertEquals(LocalDate.parse("1996-05-22").toString(), fooRequestDTO.getBirthDate().toString());

    }

    @Test
    void toObjectErrorTest() {

        JsonConvertException jsonConvertException = assertThrows(JsonConvertException.class, () -> ObjectMapperUtil.toObject("", FooRequestDTO.class));

        assertEquals(MessagesUtil.getMessage(MessageErrorImpl.JSON_TO_OBJECT_FAIL), jsonConvertException.getMessage());


    }

    @Test
    void toJson() {

        JsonConvertException jsonConvertException = assertThrows(JsonConvertException.class, () -> ObjectMapperUtil.toJson(new Object()));

        assertEquals(MessagesUtil.getMessage(MessageErrorImpl.OBJECT_TO_JSON_FAIL), jsonConvertException.getMessage());

    }

    @Test
    void toJsonError() {

        FooRequestDTO fooRequestDTO = new FooRequestDTO();
        fooRequestDTO.setBirthDate(LocalDate.parse("1996-05-22"));
        fooRequestDTO.setId(124L);
        fooRequestDTO.setName("Foo name");

        String json = ObjectMapperUtil.toJson(fooRequestDTO);

        assertEquals("{\"id\":124,\"name\":\"Foo name\",\"birthDate\":\"1996-05-22\",\"fooDate\":null,\"dtoList\":null,\"barDTO\":null}", json);

    }

    @Test
    void getMapper() {

        ObjectMapper mapper = ObjectMapperUtil.getMapper();

        assertNotNull(mapper);
    }
}