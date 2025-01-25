package com.issue_tracker.issue_tracker.service.ComentarRequerimiento;

import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.repository.ComentarioRepository;

public class StateFactory {
    
    public static RequerimientoState createRequerimientoState(Requerimiento requerimiento, ComentarioRepository comentarioRepository) {

        String currentState = requerimiento.getEstado();

        RequerimientoState stateContext = null;

        if (currentState.equalsIgnoreCase("ABIERTO")) {
            stateContext = new StateAbierto(requerimiento, comentarioRepository);
        } else if (currentState.equalsIgnoreCase("ASIGNADO")) {
            stateContext =  new StateAsignado(requerimiento, comentarioRepository);
        } else if (currentState.equalsIgnoreCase("CERRADO")) {
            stateContext =  new StateCerrado(requerimiento, comentarioRepository);
        } else {
            throw new IllegalArgumentException("Estado no valido: " + currentState);
        }
         
        return stateContext;
    }
}