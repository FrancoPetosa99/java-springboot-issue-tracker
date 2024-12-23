package com.issue_tracker.issue_tracker.State.Requerimiento;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.issue_tracker.issue_tracker.config.CustomUserDetails;
import com.issue_tracker.issue_tracker.exception.ForbiddenException;
import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;

public class StateAsignado extends RequerimientoState {
    
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

    public void agregarComentario(Comentario comentario) {
        List<Comentario> comentarios = this.requerimiento.getListaComentarios();
        comentarios.add(comentario);
    }

    public void cerrarRequerimiento()
    throws ForbiddenException {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();
        Usuario usuarioAsignado = this.requerimiento.getUsuarioPropietario();
        
        if (usuarioAutenticado.getId() != usuarioAsignado.getId()) 
            throw new ForbiddenException("Solo el actual propietario tiene permisos para cerrar el requerimiento");

        this.requerimiento.setUsuarioPropietario(null);
    }
}