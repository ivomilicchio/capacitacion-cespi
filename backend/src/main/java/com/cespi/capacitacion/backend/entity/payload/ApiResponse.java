package com.cespi.capacitacion.backend.entity.payload;

import java.util.Date;

public class ApiResponse {

    private Date tiempo;
    private String mensaje;
    private String uri;

    public ApiResponse() {

    }

    public ApiResponse(String mensaje, String uri) {
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
