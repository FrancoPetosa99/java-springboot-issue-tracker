package com.issue_tracker.issue_tracker.service.ComentarRequerimiento;

import com.issue_tracker.issue_tracker.exception.ForbiddenException;
import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.repository.ComentarioRepository;

public class StateAsignado extends RequerimientoState {

    public StateAsignado(Requerimiento requerimiento, ComentarioRepository comentarioRepository) {
        super(requerimiento, comentarioRepository);
    }

    public void agregarComentario(Comentario comentario) 
    throws ForbiddenException {

        this.comentarioRepository.save(comentario);
    }
}