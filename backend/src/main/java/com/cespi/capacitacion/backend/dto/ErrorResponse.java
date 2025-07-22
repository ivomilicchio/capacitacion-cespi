package com.cespi.capacitacion.backend.dto;

import java.util.Date;

public class ErrorResponse {

    private Date tiempo;
    private String mensaje;
    private String uri;

    public ErrorResponse() {

    }

    public ErrorResponse(String mensaje, String uri) {
        this.tiempo = new Date();
        this.mensaje = mensaje;
        this.uri = uri.replace("uri=", "") ;
    }

    public Date getTiempo() {
        return tiempo;
    }

    public void setTiempo(Date tiempo) {
        this.tiempo = tiempo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
