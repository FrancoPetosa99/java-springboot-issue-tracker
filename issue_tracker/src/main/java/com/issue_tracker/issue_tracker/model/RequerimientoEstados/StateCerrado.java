package com.issue_tracker.issue_tracker.model.RequerimientoEstados;

import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;

public class StateCerrado extends RequerimientoState {

    private final String estado = "Cerrado";
 
    public StateCerrado(Requerimiento requerimiento) {
        super(requerimiento);
    }

    public void asignarNuevoPropietario(Usuario nuevoPropietario) {
        this.requerimiento.setUsuarioPropietario(nuevoPropietario);
        this.requerimiento.setEstado("Asignado");
        this.requerimiento.setStateContext(new StateAsignado(requerimiento));
    }

    public void agregarComentario() 
    throws BadRequestException {
        throw new BadRequestException("No se admiten comentarios una vez cerrado el Requerimiento");
    }

    public void cerrarRequerimiento() 
    throws BadRequestException {
        throw new BadRequestException("El requerimiento ya se encuentra en estado cerrado");
    }
}