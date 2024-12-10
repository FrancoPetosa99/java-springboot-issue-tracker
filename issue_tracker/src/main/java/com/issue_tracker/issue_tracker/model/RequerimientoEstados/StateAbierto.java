package com.issue_tracker.issue_tracker.model.RequerimientoEstados;

import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;

public class StateAbierto extends RequerimientoState {

    private final String estado = "Abierto";
    
    public StateAbierto(Requerimiento requerimiento) {
        super(requerimiento);
    }

    public void asignarNuevoPropietario(Usuario nuevoPropietario) {
        this.requerimiento.setUsuarioPropietario(nuevoPropietario);
        this.requerimiento.setEstado("Asignado");
        this.requerimiento.setStateContext(new StateAsignado(requerimiento));
    }

    public void cerrarRequerimiento() 
    throws BadRequestException {
        throw new BadRequestException("Primero debe asignarse el requerimiento antes de cerrarse");        
    }
}