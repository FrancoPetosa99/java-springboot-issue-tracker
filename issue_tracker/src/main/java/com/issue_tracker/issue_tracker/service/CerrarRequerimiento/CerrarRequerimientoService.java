package com.issue_tracker.issue_tracker.service.CerrarRequerimiento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.issue_tracker.issue_tracker.Builder.Evento.EventoBuilder;
import com.issue_tracker.issue_tracker.State.Requerimiento.RequerimientoHandler;
import com.issue_tracker.issue_tracker.exception.ForbiddenException;
import com.issue_tracker.issue_tracker.model.Evento;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.repository.EventoRepository;

public class CerrarRequerimientoService {

    @Autowired
    private EventoRepository eventoRepository;

    public void cerrarRequerimiento(Requerimiento requerimiento) 
    throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();
        Usuario usuarioAsignado = requerimiento.getUsuarioPropietario();
        
        if (usuarioAutenticado.getId() != usuarioAsignado.getId()) 
            throw new ForbiddenException("Solo el actual propietario tiene permisos para cerrar el requerimiento");

        RequerimientoHandler handler = new RequerimientoHandler(requerimiento);

        handler.cerrarRequerimiento();
        
        Evento evento = new EventoBuilder()
        .buildAccion("Cierre del Caso")
        .buildRequerimeiento(requerimiento)
        .buildUsuarioEmisor(usuarioAsignado)
        .build();

        eventoRepository.save(evento);
    }
}