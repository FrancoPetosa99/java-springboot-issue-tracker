package com.issue_tracker.issue_tracker.service.AsignarRequerimiento;

import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;

public class StateAbierto extends RequerimientoState {
    
    public StateAbierto(Requerimiento requerimiento) {
        super(requerimiento);
    }

    public void asignarNuevoPropietario(UsuarioInterno propietario) 
    throws BadRequestException {
        
        this.requerimiento.setUsuarioPropietario(propietario);
        this.requerimiento.setEstado("Asignado");

        this.requerimientoRepository.save(this.requerimiento);        
    }
}