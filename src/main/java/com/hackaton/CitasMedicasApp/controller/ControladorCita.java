package com.hackaton.CitasMedicasApp.controller;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;

import com.hackaton.CitasMedicasApp.model.Cita;
import com.hackaton.CitasMedicasApp.model.RepositorioCita;


public class ControladorCita {
    @Autowired
    private RepositorioCita repositorioCita;

    // Constructor
    public ControladorCita(RepositorioCita repositorioCita) {
        this.repositorioCita = repositorioCita;
    }
    // Metodos

    // genera Informe
    public Mensaje generarInforme(){
        Cita min = null;
        Cita max = null;
        double promPrecio = 0.0;
        double totalPrecio = 0.0;
        List<Cita> citas = buscarCita();
        for(Cita cita : citas) {
            if(min == null && max == null){
                min = cita;
                max = cita;
            }
            if( min.getPrecio() > cita.getPrecio() ){
                min = cita;
            }
            if( max.getPrecio() < cita.getPrecio() ){
                max = cita;
            }
            promPrecio += cita.getPrecio();
        }
        if(citas.size() > 0) {
            totalPrecio = promPrecio;
            totalPrecio = Math.floor(totalPrecio * 10) / 10;
            promPrecio = promPrecio/citas.size();
            promPrecio = Math.floor(promPrecio * 10) / 10;
            DecimalFormat df = new DecimalFormat("#.0");
            return new Mensaje(JOptionPane.PLAIN_MESSAGE,"",
                String.format(
                    "Paciente con cita m\u00E1s costosa: %s\n" +
                    "Paciente con cita menos costosa: %s\n" +
                    "Promedio del precio de las citas: %s\n" +
                    "Valor total acumulado de las citas: %s",
                    max.getPaciente(),min.getPaciente(),df.format(promPrecio),df.format(totalPrecio)));
        }
        return new Mensaje(JOptionPane.ERROR_MESSAGE,"Error","No hay citas agendas");
    }
    // generar Informe Ordenar
    public Mensaje orderCitas(Cita inCita){
        String str = "";
        List<Cita> citas = buscarCita();
        for(int i = 0; i < citas.size(); i++){
            for(int j = i + 1; j < citas.size(); j++) {
                if(citas.get(j).getPrecio() > citas.get(i).getPrecio() || (citas.get(j).getPrecio().equals(citas.get(i).getPrecio()) && citas.get(j).getId() > citas.get(i).getId())) {
                    Cita auxCita = citas.get(i);
                    citas.set(i, citas.get(j));
                    citas.set(j, auxCita);
                }
            }
            if(inCita.getPrecio() < citas.get(i).getPrecio() ){
                continue;
            }
            str += String.format("%d %s %s %.1f %s",
                citas.get(i).getId(),
                citas.get(i).getPaciente(),
                citas.get(i).getTipoServicio(),
                (citas.get(i).getPrecio() * 10) / 10,
                citas.get(i).getHora());
            if(i < citas.size() - 1){
                str += "\n";
            }
        }
        if(citas.size() > 0) {
            return new Mensaje(JOptionPane.PLAIN_MESSAGE,"",str);
        }
        return new Mensaje(JOptionPane.ERROR_MESSAGE,"Error","No hay citas agendas");
    }
    // Verifica si existe el id de la cita
    public boolean verificarExistencia(Integer id){
        return !this.repositorioCita.findById(id).isEmpty();
    }
    // CRUD - Create - Agrega Cita
    public Mensaje agregarCita(Cita cita){
        if(verificarExistencia(cita.getId())){
            return new Mensaje(JOptionPane.ERROR_MESSAGE,"Error","Ya existe una cita con este Id");
        }
        this.repositorioCita.save(cita);
        return new Mensaje(JOptionPane.INFORMATION_MESSAGE,"Confirmacion","La cita se agrego correctamente");
    }
    // CRUD - Read - Regresa todas la citas
    public List<Cita> buscarCita(){
        return this.repositorioCita.findAll();
    }
    // CRUD - Read - Regresa la cita segun el id
    public Cita buscarCita(Integer id){
        return this.repositorioCita.findById(id).get();
    }
    // CRUD - Delete - Elimina una cita selecionada
    public Mensaje eliminarCita(Cita cita){
        if(!verificarExistencia(cita.getId())){
            return new Mensaje(JOptionPane.ERROR_MESSAGE,"Error","No existe una cita con este Id");
        }
        this.repositorioCita.deleteById(cita.getId());
        return new Mensaje(JOptionPane.INFORMATION_MESSAGE,"Confirmacion","La cita se elimino correctamente");
    }
    // CRUD - Update - Actualiza una cita
    public Mensaje actualizarCita(Cita cita){
        if(!verificarExistencia(cita.getId())){
            return new Mensaje(JOptionPane.ERROR_MESSAGE,"Error","No existe una cita con este Id");
        }
        Cita nuevaCita = this.repositorioCita.findById(cita.getId()).get();
        // Si algun campo es vacio no se actualiza
        if (cita.getPaciente() != null) {
            nuevaCita.setPaciente(cita.getPaciente());
        }
        if (cita.getTipoServicio() != null) {
            nuevaCita.setTipoServicio(cita.getTipoServicio());
        }
        if (cita.getPrecio() != null) {
            nuevaCita.setPrecio(cita.getPrecio());
        }
        if (cita.getHora() != null) {
            nuevaCita.setHora(cita.getHora());
        }
        this.repositorioCita.save(nuevaCita);
        // listaCitas.put(cita.getId(),cita);
        return new Mensaje(JOptionPane.INFORMATION_MESSAGE,"Confirmacion","La cita se actualizo correctamente");
    }    
}
