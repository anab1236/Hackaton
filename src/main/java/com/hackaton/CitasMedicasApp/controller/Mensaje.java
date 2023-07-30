package com.hackaton.CitasMedicasApp.controller;
// Esta clase es solo para que el controlador pueda generar mensajes
public class Mensaje {
    // Atributos 
    private Integer tipoMensaje;
    private String tituloMensaje;
    private String mensaje;
    // Constructor
    public Mensaje(Integer tipoMensaje, String tituloMensaje, String mensaje) {
        this.tipoMensaje = tipoMensaje;
        this.tituloMensaje = tituloMensaje;
        this.mensaje = mensaje;
    }
    // Metodos
    public Integer getTipoMensaje() {
        return tipoMensaje;
    }
    public String getTituloMensaje() {
        return tituloMensaje;
    }
    public String getMensaje() {
        return mensaje;
    }
    
}
