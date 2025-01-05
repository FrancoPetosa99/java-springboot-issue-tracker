package com.issue_tracker.issue_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.config.CustomUserDetails;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;

@Service
public class RequerimientoService {

    @Autowired
    private RequerimientoRepository requerimientoRepository;
    
    public Page<Requerimiento> getRequerimientos(Pageable pageable) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        String role = currentUser.getRole();
        
        if (role.equalsIgnoreCase("EXTERNO")) {
            Integer userId = currentUser.getUserId();
            Page<Requerimiento> requerimientos = this.getRequerimientoByUsuarioEmisorId(userId, pageable);
            return requerimientos;
        }

        Page<Requerimiento> requerimientos = requerimientoRepository.findAll(pageable);
        return requerimientos;
    }

    private Page<Requerimiento> getRequerimientoByUsuarioEmisorId(Integer userId, Pageable pageable) {
        Page<Requerimiento> requerimientos = requerimientoRepository.findByUsuarioEmisorId(userId, pageable);
        return requerimientos;
    }
    
    public Requerimiento getRequerimientoById(Integer requerimientoId) 
    throws NotFoundException {

        Requerimiento requerimiento = requerimientoRepository.findById(requerimientoId).orElse(null);

        if (requerimiento == null) 
            throw new NotFoundException("No se ha encontrado Requerimiento con id: " + requerimientoId);

        return requerimiento;
    }
}