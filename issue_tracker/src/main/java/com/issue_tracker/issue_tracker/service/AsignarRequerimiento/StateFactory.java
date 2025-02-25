package com.issue_tracker.issue_tracker.service.AsignarRequerimiento;

import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;

public class StateFactory {
    
    public static RequerimientoState createRequerimientoState(Requerimiento requerimiento, RequerimientoRepository requerimientoRepository) {
        
        String currentState = requerimiento.getEstado();

        RequerimientoState stateContext = null;

        if (currentState.equalsIgnoreCase("ABIERTO")) {
            stateContext = new StateAbierto(requerimiento, requerimientoRepository);
        } else if (currentState.equalsIgnoreCase("ASIGNADO")) {
            stateContext =  new StateAsignado(requerimiento, requerimientoRepository);
        } else if (currentState.equalsIgnoreCase("CERRADO")) {
            stateContext =  new StateCerrado(requerimiento, requerimientoRepository);
        } else {
            throw new IllegalArgumentException("Estado no valido: " + currentState);
        }
         
        return stateContext;
    }
}