package com.cespi.capacitacion.backend.dto;

public class UserRequestDTO {

    private String token;

    public UserRequestDTO() {

    }

    public UserRequestDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
