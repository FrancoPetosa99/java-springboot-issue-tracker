package com.issue_tracker.issue_tracker.Builder.Evento;

import java.time.LocalDateTime;

import com.issue_tracker.issue_tracker.model.Evento;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;

public class EventoBuilder {

    private String accion;
    private Requerimiento requerimiento;
    private Usuario emisorUsuario;

    public EventoBuilder buildActionTypeComentario() {
        this.accion = "Emision de Respuesta";
        return this;
    }

    public EventoBuilder buildActionTypeAltaRequerimiento() {
        this.accion = "Alta de Requerimiento";
        return this;
    }

    public EventoBuilder buildActionTypeAssignacion() {
        this.accion = "Asignacion de Caso";
        return this;
    }

    public EventoBuilder buildActionTypeCierreDeCaso() {
        this.accion = "Asignación de Caso";
        return this;
    }

    public EventoBuilder buildRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
        return this;
    }

    public EventoBuilder buildUsuarioEmisor(Usuario emisor) {
        this.emisorUsuario = emisor;
        return this;
    }

    public Evento build() {

        Evento evento = new Evento();

        evento.setAccion(this.accion);
        evento.setRequerimiento(this.requerimiento);
        evento.setEmisorUsuario(this.emisorUsuario);
        evento.setCreatedAt(LocalDateTime.now());
        evento.setUpdatedAt(LocalDateTime.now());

        return evento;
    } 
}