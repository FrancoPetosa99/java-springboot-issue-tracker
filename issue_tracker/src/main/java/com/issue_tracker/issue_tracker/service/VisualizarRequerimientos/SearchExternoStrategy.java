package com.issue_tracker.issue_tracker.service.VisualizarRequerimientos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.issue_tracker.issue_tracker.config.CustomUserDetails;
import com.issue_tracker.issue_tracker.model.Requerimiento;

public class SearchExternoStrategy extends SearchStrategy {

    public SearchExternoStrategy() {
        super();
    }
    
    public Page<Requerimiento> buscarRequerimientos(Pageable pageable) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        Integer userId = currentUser.getUserId();
        
        Page<Requerimiento> requerimientos = this.requerimientoRepository.findByUsuarioEmisorId(userId, pageable);

        return requerimientos;
    }
}