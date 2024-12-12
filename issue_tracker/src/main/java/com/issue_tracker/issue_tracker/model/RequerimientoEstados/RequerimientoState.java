package com.issue_tracker.issue_tracker.model.RequerimientoEstados;

import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;

public abstract class RequerimientoState {
    
    private String estado;
    protected Requerimiento requerimiento;

    public RequerimientoState(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }

    public abstract void asignarNuevoPropietario(Usuario usuarioPropietario) throws Exception;
    public abstract void cerrarRequerimiento() throws Exception;
    public abstract void agregarComentario(Comentario comentario) throws Exception;

    public String getEstado(Usuario usuarioPropietario) {
        return this.estado;
    }
}