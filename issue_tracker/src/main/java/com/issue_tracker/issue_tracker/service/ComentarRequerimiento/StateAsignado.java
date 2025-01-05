package com.issue_tracker.issue_tracker.service.ComentarRequerimiento;

import com.issue_tracker.issue_tracker.exception.ForbiddenException;
import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;

public class StateAsignado extends RequerimientoState {

    public StateAsignado(Requerimiento requerimiento) {
        super(requerimiento);
    }

    public void agregarComentario(Comentario comentario) 
    throws ForbiddenException {

        this.comentarioRepository.save(comentario);
    }
}