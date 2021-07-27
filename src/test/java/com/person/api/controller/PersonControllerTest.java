package com.person.api.controller;

import com.person.api.exception.MessageErrorImpl;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static com.person.api.exception.MessageErrorImpl.RESOURCE_ALREADY_REGISTERED;
import static com.person.api.util.MessagesUtil.getMessage;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setup() {

        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void insert() throws Exception {

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8084/v1/api/auth/token")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().string("{\"username\":\"gtr1\",\"password\":\"brincante\",\"clientId\":\"tcc\",\"clientSecret\":\"75c4fcdc-f717-4306-ac0c-265148984585\"}"))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON).body("{\n" +
                                "    \"access_token\": \"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJqVU1rQ0F1V1pTT1BUeHVuTzNza0tZUzRZM3NPWmVKeUZaYWpPV2hYM2hVIn0.eyJqdGkiOiI4ODJlOTQ2MC0zZmI1LTQ1M2YtOGFhNC1iMTdlMjJiNjY2NGMiLCJleHAiOjE1OTE2MDIzMjYsIm5iZiI6MCwiaWF0IjoxNTkxNjAyMDI2LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODAvYXV0aC9yZWFsbXMvdGNjIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImI1MGI1ZjZkLWViNzAtNDYyMi1hMDZkLTQzNDNiMTIwZTk5OSIsInR5cCI6IkJlYXJlciIsImF6cCI6InRjYyIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6IjdlZWY1ZjI2LTRiNTYtNGE3Ni05OGYxLTExNWVkMTFhZGVjNyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsInVtYV9hdXRob3JpemF0aW9uIiwidXNlciJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiZm9vIHNpbHZhIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiZ3RyMSIsImdpdmVuX25hbWUiOiJmb28iLCJmYW1pbHlfbmFtZSI6InNpbHZhIiwiZW1haWwiOiJmb29AZm9vLmNvbSJ9.dGzrhiiiShfar6K3oczccv-6OyC2NalS5xKGHYDiX1106GFcmtPtBtsiBQ4wA9skCX7aQlvQFKUAwegT68RyhTFC1aBmgfzMtrjhLJeqWf3Evl3-hwvGv4zF5bMQXTCeUZ_Vq9gAs3kb9ZNOnYTtz7lILEVomKTzRZB7LEr74O5L9xHE6BX_1bOdxzvSX_3XC02TXjObMo7RyxGJGOnmSD4cfENHMj67G9ggvSfj7ECUtQI78-ehbHlywR7h4ZjrqzW9-pDGBqQx9e4M2jbx6c4T6c7PCzQ_MFVpK1H0QIjMZGhZAJ6dNFri8EoDwWyqwizoORV8j7NtTjB3X_eCPA\"}"));

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8084/v1/api/auth/user")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJqVU1rQ0F1V1pTT1BUeHVuTzNza0tZUzRZM3NPWmVKeUZaYWpPV2hYM2hVIn0.eyJqdGkiOiI4ODJlOTQ2MC0zZmI1LTQ1M2YtOGFhNC1iMTdlMjJiNjY2NGMiLCJleHAiOjE1OTE2MDIzMjYsIm5iZiI6MCwiaWF0IjoxNTkxNjAyMDI2LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODAvYXV0aC9yZWFsbXMvdGNjIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImI1MGI1ZjZkLWViNzAtNDYyMi1hMDZkLTQzNDNiMTIwZTk5OSIsInR5cCI6IkJlYXJlciIsImF6cCI6InRjYyIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6IjdlZWY1ZjI2LTRiNTYtNGE3Ni05OGYxLTExNWVkMTFhZGVjNyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsInVtYV9hdXRob3JpemF0aW9uIiwidXNlciJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiZm9vIHNpbHZhIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiZ3RyMSIsImdpdmVuX25hbWUiOiJmb28iLCJmYW1pbHlfbmFtZSI6InNpbHZhIiwiZW1haWwiOiJmb29AZm9vLmNvbSJ9.dGzrhiiiShfar6K3oczccv-6OyC2NalS5xKGHYDiX1106GFcmtPtBtsiBQ4wA9skCX7aQlvQFKUAwegT68RyhTFC1aBmgfzMtrjhLJeqWf3Evl3-hwvGv4zF5bMQXTCeUZ_Vq9gAs3kb9ZNOnYTtz7lILEVomKTzRZB7LEr74O5L9xHE6BX_1bOdxzvSX_3XC02TXjObMo7RyxGJGOnmSD4cfENHMj67G9ggvSfj7ECUtQI78-ehbHlywR7h4ZjrqzW9-pDGBqQx9e4M2jbx6c4T6c7PCzQ_MFVpK1H0QIjMZGhZAJ6dNFri8EoDwWyqwizoORV8j7NtTjB3X_eCPA"))
                .andExpect(content().string("{\"roles\":[{\"id\":\"f476df6b-27a1-495f-bd16-9bf3e2e93865\",\"name\":\"user\"}],\"password\":\"03092205\",\"firstName\":\"Foo Name\",\"lastName\":\"Foo Name\",\"email\":\"foo@foo.com.br\",\"username\":\"foo@foo.com.br\",\"enabled\":true}"))
                .andRespond(withStatus(HttpStatus.OK));

        mockMvc.perform(post("/v1/api/person")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"fullName\": \"Foo Name\",\n" +
                        "    \"birthDate\": \"1996-05-22\",\n" +
                        "    \"mothersName\": \"roselia\",\n" +
                        "    \"email\": \"foo@foo.com.br\",\n" +
                        "    \"documentNumber\": \"281.748.910-13\",\n" +
                        "    \"sexType\": \"M\",\n" +
                        "    \"address\": {\n" +
                        "        \"zipCode\": \"73080100\",\n" +
                        "        \"city\": \"Brasília\",\n" +
                        "        \"street\": \"QMS 55A\",\n" +
                        "        \"complement\": \"LA MANOS\",\n" +
                        "        \"neighborhood\": \"Setor de Mansões de Sobradinho\",\n" +
                        "        \"state\": \"DF\"\n" +
                        "    },\n" +
                        "        \"phone\": {\n" +
                        "        \"number\": \"996585455\",\n" +
                        "        \"areaCode\": \"61\"\n" +
                        "    },\n" +
                        "        \"height\": {\n" +
                        "        \"height\": \"1.88\"\n" +
                        "    },\n" +
                        "        \"weight\": {\n" +
                        "        \"weight\": \"62\"\n" +
                        "    },\n" +
                        " \"credential\": {\n" +
                        "    \t\"password\" : \"03092205\",\n" +
                        "        \"roles\": [\n" +
                        "            {\n" +
                        "                \"id\": \"f476df6b-27a1-495f-bd16-9bf3e2e93865\",\n" +
                        "                \"name\": \"user\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }" +
                        "}"))


                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", notNullValue()))
                .andExpect(jsonPath("$.birthDate", is("1996-05-22")))
                .andExpect(jsonPath("$.mothersName", is("roselia")))
                .andExpect(jsonPath("$.email", is("foo@foo.com.br")))
                .andExpect(jsonPath("$.documentNumber", is("281.748.910-13")))
                .andExpect(jsonPath("$.sexType", is("M")))
                .andExpect(jsonPath("$.fullName", is("Foo Name")))
                .andExpect(jsonPath("$.address.zipCode", is("73080100")))
                .andExpect(jsonPath("$.address.city", is("Brasília")))
                .andExpect(jsonPath("$.address.street", is("QMS 55A")))
                .andExpect(jsonPath("$.address.complement", is("LA MANOS")))
                .andExpect(jsonPath("$.address.neighborhood", is("Setor de Mansões de Sobradinho")))
                .andExpect(jsonPath("$.address.state", is("DF")))
                .andExpect(jsonPath("$.phone.number", is("996585455")))
                .andExpect(jsonPath("$.phone.areaCode", is(61)))
                .andExpect(jsonPath("$.height.height", is("1.88")))
                .andExpect(jsonPath("$.weight.weight", is("62")));


        mockServer.verify();

    }

    @Test
    void insertWithoutAddress() throws Exception {


        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8084/v1/api/auth/token")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().string("{\"username\":\"gtr1\",\"password\":\"brincante\",\"clientId\":\"tcc\",\"clientSecret\":\"75c4fcdc-f717-4306-ac0c-265148984585\"}"))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON).body("{\n" +
                                "    \"access_token\": \"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJqVU1rQ0F1V1pTT1BUeHVuTzNza0tZUzRZM3NPWmVKeUZaYWpPV2hYM2hVIn0.eyJqdGkiOiI4ODJlOTQ2MC0zZmI1LTQ1M2YtOGFhNC1iMTdlMjJiNjY2NGMiLCJleHAiOjE1OTE2MDIzMjYsIm5iZiI6MCwiaWF0IjoxNTkxNjAyMDI2LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODAvYXV0aC9yZWFsbXMvdGNjIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImI1MGI1ZjZkLWViNzAtNDYyMi1hMDZkLTQzNDNiMTIwZTk5OSIsInR5cCI6IkJlYXJlciIsImF6cCI6InRjYyIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6IjdlZWY1ZjI2LTRiNTYtNGE3Ni05OGYxLTExNWVkMTFhZGVjNyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsInVtYV9hdXRob3JpemF0aW9uIiwidXNlciJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiZm9vIHNpbHZhIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiZ3RyMSIsImdpdmVuX25hbWUiOiJmb28iLCJmYW1pbHlfbmFtZSI6InNpbHZhIiwiZW1haWwiOiJmb29AZm9vLmNvbSJ9.dGzrhiiiShfar6K3oczccv-6OyC2NalS5xKGHYDiX1106GFcmtPtBtsiBQ4wA9skCX7aQlvQFKUAwegT68RyhTFC1aBmgfzMtrjhLJeqWf3Evl3-hwvGv4zF5bMQXTCeUZ_Vq9gAs3kb9ZNOnYTtz7lILEVomKTzRZB7LEr74O5L9xHE6BX_1bOdxzvSX_3XC02TXjObMo7RyxGJGOnmSD4cfENHMj67G9ggvSfj7ECUtQI78-ehbHlywR7h4ZjrqzW9-pDGBqQx9e4M2jbx6c4T6c7PCzQ_MFVpK1H0QIjMZGhZAJ6dNFri8EoDwWyqwizoORV8j7NtTjB3X_eCPA\"}"));

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8084/v1/api/auth/user")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJqVU1rQ0F1V1pTT1BUeHVuTzNza0tZUzRZM3NPWmVKeUZaYWpPV2hYM2hVIn0.eyJqdGkiOiI4ODJlOTQ2MC0zZmI1LTQ1M2YtOGFhNC1iMTdlMjJiNjY2NGMiLCJleHAiOjE1OTE2MDIzMjYsIm5iZiI6MCwiaWF0IjoxNTkxNjAyMDI2LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODAvYXV0aC9yZWFsbXMvdGNjIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImI1MGI1ZjZkLWViNzAtNDYyMi1hMDZkLTQzNDNiMTIwZTk5OSIsInR5cCI6IkJlYXJlciIsImF6cCI6InRjYyIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6IjdlZWY1ZjI2LTRiNTYtNGE3Ni05OGYxLTExNWVkMTFhZGVjNyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsInVtYV9hdXRob3JpemF0aW9uIiwidXNlciJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiZm9vIHNpbHZhIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiZ3RyMSIsImdpdmVuX25hbWUiOiJmb28iLCJmYW1pbHlfbmFtZSI6InNpbHZhIiwiZW1haWwiOiJmb29AZm9vLmNvbSJ9.dGzrhiiiShfar6K3oczccv-6OyC2NalS5xKGHYDiX1106GFcmtPtBtsiBQ4wA9skCX7aQlvQFKUAwegT68RyhTFC1aBmgfzMtrjhLJeqWf3Evl3-hwvGv4zF5bMQXTCeUZ_Vq9gAs3kb9ZNOnYTtz7lILEVomKTzRZB7LEr74O5L9xHE6BX_1bOdxzvSX_3XC02TXjObMo7RyxGJGOnmSD4cfENHMj67G9ggvSfj7ECUtQI78-ehbHlywR7h4ZjrqzW9-pDGBqQx9e4M2jbx6c4T6c7PCzQ_MFVpK1H0QIjMZGhZAJ6dNFri8EoDwWyqwizoORV8j7NtTjB3X_eCPA"))
                .andExpect(content().string("{\"roles\":[{\"id\":\"f476df6b-27a1-495f-bd16-9bf3e2e93865\",\"name\":\"user\"}],\"password\":\"03092205\",\"firstName\":\"Foo Name\",\"lastName\":\"Foo Name\",\"email\":\"foo@foo.com.br\",\"username\":\"foo@foo.com.br\",\"enabled\":true}"))
                .andRespond(withStatus(HttpStatus.OK));


        mockMvc.perform(post("/v1/api/person")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\n" +
                        "\t\"fullName\" : \"Foo Name\",\n" +
                        "\t\"birthDate\" : \"1996-05-22\",\n" +
                        "\t\"sexType\" : \"M\",\n" +
                        "\t\"mothersName\" : \"roselia\",\n" +
                        "\t\"email\" : \"foo@foo.com.br\",\n" +
                        "        \"height\": {\n" +
                                "        \"height\": \"1.88\"\n" +
                                "    },\n" +
                                "        \"weight\": {\n" +
                                "        \"weight\": \"62\"\n" +
                                "    },\n" +
                        "\t\"documentNumber\" : \"257.912.240-01\" ,\n" +
                                " \"credential\": {\n" +
                                "    \t\"password\" : \"03092205\",\n" +
                                "        \"roles\": [\n" +
                                "            {\n" +
                                "                \"id\": \"f476df6b-27a1-495f-bd16-9bf3e2e93865\",\n" +
                                "                \"name\": \"user\"\n" +
                                "            }\n" +
                                "        ]\n" +
                                "    }" +
                                "}"+
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.birthDate", is("1996-05-22")))
                .andExpect(jsonPath("$.mothersName", is("roselia")))
                .andExpect(jsonPath("$.email", is("foo@foo.com.br")))
                .andExpect(jsonPath("$.documentNumber", is("257.912.240-01")))
                .andExpect(jsonPath("$.sexType", is("M")))
                .andExpect(jsonPath("$.fullName", is("Foo Name")));

    }


    @Test
    void insertWithDocumentNumberAndEmailAlreadyRegistered() throws Exception {

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8084/v1/api/auth/token")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().string("{\"username\":\"gtr1\",\"password\":\"brincante\",\"clientId\":\"tcc\",\"clientSecret\":\"75c4fcdc-f717-4306-ac0c-265148984585\"}"))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON).body("{\n" +
                                "    \"access_token\": \"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJqVU1rQ0F1V1pTT1BUeHVuTzNza0tZUzRZM3NPWmVKeUZaYWpPV2hYM2hVIn0.eyJqdGkiOiI4ODJlOTQ2MC0zZmI1LTQ1M2YtOGFhNC1iMTdlMjJiNjY2NGMiLCJleHAiOjE1OTE2MDIzMjYsIm5iZiI6MCwiaWF0IjoxNTkxNjAyMDI2LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODAvYXV0aC9yZWFsbXMvdGNjIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImI1MGI1ZjZkLWViNzAtNDYyMi1hMDZkLTQzNDNiMTIwZTk5OSIsInR5cCI6IkJlYXJlciIsImF6cCI6InRjYyIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6IjdlZWY1ZjI2LTRiNTYtNGE3Ni05OGYxLTExNWVkMTFhZGVjNyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsInVtYV9hdXRob3JpemF0aW9uIiwidXNlciJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiZm9vIHNpbHZhIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiZ3RyMSIsImdpdmVuX25hbWUiOiJmb28iLCJmYW1pbHlfbmFtZSI6InNpbHZhIiwiZW1haWwiOiJmb29AZm9vLmNvbSJ9.dGzrhiiiShfar6K3oczccv-6OyC2NalS5xKGHYDiX1106GFcmtPtBtsiBQ4wA9skCX7aQlvQFKUAwegT68RyhTFC1aBmgfzMtrjhLJeqWf3Evl3-hwvGv4zF5bMQXTCeUZ_Vq9gAs3kb9ZNOnYTtz7lILEVomKTzRZB7LEr74O5L9xHE6BX_1bOdxzvSX_3XC02TXjObMo7RyxGJGOnmSD4cfENHMj67G9ggvSfj7ECUtQI78-ehbHlywR7h4ZjrqzW9-pDGBqQx9e4M2jbx6c4T6c7PCzQ_MFVpK1H0QIjMZGhZAJ6dNFri8EoDwWyqwizoORV8j7NtTjB3X_eCPA\"}"));

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8084/v1/api/auth/user")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJqVU1rQ0F1V1pTT1BUeHVuTzNza0tZUzRZM3NPWmVKeUZaYWpPV2hYM2hVIn0.eyJqdGkiOiI4ODJlOTQ2MC0zZmI1LTQ1M2YtOGFhNC1iMTdlMjJiNjY2NGMiLCJleHAiOjE1OTE2MDIzMjYsIm5iZiI6MCwiaWF0IjoxNTkxNjAyMDI2LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODAvYXV0aC9yZWFsbXMvdGNjIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImI1MGI1ZjZkLWViNzAtNDYyMi1hMDZkLTQzNDNiMTIwZTk5OSIsInR5cCI6IkJlYXJlciIsImF6cCI6InRjYyIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6IjdlZWY1ZjI2LTRiNTYtNGE3Ni05OGYxLTExNWVkMTFhZGVjNyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsInVtYV9hdXRob3JpemF0aW9uIiwidXNlciJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiZm9vIHNpbHZhIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiZ3RyMSIsImdpdmVuX25hbWUiOiJmb28iLCJmYW1pbHlfbmFtZSI6InNpbHZhIiwiZW1haWwiOiJmb29AZm9vLmNvbSJ9.dGzrhiiiShfar6K3oczccv-6OyC2NalS5xKGHYDiX1106GFcmtPtBtsiBQ4wA9skCX7aQlvQFKUAwegT68RyhTFC1aBmgfzMtrjhLJeqWf3Evl3-hwvGv4zF5bMQXTCeUZ_Vq9gAs3kb9ZNOnYTtz7lILEVomKTzRZB7LEr74O5L9xHE6BX_1bOdxzvSX_3XC02TXjObMo7RyxGJGOnmSD4cfENHMj67G9ggvSfj7ECUtQI78-ehbHlywR7h4ZjrqzW9-pDGBqQx9e4M2jbx6c4T6c7PCzQ_MFVpK1H0QIjMZGhZAJ6dNFri8EoDwWyqwizoORV8j7NtTjB3X_eCPA"))
                .andExpect(content().string("{\"roles\":[{\"id\":\"f476df6b-27a1-495f-bd16-9bf3e2e93865\",\"name\":\"user\"}],\"password\":\"03092205\",\"firstName\":\"Foo Name\",\"lastName\":\"Foo Name\",\"email\":\"zezinho@foo.com\",\"username\":\"zezinho@foo.com\",\"enabled\":true}"))
                .andRespond(withStatus(HttpStatus.OK));

        mockMvc.perform(post("/v1/api/person")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"fullName\": \"Foo Name\",\n" +
                        "    \"birthDate\": \"1996-05-22\",\n" +
                        "    \"mothersName\": \"roselia\",\n" +
                        "    \"email\": \"zezinho@foo.com\",\n" +
                        "    \"documentNumber\": \"837.650.790-73\",\n" +
                        "    \"sexType\": \"M\",\n" +
                        "    \"address\": {\n" +
                        "        \"zipCode\": \"73080100\",\n" +
                        "        \"city\": \"Brasília\",\n" +
                        "        \"street\": \"QMS 55A\",\n" +
                        "        \"complement\": \"LA MANOS\",\n" +
                        "        \"neighborhood\": \"Setor de Mansões de Sobradinho\",\n" +
                        "        \"state\": \"DF\"\n" +
                        "    },\n" +
                                " \"credential\": {\n" +
                                "    \t\"password\" : \"03092205\",\n" +
                                "        \"roles\": [\n" +
                                "            {\n" +
                                "                \"id\": \"f476df6b-27a1-495f-bd16-9bf3e2e93865\",\n" +
                                "                \"name\": \"user\"\n" +
                                "            }\n" +
                                "        ]\n" +
                                "    }" +
                                "}"+
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error", is("Unprocessable Entity")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(422)))
                .andExpect(jsonPath("$.path", is("/v1/api/person")))
                .andExpect(jsonPath("$.messages[*]", hasItem(getMessage(RESOURCE_ALREADY_REGISTERED, getMessage(MessageErrorImpl.DOCUMENT_NUMBER, "837.650.790-73")))))
                .andExpect(jsonPath("$.messages[*]", hasItem(getMessage(RESOURCE_ALREADY_REGISTERED, getMessage(MessageErrorImpl.EMAIL, "zezinho@foo.com")))))
                .andExpect(jsonPath("$.messages[*]", hasSize(2)));

    }


    @Test
    void insertWithInvalidDocumentNumber() throws Exception {

        mockMvc.perform(post("/v1/api/person")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"fullName\": \"Foo Name\",\n" +
                        "    \"birthDate\": \"1996-05-22\",\n" +
                        "    \"mothersName\": \"roselia\",\n" +
                        "    \"email\": \"foo@foo.com.br\",\n" +
                        "    \"documentNumber\": \"788.788.788-78\",\n" +
                        "    \"sexType\": \"M\",\n" +
                        "    \"address\": {\n" +
                        "        \"zipCode\": \"73080100\",\n" +
                        "        \"city\": \"Brasília\",\n" +
                        "        \"street\": \"QMS 55A\",\n" +
                        "        \"complement\": \"LA MANOS\",\n" +
                        "        \"neighborhood\": \"Setor de Mansões de Sobradinho\",\n" +
                        "        \"state\": \"DF\"\n" +
                        "    }\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Bad Request")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.path", is("/v1/api/person")))
                .andExpect(jsonPath("$.messages[0]", is(getMessage(MessageErrorImpl.INVALID_DOCUMENT_NUMBER, "788.788.788-78"))))
                .andExpect(jsonPath("$.messages[*]", hasSize(1)));

    }


    @Test
    void insertWithInvalidProperties() throws Exception {

        mockMvc.perform(post("/v1/api/person")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\n" +
                        "\t\"fullName\" : \"\",\n" +
                        "\t\"documentNumber\" : \"789.789.879-999999\", \n" +
                        "    \"address\": {\n" +
                        "        \"complement\": \"LA MANOS\",\n" +
                        "        \"neighborhood\": \"Setor de Mansoes de Sobradinho\",\n" +
                        "        \"state\": \"DF\"\n" +
                        "    }\n" +
                        "\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Bad Request")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.path", is("/v1/api/person")))
                .andExpect(jsonPath("$.messages[*]", hasSize(10)));

    }

    @Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void listPerson() throws Exception {

        mockMvc.perform(get("/v1/api/person")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].birthDate", is("1996-05-22")))
                .andExpect(jsonPath("$[0].mothersName", is("roselia")))
                .andExpect(jsonPath("$[0].email", is("brancante@foo.com")))
                .andExpect(jsonPath("$[0]documentNumber", is("522.785.120-48")))
                .andExpect(jsonPath("$[0].sexType", is("M")))
                .andExpect(jsonPath("$[0].fullName", is("brincante da silva")))
                .andExpect(jsonPath("$[0].address.zipCode", is("73080100")))
                .andExpect(jsonPath("$[0].address.city", is("Brasilia")))
                .andExpect(jsonPath("$[0].address.street", is("qms 30a BSB")))
                .andExpect(jsonPath("$[0].address.complement", is("la manos")))
                .andExpect(jsonPath("$[0].address.neighborhood", is("Setor de Mansoes de Sobradinho")))
                .andExpect(jsonPath("$[0].address.state", is("DF")))
                .andExpect(jsonPath("$[0].phone.number", is("996585455")))
                .andExpect(jsonPath("$[0].phone.areaCode", is(61)))
                .andExpect(jsonPath("[0].height.height", is("1.99")))
                .andExpect(jsonPath("[0].weight.weight", is("65")))
                .andExpect(jsonPath("$[1].birthDate", is("1998-03-24")))
                .andExpect(jsonPath("$[1].mothersName", is("maria")))
                .andExpect(jsonPath("$[1].email", is("brancantedasilva@foo.com")))
                .andExpect(jsonPath("$[1].documentNumber", is("152.863.510-85")))
                .andExpect(jsonPath("$[1].sexType", is("M")))
                .andExpect(jsonPath("$[1].fullName", is("mister")))
                .andExpect(jsonPath("$[1].address").doesNotExist())
                .andExpect(jsonPath("$[*]", hasSize(7)));

    }


    @Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void listPersonWithDocumentNumberInFilter() throws Exception {

        mockMvc.perform(get("/v1/api/person?documentNumber=522.785.120-48")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].birthDate", is("1996-05-22")))
                .andExpect(jsonPath("$[0].mothersName", is("roselia")))
                .andExpect(jsonPath("$[0].email", is("brancante@foo.com")))
                .andExpect(jsonPath("$[0]documentNumber", is("522.785.120-48")))
                .andExpect(jsonPath("$[0].sexType", is("M")))
                .andExpect(jsonPath("$[0].fullName", is("brincante da silva")))
                .andExpect(jsonPath("$[0].address.zipCode", is("73080100")))
                .andExpect(jsonPath("$[0].address.city", is("Brasilia")))
                .andExpect(jsonPath("$[0].address.street", is("qms 30a BSB")))
                .andExpect(jsonPath("$[0].address.complement", is("la manos")))
                .andExpect(jsonPath("$[0].address.neighborhood", is("Setor de Mansoes de Sobradinho")))
                .andExpect(jsonPath("$[0].address.state", is("DF")))
                .andExpect(jsonPath("$[*]", hasSize(1)));


    }

    @Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void getPerson() throws Exception {

        mockMvc.perform(get("/v1/api/person/9209786f-c1ed-4014-a3fe-5601aacee291")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.birthDate", is("1996-05-22")))
                .andExpect(jsonPath("$.mothersName", is("roselia")))
                .andExpect(jsonPath("$.email", is("brancante@foo.com")))
                .andExpect(jsonPath("$.documentNumber", is("522.785.120-48")))
                .andExpect(jsonPath("$.sexType", is("M")))
                .andExpect(jsonPath("$.fullName", is("brincante da silva")))
                .andExpect(jsonPath("$.address.zipCode", is("73080100")))
                .andExpect(jsonPath("$.address.city", is("Brasilia")))
                .andExpect(jsonPath("$.address.street", is("qms 30a BSB")))
                .andExpect(jsonPath("$.address.complement", is("la manos")))
                .andExpect(jsonPath("$.address.neighborhood", is("Setor de Mansoes de Sobradinho")))
                .andExpect(jsonPath("$.address.state", is("DF")))
                .andExpect(jsonPath("$.phone.number", is("996585455")))
                .andExpect(jsonPath("$.phone.areaCode", is(61)))
                .andExpect(jsonPath("$.height.height", is("1.99")))
                .andExpect(jsonPath("$.weight.weight", is("65")));

    }


    @Test
    void getPersonWithoutAddress() throws Exception {

        mockMvc.perform(get("/v1/api/person/63f097e2-6b1c-47cc-bae6-b1a941ee218b")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.birthDate", is("1998-03-24")))
                .andExpect(jsonPath("$.mothersName", is("maria")))
                .andExpect(jsonPath("$.email", is("brancantedasilva@foo.com")))
                .andExpect(jsonPath("$.documentNumber", is("152.863.510-85")))
                .andExpect(jsonPath("$.sexType", is("M")))
                .andExpect(jsonPath("$.fullName", is("mister")))
                .andExpect(jsonPath("$.address").doesNotExist())
                .andExpect(jsonPath("$.phone").doesNotExist())
                .andExpect(jsonPath("$.weight").doesNotExist())
                .andExpect(jsonPath("$.height").doesNotExist());

    }

    @Test
    void getPersonNotFound() throws Exception {

        mockMvc.perform(get("/v1/api/person/19?lang=pt")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("Not Found")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.path", is("/v1/api/person/19")))
                .andExpect(jsonPath("$.messages[0]", is("Pessoa não encontrado(a)!")));

    }

    @Test
    void updatePersonAndAddress() throws Exception {

        mockMvc.perform(patch("/v1/api/person/299dd3ad-65f9-43d5-8017-d15de663c5a0")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"fullName\": \"Foo Name\",\n" +
                        "    \"birthDate\": \"1996-05-22\",\n" +
                        "    \"mothersName\": \"roselia\",\n" +
                        "    \"email\": \"foozinho@foo.com.br\",\n" +
                        "    \"documentNumber\": \"454.693.510-25\",\n" +
                        "    \"sexType\": \"M\",\n" +
                        "    \"address\": {\n" +
                        "        \"zipCode\": \"73080100\",\n" +
                        "        \"city\": \"Brasília\",\n" +
                        "        \"street\": \"QMS 55A\",\n" +
                        "        \"complement\": \"LA MANOS\",\n" +
                        "        \"neighborhood\": \"Setor de Mansões de Sobradinho\",\n" +
                        "        \"state\": \"DF\"\n" +
                        "    }\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.birthDate", is("1996-05-22")))
                .andExpect(jsonPath("$.mothersName", is("roselia")))
                .andExpect(jsonPath("$.email", is("foozinho@foo.com.br")))
                .andExpect(jsonPath("$.documentNumber", is("454.693.510-25")))
                .andExpect(jsonPath("$.sexType", is("M")))
                .andExpect(jsonPath("$.fullName", is("Foo Name")))
                .andExpect(jsonPath("$.address.zipCode", is("73080100")))
                .andExpect(jsonPath("$.address.city", is("Brasília")))
                .andExpect(jsonPath("$.address.street", is("QMS 55A")))
                .andExpect(jsonPath("$.address.complement", is("LA MANOS")))
                .andExpect(jsonPath("$.address.neighborhood", is("Setor de Mansões de Sobradinho")))
                .andExpect(jsonPath("$.address.state", is("DF")));

    }


    @Test
    void updatePerson() throws Exception {

        mockMvc.perform(patch("/v1/api/person/c09dab5a-36c9-4bbc-a8c8-b3a29afd1cf2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"fullName\": \"Foo Name\",\n" +
                        "    \"birthDate\": \"1996-05-22\",\n" +
                        "    \"mothersName\": \"roselia\",\n" +
                        "    \"documentNumber\": \"239.730.690-50\",\n" +
                        "    \"sexType\": \"M\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.birthDate", is("1996-05-22")))
                .andExpect(jsonPath("$.mothersName", is("roselia")))
                .andExpect(jsonPath("$.email", is("pedacinho@foo.com")))
                .andExpect(jsonPath("$.documentNumber", is("239.730.690-50")))
                .andExpect(jsonPath("$.sexType", is("M")))
                .andExpect(jsonPath("$.fullName", is("Foo Name")))
                .andExpect(jsonPath("$.address.zipCode", is("73080100")))
                .andExpect(jsonPath("$.address.city", is("Brasilia")))
                .andExpect(jsonPath("$.address.street", is("qms 30a BSB")))
                .andExpect(jsonPath("$.address.complement", is("la manos")))
                .andExpect(jsonPath("$.address.neighborhood", is("Setor de Mansoes de Sobradinho")))
                .andExpect(jsonPath("$.address.state", is("DF")));

    }


    @Test
    void updatePersonWithSameEmailAndDocumentNumber() throws Exception {

        mockMvc.perform(patch("/v1/api/person/f62ee5b3-55b4-4a86-bc6a-a5fb06cc3bcf")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"fullName\": \"Foo Name\",\n" +
                        "    \"birthDate\": \"1996-05-22\",\n" +
                        "    \"mothersName\": \"roselia\",\n" +
                        "    \"email\": \"tttt@foo.com\",\n" +
                        "    \"documentNumber\": \"351.614.340-15\",\n" +
                        "    \"sexType\": \"M\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.birthDate", is("1996-05-22")))
                .andExpect(jsonPath("$.mothersName", is("roselia")))
                .andExpect(jsonPath("$.email", is("tttt@foo.com")))
                .andExpect(jsonPath("$.documentNumber", is("351.614.340-15")))
                .andExpect(jsonPath("$.sexType", is("M")))
                .andExpect(jsonPath("$.fullName", is("Foo Name")));

    }


    @Test
    void updatePersonWithDocumentNumberAndEMailAlreadyExist() throws Exception {

        mockMvc.perform(patch("/v1/api/person/c09dab5a-36c9-4bbc-a8c8-b3a29afd1cf2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"fullName\": \"Foo Name\",\n" +
                        "    \"birthDate\": \"1996-05-22\",\n" +
                        "    \"mothersName\": \"roselia\",\n" +
                        "    \"email\": \"zezinho@foo.com\",\n" +
                        "    \"documentNumber\": \"837.650.790-73\",\n" +
                        "    \"sexType\": \"M\"\n" +
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error", is("Unprocessable Entity")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(422)))
                .andExpect(jsonPath("$.path", is("/v1/api/person/c09dab5a-36c9-4bbc-a8c8-b3a29afd1cf2")))
                .andExpect(jsonPath("$.messages[*]", hasItem(getMessage(RESOURCE_ALREADY_REGISTERED, getMessage(MessageErrorImpl.DOCUMENT_NUMBER, "837.650.790-73")))))
                .andExpect(jsonPath("$.messages[*]", hasItem(getMessage(RESOURCE_ALREADY_REGISTERED, getMessage(MessageErrorImpl.EMAIL, "zezinho@foo.com")))))
                .andExpect(jsonPath("$.messages[*]", hasSize(2)));

    }


    @Test
    void updateWithInvalidProperties() throws Exception {

        mockMvc.perform(patch("/v1/api/person/299dd3ad-65f9-43d5-8017-d15de663c5a0")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"fullName\": \"josefino silveira ribeiro alves de oliveira pinhero da silva vieira lima souza da luz\",\n" +
                        "    \"birthDate\": \"1996-05-22\",\n" +
                        "    \"mothersName\": \"roselia\",\n" +
                        "    \"email\": \"fooooo@foo.com.br\",\n" +
                        "    \"documentNumber\": \"454.693.510-28\",\n" +
                        "    \"sexType\": \"M\",\n" +
                        "    \"address\": {\n" +
                        "        \"zipCode\": \"7308010000000000000000000000000000\",\n" +
                        "        \"city\": \"Brasília\",\n" +
                        "        \"street\": \"QMS 55A\",\n" +
                        "        \"complement\": \"LA MANOS\",\n" +
                        "        \"neighborhood\": \"Setor de Mansões de Sobradinho\",\n" +
                        "        \"state\": \"DF\"\n" +
                        "    }\n" +
                        "}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error", is("Unprocessable Entity")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(422)))
                .andExpect(jsonPath("$.path", is("/v1/api/person/299dd3ad-65f9-43d5-8017-d15de663c5a0")))
                .andExpect(jsonPath("$.messages[*]", hasItem(getMessage(MessageErrorImpl.INVALID_DOCUMENT_NUMBER, "454.693.510-28"))))
                .andExpect(jsonPath("$.messages[*]", hasSize(3)));


    }

}
