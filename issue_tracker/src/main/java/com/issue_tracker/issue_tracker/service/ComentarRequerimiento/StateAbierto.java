package com.issue_tracker.issue_tracker.service.ComentarRequerimiento;

import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;

public class StateAbierto extends RequerimientoState {
    
    public StateAbierto(Requerimiento requerimiento) {
        super(requerimiento);
    }

    public void agregarComentario(Comentario comentario) 
    throws BadRequestException {
        throw new BadRequestException("No se pueden registrar comentarios en un requerimiento sin propietario");        
    }
}