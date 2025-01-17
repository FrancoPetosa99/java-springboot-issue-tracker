package com.issue_tracker.issue_tracker.service.VisualizarRequerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.config.CustomUserDetails;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;

@Service
public class BuscarRequerimientosService {

    @Autowired
    RequerimientoRepository requerimientoRepository;

    public Page<Requerimiento> buscarRequerimientos(Pageable pageable) 
    throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        String tipoUsuario = currentUser.getRole();

        SearchStrategy strategy = StrategyFactory.getStrategyClass(tipoUsuario, requerimientoRepository);

        return strategy.buscarRequerimientos(pageable);
    }
}