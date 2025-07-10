package com.cespi.capacitacion.backend.auth;

public class RegisterRequest {

    private String phoneNumber;
    private String password;
    private String mail;

    public RegisterRequest() {

    }

    public RegisterRequest(String phoneNumber, String password, String mail) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.mail = mail;
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
