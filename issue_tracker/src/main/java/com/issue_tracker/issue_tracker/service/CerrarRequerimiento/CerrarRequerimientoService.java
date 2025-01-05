package com.issue_tracker.issue_tracker.service.CerrarRequerimiento;

import org.springframework.beans.factory.annotation.Autowired;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;
import com.issue_tracker.issue_tracker.service.EventoService;

public class CerrarRequerimientoService {

    @Autowired
    private EventoService eventoService;

    public void cerrarRequerimiento(Requerimiento requerimiento) 
    throws Exception {

        RequerimientoState stateContext = StateFactory.createRequerimientoState(requerimiento);
        stateContext.cerrarRequerimiento();

        UsuarioInterno propietarioAsignado = (UsuarioInterno) requerimiento.getUsuarioPropietario(); 

        eventoService.registrarEvento(requerimiento, propietarioAsignado,"Cierre del Caso");
    }
}