package com.issue_tracker.issue_tracker.service.AsignarRequerimiento;

import com.issue_tracker.issue_tracker.model.Requerimiento;

public class StateFactory {
    
    public static RequerimientoState createRequerimientoState(Requerimiento requerimiento) {
        
        String currentState = requerimiento.getEstado();

        RequerimientoState stateContext = null;

        if (currentState.equalsIgnoreCase("ABIERTO")) {
            stateContext = new StateAbierto(requerimiento);
        } else if (currentState.equalsIgnoreCase("ASIGNADO")) {
            stateContext =  new StateAsignado(requerimiento);
        } else if (currentState.equalsIgnoreCase("CERRADO")) {
            stateContext =  new StateCerrado(requerimiento);
        } else {
            throw new IllegalArgumentException("Estado no valido: " + currentState);
        }
         
        return stateContext;
    }
}