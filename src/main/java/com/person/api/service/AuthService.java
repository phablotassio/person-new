package com.person.api.service;

import com.person.api.client.AuthorizationClient;
import com.person.api.dto.PersonRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private AuthorizationClient client;

    public AuthService(AuthorizationClient client) {
        this.client = client;
    }

    public void createUser(PersonRequestDTO personRequestDTO) {

        client.createUser(personRequestDTO);
    }
}
