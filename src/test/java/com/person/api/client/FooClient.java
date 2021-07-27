package com.person.api.client;

import com.person.api.dto.FooRequestDTO;
import com.person.api.dto.FooResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FooClient {

    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    public FooClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${foo.host}")
    private String fooHost;

    public FooResponseDTO insert(FooRequestDTO fooRequestDTO) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("FOO-HEADER-NAME", "FOO-HEADER-VALUE");

        return restTemplate.exchange(fooHost, HttpMethod.POST, new HttpEntity<>(fooRequestDTO, httpHeaders), FooResponseDTO.class).getBody();
    }

}
