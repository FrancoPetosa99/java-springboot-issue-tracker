package com.issue_tracker.issue_tracker.Builder.Evento;

import com.issue_tracker.issue_tracker.model.Evento;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;

public abstract class EventoBuilder {

    protected Requerimiento requerimiento;
    protected Usuario emisorUsuario;

    public EventoBuilder buildRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
        return this;
    }

    public EventoBuilder buildUsuarioEmisor(Usuario emisor) {
        this.emisorUsuario = emisor;
        return this;
    }

    public abstract Evento build();
}