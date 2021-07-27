package com.person.api.client.dto;

import com.person.api.dto.PersonRequestDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class UserRequestDTO {

    @Valid
    @NotNull
    private List<RoleDTO> roles;
    @NotBlank
    @Size(min = 8, max = 8)
    private String password;
    private String firstName;
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String username;
    private Boolean enabled;

    public UserRequestDTO(PersonRequestDTO personRequestDTO) {
        this.roles = personRequestDTO.getCredential().getRoles();
        this.password = personRequestDTO.getCredential().getPassword();
        this.firstName = personRequestDTO.getFullName();
        this.lastName = personRequestDTO.getFullName();
        this.email = personRequestDTO.getEmail();
        this.username = personRequestDTO.getEmail();
        this.enabled = Boolean.TRUE;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
