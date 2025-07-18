package com.cespi.capacitacion.backend.auth;

import com.cespi.capacitacion.backend.entity.NumberPlate;

import java.util.ArrayList;
import java.util.List;

public class AuthResponse {

    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public AuthResponse() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
