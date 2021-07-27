package com.person.api.client;

import com.person.api.configuration.RestTemplateConfiguration;
import com.person.api.dto.FooRequestDTO;
import com.person.api.dto.FooResponseDTO;
import com.person.api.exception.AbstractRuntimeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@Import(RestTemplateConfiguration.class)
@RestClientTest(FooClient.class)
class FooClientTest {

    @Autowired
    private FooClient client;

    @Autowired
    private MockRestServiceServer server;

    @Test
    void insert() throws URISyntaxException {

        FooRequestDTO fooRequestDTO = new FooRequestDTO();
        fooRequestDTO.setName("Foo name");
        fooRequestDTO.setId(1233L);
        fooRequestDTO.setBirthDate(LocalDate.parse("1999-05-22"));

        server.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8089")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("FOO-HEADER-NAME", "FOO-HEADER-VALUE"))
                .andExpect(content().string("{\"id\":1233,\"name\":\"Foo name\",\"birthDate\":\"1999-05-22\",\"fooDate\":null,\"dtoList\":null,\"barDTO\":null}"))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON).body("{\"id\":124,\"name\":\"Foo name\",\"birthDate\":\"1996-05-22\"}"));

        FooResponseDTO fooResponseDTO = client.insert(fooRequestDTO);

        assertEquals(124, fooResponseDTO.getId());
        assertEquals("Foo name", fooResponseDTO.getName());
        assertEquals(LocalDate.parse("1996-05-22"), fooResponseDTO.getBirthDate());

        server.verify();


    }

    @Test
    void insertWithNotFoundError() throws URISyntaxException {

        FooRequestDTO fooRequestDTO = new FooRequestDTO();
        fooRequestDTO.setName("Foo name");
        fooRequestDTO.setId(1233L);
        fooRequestDTO.setBirthDate(LocalDate.parse("1999-05-22"));

        server.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8089")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("FOO-HEADER-NAME", "FOO-HEADER-VALUE"))
                .andExpect(content().string("{\"id\":1233,\"name\":\"Foo name\",\"birthDate\":\"1999-05-22\",\"fooDate\":null,\"dtoList\":null,\"barDTO\":null}"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON).body("{\n" +
                                "    \"timestamp\": \"2020-01-27T23:22:34.689+0000\",\n" +
                                "    \"status\": 404,\n" +
                                "    \"error\": \"Not Found\",\n" +
                                "    \"messages\": \"No message available\",\n" +
                                "    \"path\": \"/v1/api/personss\"\n" +
                                "}"));

        AbstractRuntimeException abstractRuntimeException = assertThrows(AbstractRuntimeException.class, () -> client.insert(fooRequestDTO));
        assertEquals("No message available", abstractRuntimeException.getMessage());
        assertEquals("No message available", abstractRuntimeException.getMessages().get(0));
        assertEquals(HttpStatus.NOT_FOUND, abstractRuntimeException.getHttpStatus());

        server.verify();


    }

    @Test
    void insertWithInternalServerError() throws URISyntaxException {

        FooRequestDTO fooRequestDTO = new FooRequestDTO();
        fooRequestDTO.setName("Foo name");
        fooRequestDTO.setId(1233L);
        fooRequestDTO.setBirthDate(LocalDate.parse("1999-05-22"));

        server.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8089")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("FOO-HEADER-NAME", "FOO-HEADER-VALUE"))
                .andExpect(content().string("{\"id\":1233,\"name\":\"Foo name\",\"birthDate\":\"1999-05-22\",\"fooDate\":null,\"dtoList\":null,\"barDTO\":null}"))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON).body("{\n" +
                                "    \"timestamp\": \"2020-01-27T23:22:34.689+0000\",\n" +
                                "    \"status\": 500,\n" +
                                "    \"error\": \"Internal Server Error\",\n" +
                                "    \"messages\": \"No message available\",\n" +
                                "    \"path\": \"/v1/api/personss\"\n" +
                                "}"));

        AbstractRuntimeException abstractRuntimeException = assertThrows(AbstractRuntimeException.class, () -> client.insert(fooRequestDTO));
        assertEquals("No message available", abstractRuntimeException.getMessage());
        assertEquals("No message available", abstractRuntimeException.getMessages().get(0));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, abstractRuntimeException.getHttpStatus());


        server.verify();


    }


    @Test
    void insertWithInternalServerErrorWithoutMessage() throws URISyntaxException {

        FooRequestDTO fooRequestDTO = new FooRequestDTO();
        fooRequestDTO.setName("Foo name");
        fooRequestDTO.setId(1233L);
        fooRequestDTO.setBirthDate(LocalDate.parse("1999-05-22"));

        server.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8089")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("FOO-HEADER-NAME", "FOO-HEADER-VALUE"))
                .andExpect(content().string("{\"id\":1233,\"name\":\"Foo name\",\"birthDate\":\"1999-05-22\",\"fooDate\":null,\"dtoList\":null,\"barDTO\":null}"))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON).body("{\n" +
                                "    \"timestamp\": \"2020-01-27T23:22:34.689+0000\",\n" +
                                "    \"status\": 500,\n" +
                                "    \"error\": \"Internal Server Error\",\n" +
                                "    \"messages\": No message available\",\n" +
                                "    \"path\": \"/v1/api/personss\"\n" +
                                "}"));

        AbstractRuntimeException abstractRuntimeException = assertThrows(AbstractRuntimeException.class, () -> client.insert(fooRequestDTO));
        assertNull(abstractRuntimeException.getMessage());
        assertNull(abstractRuntimeException.getMessages());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, abstractRuntimeException.getHttpStatus());


        server.verify();


    }


    @Test
    void insertWithInternalServerErrorWithJsonConvertException() throws URISyntaxException {

        FooRequestDTO fooRequestDTO = new FooRequestDTO();
        fooRequestDTO.setName("Foo name");
        fooRequestDTO.setId(1233L);
        fooRequestDTO.setBirthDate(LocalDate.parse("1999-05-22"));

        server.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8089")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("FOO-HEADER-NAME", "FOO-HEADER-VALUE"))
                .andExpect(content().string("{\"id\":1233,\"name\":\"Foo name\",\"birthDate\":\"1999-05-22\",\"fooDate\":null,\"dtoList\":null,\"barDTO\":null}"))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON).body("{\n" +
                                "    \"timestamp\": \"2020-01-27T23:22:34.689+0000\",\n" +
                                "    \"status\": 500,\n" +
                                "    \"error\": \"Internal Server Error\",\n" +
                                "    \"path\": \"/v1/api/personss\"\n" +
                                "}"));

        AbstractRuntimeException abstractRuntimeException = assertThrows(AbstractRuntimeException.class, () -> client.insert(fooRequestDTO));
        assertNull(abstractRuntimeException.getMessage());
        assertNull(abstractRuntimeException.getMessages());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, abstractRuntimeException.getHttpStatus());


        server.verify();


    }
}