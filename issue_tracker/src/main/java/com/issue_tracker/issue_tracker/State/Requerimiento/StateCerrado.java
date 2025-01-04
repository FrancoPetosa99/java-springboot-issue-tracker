package com.issue_tracker.issue_tracker.State.Requerimiento;

import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;

public class StateCerrado extends RequerimientoState {
     
    public StateCerrado(Requerimiento requerimiento) {
        super(requerimiento);
    }

    public void asignarNuevoPropietario(Usuario nuevoPropietario) 
    throws BadRequestException {
        throw new BadRequestException("No puede asignarse un requerimiento una vez cerrado");
    }

    public void agregarComentario(Comentario comentario) 
    throws BadRequestException {
        throw new BadRequestException("No se admiten comentarios una vez cerrado el Requerimiento");
    }

    public void cerrarRequerimiento() 
    throws BadRequestException {
        throw new BadRequestException("El requerimiento ya se encuentra en estado cerrado");
    }
}