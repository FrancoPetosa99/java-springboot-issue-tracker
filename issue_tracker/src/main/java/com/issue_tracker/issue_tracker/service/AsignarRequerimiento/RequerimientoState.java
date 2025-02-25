package com.issue_tracker.issue_tracker.service.AsignarRequerimiento;

import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;

public abstract class RequerimientoState {

    protected RequerimientoRepository requerimientoRepository;
    protected Requerimiento requerimiento;

    public RequerimientoState(Requerimiento requerimiento, RequerimientoRepository requerimientoRepository) {
        this.requerimiento = requerimiento;
        this.requerimientoRepository = requerimientoRepository;
    }
   
    public abstract void asignarNuevoPropietario(UsuarioInterno propietario) throws Exception;
}