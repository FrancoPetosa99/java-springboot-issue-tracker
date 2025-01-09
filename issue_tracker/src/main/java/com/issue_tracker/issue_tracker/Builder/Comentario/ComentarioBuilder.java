package com.issue_tracker.issue_tracker.builder.comentario;

import java.time.LocalDateTime;
import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;

public class ComentarioBuilder {

    private String asunto;
    private String descripcion;
    private Usuario usuarioEmisor;
    private Requerimiento requerimiento;

    public ComentarioBuilder buildAsunto(String asunto) {
        this.asunto = asunto;
        return this;
    }

    public ComentarioBuilder buildDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public ComentarioBuilder buildUsuarioEmisor(Usuario emisor) {
        this.usuarioEmisor = emisor;
        return this;
    }

    public ComentarioBuilder buildRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
        return this;
    }

    public Comentario build() {
        
        Comentario comentario = new Comentario();

        comentario.setAsunto(this.asunto);
        comentario.setDescripcion(this.descripcion);
        comentario.setRequerimiento(this.requerimiento);
        comentario.setUsuarioEmisor(this.usuarioEmisor);
        comentario.setCreatedAt(LocalDateTime.now());
        comentario.setUpdatedAt(LocalDateTime.now());

        return comentario;
    }
}