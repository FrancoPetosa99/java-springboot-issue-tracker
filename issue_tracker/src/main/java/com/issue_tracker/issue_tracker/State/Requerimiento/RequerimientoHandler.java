package com.issue_tracker.issue_tracker.State.Requerimiento;

import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;

public class RequerimientoHandler {

    private RequerimientoState stateContext;

    public RequerimientoHandler(Requerimiento requerimiento) {
        StateFactory factory = new StateFactory();
        this.stateContext = factory.createStateContext(requerimiento);
    }

    public void setStateContext(RequerimientoState stateContext) {
        this.stateContext = stateContext;
    }

    public void agregarComentario(Comentario comentario) 
    throws Exception {
        this.stateContext.agregarComentario(comentario);
    }

    public void asignarPropietario(UsuarioInterno nuevoPropietario)
    throws Exception {
        this.stateContext.asignarNuevoPropietario(nuevoPropietario);
    }

    public void cerrarRequerimiento()
    throws Exception {
        this.stateContext.cerrarRequerimiento();
    }
}