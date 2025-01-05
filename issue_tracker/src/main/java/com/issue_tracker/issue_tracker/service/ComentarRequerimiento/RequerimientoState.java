package com.issue_tracker.issue_tracker.service.ComentarRequerimiento;

import org.springframework.beans.factory.annotation.Autowired;

import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.repository.ComentarioRepository;

public abstract class RequerimientoState {

    @Autowired
    protected ComentarioRepository comentarioRepository;

    protected Requerimiento requerimiento;

    public RequerimientoState(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }
   
    public abstract void agregarComentario(Comentario comentario) throws Exception;
}