package com.issue_tracker.issue_tracker.service.AsignarRequerimiento;

import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;

public class StateCerrado extends RequerimientoState {
     
    public StateCerrado(Requerimiento requerimiento, RequerimientoRepository requerimientoRepository) {
        super(requerimiento, requerimientoRepository);
    }

    public void asignarNuevoPropietario(UsuarioInterno propietario) 
    throws BadRequestException {
        throw new BadRequestException("El requerimiento no puede ser asignado a un usuario interno una vez que fue cerrado");
    }
}