package com.issue_tracker.issue_tracker.State.Requerimiento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.issue_tracker.issue_tracker.config.CustomUserDetails;
import com.issue_tracker.issue_tracker.exception.ForbiddenException;
import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.repository.ComentarioRepository;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;

public class StateAsignado extends RequerimientoState {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private RequerimientoRepository requerimientoRepository;
    
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

        requerimientoRepository.save(requerimiento);
    }

    public void agregarComentario(Comentario comentario) {
        comentarioRepository.save(comentario);
    }

    public void cerrarRequerimiento() {

        this.requerimiento.setUsuarioPropietario(null);
        this.requerimiento.setEstado("Cerrado");

        requerimientoRepository.save(this.requerimiento);
    }
}