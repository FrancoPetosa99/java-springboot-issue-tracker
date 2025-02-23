package com.issue_tracker.issue_tracker.builder.evento;

import java.time.LocalDateTime;

import com.issue_tracker.issue_tracker.model.Evento;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;

public class EventoBuilder {

    private String accion;
    private Requerimiento requerimiento;
    private Usuario emisor;

    public EventoBuilder buildAccion(String accion) {
        this.accion = accion;
        return this;
    }

    public EventoBuilder buildUsuarioEmisor(Usuario emisor) {
        this.emisor = emisor;
        return this;
    }

    public EventoBuilder buildRequerimeiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
        return this;
    }

    public Evento build() {
        
        Evento evento = new Evento(
            null, 
            accion, 
            requerimiento, 
            emisor, 
            LocalDateTime.now(), 
            LocalDateTime.now()
        );

        requerimiento.addEvento(evento);

        return evento;
    }
}