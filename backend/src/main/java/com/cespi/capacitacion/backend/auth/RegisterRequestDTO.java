package com.cespi.capacitacion.backend.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class RegisterRequestDTO {

    @NotEmpty(message = "El campo 'phoneNumber' es requerido")
    private String phoneNumber;
    @NotEmpty(message = "El campo 'mail' es requerido")
    @Email(message = "El mail ingresado tiene un formato inválido")
    private String mail;
    @NotEmpty(message = "El campo 'password' es requerido")
    private String password;

    public RegisterRequestDTO() {

    }

    public RegisterRequestDTO(String phoneNumber, String mail, String password) {
        this.phoneNumber = phoneNumber;
        this.mail = mail;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
