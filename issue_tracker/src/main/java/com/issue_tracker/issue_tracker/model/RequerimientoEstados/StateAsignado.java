package com.issue_tracker.issue_tracker.model.RequerimientoEstados;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.issue_tracker.issue_tracker.config.CustomUserDetails;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.exception.ForbiddenException;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;

public class StateAsignado extends RequerimientoState {

    private final String estado = "Asignado";

    public StateAsignado(Requerimiento requerimiento) {
        super(requerimiento);
    }

    public void asignarNuevoPropietario(Usuario nuevoPropietario)
    throws ForbiddenException {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails usuarioAutenticado = (CustomUserDetails) authentication.getPrincipal();
        Usuario usuarioAsignado = this.requerimiento.getUsuarioPropietario();
        
        if (usuarioAutenticado.getUserId() != usuarioAsignado.getId()) 
            throw new ForbiddenException("Solo el actual propietario tiene permisos para asignar el requerimiento");

        this.requerimiento.setUsuarioPropietario(nuevoPropietario);
    }

    public void cerrarRequerimiento()
    throws ForbiddenException {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();
        Usuario usuarioAsignado = this.requerimiento.getUsuarioPropietario();
        
        if (usuarioAutenticado.getId() != usuarioAsignado.getId()) 
            throw new ForbiddenException("Solo el actual propietario tiene permisos para cerrar el requerimiento");

        this.requerimiento.setUsuarioPropietario(null);
        this.requerimiento.setStateContext(new StateCerrado(requerimiento));
    }
}