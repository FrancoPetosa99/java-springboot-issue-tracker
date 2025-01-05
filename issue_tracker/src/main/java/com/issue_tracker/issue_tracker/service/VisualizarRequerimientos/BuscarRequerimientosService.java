package com.issue_tracker.issue_tracker.service.VisualizarRequerimientos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.issue_tracker.issue_tracker.config.CustomUserDetails;
import com.issue_tracker.issue_tracker.model.Requerimiento;

@Service
public class BuscarRequerimientosService {

    public Page<Requerimiento> buscarRequerimientos(Pageable pageable) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        String tipoUsuario = currentUser.getRole();

        SearchStrategy strategy = StrategyFactory.createSearchStrategy(tipoUsuario);

        return strategy.buscarRequerimientos(pageable);
    }
}