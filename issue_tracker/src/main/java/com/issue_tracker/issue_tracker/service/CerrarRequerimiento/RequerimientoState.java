package com.issue_tracker.issue_tracker.service.CerrarRequerimiento;

import org.springframework.beans.factory.annotation.Autowired;

import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;

public abstract class RequerimientoState {

    @Autowired
    protected RequerimientoRepository requerimientoRepository;

    protected Requerimiento requerimiento;

    public RequerimientoState(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }
   
    public abstract void cerrarRequerimiento() throws Exception;
}