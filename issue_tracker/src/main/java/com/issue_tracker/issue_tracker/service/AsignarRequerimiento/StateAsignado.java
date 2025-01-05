package com.issue_tracker.issue_tracker.service.AsignarRequerimiento;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.issue_tracker.issue_tracker.exception.ForbiddenException;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;

public class StateAsignado extends RequerimientoState {

    public StateAsignado(Requerimiento requerimiento) {
        super(requerimiento);
    }

    public void asignarNuevoPropietario(UsuarioInterno propietario) 
    throws ForbiddenException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();
        Usuario usuarioAsignado = this.requerimiento.getUsuarioPropietario();
        
        if (usuarioAutenticado.getId() != usuarioAsignado.getId()) 
            throw new ForbiddenException("Solo el actual propietario puede asignar el requerimiento a otro usuario");

        this.requerimiento.setUsuarioPropietario(propietario);
        this.requerimiento.setEstado("Asignado");

        this.requerimientoRepository.save(this.requerimiento);
    }
}