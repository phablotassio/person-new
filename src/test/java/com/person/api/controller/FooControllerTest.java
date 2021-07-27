package com.person.api.controller;

import com.person.api.exception.MessageErrorImpl;
import com.person.api.util.MessagesUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class FooControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @BeforeEach
    void setup() {

        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void insert() throws Exception {

        mockMvc.perform(post("/api/test")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": \"25\",\n" +
                        "    \"name\": \"Foo Name\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(25)))
                .andExpect(jsonPath("$.name", is("Foo Name")));


    }

    @Test
    void insertWithInvalidRequestBody() throws Exception {

        mockMvc.perform(post("/api/test")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Bad Request")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.path", is("/api/test")))
                .andExpect(jsonPath("$.messages[0]", is(MessagesUtil.getMessage(MessageErrorImpl.MALFORMED_REQUEST_JSON))));


    }

    @Test
    public void insertWithInvalidName() throws Exception {

        mockMvc.perform(post("/api/test")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": \"25\",\n" +
                        "    \"name\": \" \"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Bad Request")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.path", is("/api/test")))
                .andExpect(jsonPath("$.messages[0]", is(MessagesUtil.getMessage("javax.validation.constraints.NotBlank.message", "name"))));

    }

    @Test
    public void insertWithInvalidNameInEnglish() throws Exception {

        mockMvc.perform(post("/api/test?lang=en")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": \"25\",\n" +
                        "    \"name\": \" \"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Bad Request")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.path", is("/api/test")))
                .andExpect(jsonPath("$.messages[0]", is("name must not be blank!")));

    }

    @Test
    public void callClientWithServiceUnavailable() throws Exception {

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8089")))
                .andRespond(response -> {
                    throw new ResourceAccessException("");
                });

        mockMvc.perform(post("/api/test/foo")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"id\": \"25\",\n" +
                        "    \"name\": \"Foo Name\"\n" +
                        "}"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.error", is("Service Unavailable")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(503)))
                .andExpect(jsonPath("$.path", is("/api/test/foo")))
                .andExpect(jsonPath("$.messages[0]", is(MessagesUtil.getMessage(MessageErrorImpl.SERVICE_UNAVAILABLE))));

        mockServer.verify();

    }


    @Test
    public void callClientWithNotFound() throws Exception {

        mockServer.expect(ExpectedCount.once(),
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

        mockMvc.perform(post("/api/test/foo")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1233,\"name\":\"Foo name\",\"birthDate\":\"1999-05-22\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("Not Found")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.path", is("/api/test/foo")))
                .andExpect(jsonPath("$.messages[0]", is("No message available")));

        mockServer.verify();

    }


}