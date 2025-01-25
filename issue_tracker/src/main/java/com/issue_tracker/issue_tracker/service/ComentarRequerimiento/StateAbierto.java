package com.issue_tracker.issue_tracker.service.ComentarRequerimiento;

import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.repository.ComentarioRepository;

public class StateAbierto extends RequerimientoState {
    
    public StateAbierto(Requerimiento requerimiento, ComentarioRepository comentarioRepository) {
        super(requerimiento, comentarioRepository);
    }

    public void agregarComentario(Comentario comentario) 
    throws BadRequestException {
        throw new BadRequestException("No se pueden registrar comentarios en un requerimiento sin propietario");        
    }
}