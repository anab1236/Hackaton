package com.hackaton.CitasMedicasApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
@Entity    
@Table(name = "cita")
public class Cita {
    // Atributos
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "paciente")
    private String paciente;

    @Column(name = "tipoServicio")
    private String tipoServicio;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "hora")
    private String hora;

    // Constructores
    public Cita() {
    }

    public Cita(Integer id, String paciente, String tipoServicio, Double precio, String hora) {
        this.id = id;
        this.paciente = paciente;
        this.tipoServicio = tipoServicio;
        this.precio = precio;
        this.hora = hora;
    }
    

    // Metodos
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getPaciente() {
        return paciente;
    }
    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }
    public String getTipoServicio() {
        return tipoServicio;
    }
    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    public String getHora() {
        return hora;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }
    public boolean equals(Cita cita){
        if(this.id != cita.getId()){
            return false;
        }
        if(!this.paciente.equals(cita.getPaciente())){
            return false;
        }
        if(!this.tipoServicio.equals(cita.getTipoServicio())){
            return false;
        }
        if(this.precio != cita.getPrecio()){
            return false;
        }
        if(!this.hora.equals(cita.getHora())){
            return false;
        }
        return true;
    }
}