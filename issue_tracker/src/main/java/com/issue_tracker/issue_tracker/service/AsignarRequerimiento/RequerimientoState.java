package com.issue_tracker.issue_tracker.service.AsignarRequerimiento;

import org.springframework.beans.factory.annotation.Autowired;

import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;

public abstract class RequerimientoState {

    @Autowired
    protected RequerimientoRepository requerimientoRepository;

    protected Requerimiento requerimiento;

    public RequerimientoState(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }
   
    public abstract void asignarNuevoPropietario(UsuarioInterno propietario) throws Exception;
}