package com.issue_tracker.issue_tracker.service.AsignarRequerimiento;

import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;

@Service
public class AsignarRequerimientoService {

    public void asignarRequerimiento(Requerimiento requerimiento, UsuarioInterno propietario) 
    throws Exception {

        RequerimientoState stateContext = StateFactory.createRequerimientoState(requerimiento);
        stateContext.asignarNuevoPropietario(propietario);
    }
}