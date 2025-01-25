package com.issue_tracker.issue_tracker.service.ComentarRequerimiento;

import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.repository.ComentarioRepository;

public class StateCerrado extends RequerimientoState {
     
    public StateCerrado(Requerimiento requerimiento, ComentarioRepository comentarioRepository) {
        super(requerimiento, comentarioRepository);
    }

    public void agregarComentario(Comentario comentario) 
    throws BadRequestException {
        throw new BadRequestException("No se puede comentar un requerimiento una vez que fue cerrado");
    }
}