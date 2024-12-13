package com.issue_tracker.issue_tracker.State.Requerimiento;

import com.issue_tracker.issue_tracker.model.Requerimiento;

public class StateFactory {

    public RequerimientoState getStateContext(Requerimiento requerimiento) {

        String currentState = requerimiento.getEstado();

        if (currentState.equalsIgnoreCase("ABIERTO")) {
            return new StateAbierto(requerimiento);
        } else if (currentState.equalsIgnoreCase("ASIGNADO")) {
            return new StateAsignado(requerimiento);
        } else if (currentState.equalsIgnoreCase("CERRADO")) {
            return new StateCerrado(requerimiento);
        } else {
            throw new IllegalArgumentException("Estado no valido: " + currentState);
        }
    }
}
