package com.issue_tracker.issue_tracker.service.CerrarRequerimiento;

import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.model.Requerimiento;

public class StateCerrado extends RequerimientoState {
     
    public StateCerrado(Requerimiento requerimiento) {
        super(requerimiento);
    }

    public void cerrarRequerimiento() 
    throws BadRequestException {
        throw new BadRequestException("El requerimiento ya se encuentra en estado cerrado");
    }
}