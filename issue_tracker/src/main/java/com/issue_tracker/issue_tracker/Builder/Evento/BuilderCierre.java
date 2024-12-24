package com.issue_tracker.issue_tracker.Builder.Evento;

import java.time.LocalDateTime;
import com.issue_tracker.issue_tracker.model.Evento;

public class BuilderCierre extends EventoBuilder {

    public BuilderCierre() {
        super();
    }

    public Evento build() {

        Evento evento = new Evento();

        evento.setAccion("Cierre de Caso");
        evento.setRequerimiento(this.requerimiento);
        evento.setEmisorUsuario(this.emisorUsuario);
        evento.setCreatedAt(LocalDateTime.now());
        evento.setUpdatedAt(LocalDateTime.now());

        return evento;
    }
}
