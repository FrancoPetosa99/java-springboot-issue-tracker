package com.issue_tracker.issue_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.issue_tracker.issue_tracker.config.CustomUserDetails;
import com.issue_tracker.issue_tracker.exception.ForbiddenException;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;

@Service
public class RequerimientoService {

    @Autowired
    private RequerimientoRepository requerimientoRepository;

    public Requerimiento getRequerimientoById(Integer requerimientoId) 
    throws Exception {

        Requerimiento requerimiento = requerimientoRepository.findById(requerimientoId).orElse(null);

        if (requerimiento == null) 
            throw new NotFoundException("No se ha encontrado Requerimiento con id: " + requerimientoId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        Integer currentUserId = currentUser.getUserId();
        String currentUserRole = currentUser.getRole();

        UsuarioInterno usuarioPropietario = (UsuarioInterno) requerimiento.getUsuarioPropietario();
        Usuario usuarioEmisor = requerimiento.getUsuarioEmisor();
        
        if (
            currentUserRole.equalsIgnoreCase("interno") &&
            usuarioPropietario != null &&
            currentUserId != usuarioPropietario.getId() &&
            currentUserId != usuarioEmisor.getId()
        ) 
            throw new ForbiddenException();

        if (
            currentUserRole.equalsIgnoreCase("externo") &&
            currentUserId != usuarioEmisor.getId()
        ) 
            throw new ForbiddenException();
 
        return requerimiento;
    }
}