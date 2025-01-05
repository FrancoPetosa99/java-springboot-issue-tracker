package com.issue_tracker.issue_tracker.service.CerrarRequerimiento;

import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.model.Requerimiento;

public class StateAbierto extends RequerimientoState {
    
    public StateAbierto(Requerimiento requerimiento) {
        super(requerimiento);
    }

    public void cerrarRequerimiento() 
    throws BadRequestException {
        throw new BadRequestException("Primero debe asignarse el requerimiento antes de cerrarse");        
    }
}