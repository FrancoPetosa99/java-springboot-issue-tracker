package com.issue_tracker.issue_tracker.service.ComentarRequerimiento;

import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.repository.ComentarioRepository;

public abstract class RequerimientoState {

    protected ComentarioRepository comentarioRepository;

    protected Requerimiento requerimiento;

    public RequerimientoState(Requerimiento requerimiento, ComentarioRepository comentarioRepository) {
        this.requerimiento = requerimiento;
        this.comentarioRepository = comentarioRepository;
    }
   
    public abstract void agregarComentario(Comentario comentario) throws Exception;
}