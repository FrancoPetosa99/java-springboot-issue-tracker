package com.issue_tracker.issue_tracker.service.AsignarRequerimiento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;

@Service
public class AsignarRequerimientoService {

    @Autowired
    private RequerimientoRepository requerimientoRepository;

    public void asignarRequerimiento(Requerimiento requerimiento, UsuarioInterno propietario) 
    throws Exception {

        RequerimientoState stateContext = StateFactory.createRequerimientoState(requerimiento, requerimientoRepository);
        stateContext.asignarNuevoPropietario(propietario);
    }
}