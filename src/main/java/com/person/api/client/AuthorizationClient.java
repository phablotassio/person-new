package com.person.api.client;

import com.person.api.client.dto.JsonWebTokenDTO;
import com.person.api.client.dto.TokenRequestDTO;
import com.person.api.client.dto.UserRequestDTO;
import com.person.api.dto.PersonRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.person.api.util.UrlsUtil.TOKEN_URL;
import static com.person.api.util.UrlsUtil.USER_URL;

@Component
public class AuthorizationClient {

    private RestTemplate restTemplate;

    @Value("${auth.user}")
    private String user;

    @Value("${auth.password}")
    private String userPw;

    @Value("${auth.client.id}")
    private String clientId;

    @Value("${auth.client.secret}")
    private String clientSecret;

    @Value("${auth.host}")
    private String authHost;

    public AuthorizationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void createUser(PersonRequestDTO personRequestDTO) {

        UserRequestDTO userRequestDTO = new UserRequestDTO(personRequestDTO);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(generateToken().getAccessToken());

        HttpEntity<UserRequestDTO> entity = new HttpEntity<>(userRequestDTO, httpHeaders);

        restTemplate.exchange(
                authHost.trim() + USER_URL,
                HttpMethod.POST, entity, Void.class);
    }

    private JsonWebTokenDTO generateToken() {

        HttpHeaders httpHeaders = new HttpHeaders();

        TokenRequestDTO tokenRequestDTO = new TokenRequestDTO(user, userPw, clientId, clientSecret);

        HttpEntity<TokenRequestDTO> entity = new HttpEntity<>(tokenRequestDTO, httpHeaders);

        return restTemplate.exchange(
                authHost.trim() + TOKEN_URL,
                HttpMethod.POST, entity, JsonWebTokenDTO.class).getBody();

    }

}
