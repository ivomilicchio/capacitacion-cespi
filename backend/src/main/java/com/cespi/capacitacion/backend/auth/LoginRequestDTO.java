package com.cespi.capacitacion.backend.auth;

import jakarta.validation.constraints.NotEmpty;

public class LoginRequestDTO {

    @NotEmpty(message = "El campo 'phoneNumber' es requerido")
    private String phoneNumber;
    @NotEmpty(message = "El campo 'password' es requerido")
    private String password;


    public LoginRequestDTO() {

    }

    public LoginRequestDTO(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
