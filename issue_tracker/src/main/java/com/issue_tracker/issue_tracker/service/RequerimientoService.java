package com.issue_tracker.issue_tracker.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;

@Service
public class RequerimientoService {
    private final RequerimientoRepository requerimientoRepository;
    
    public RequerimientoService(RequerimientoRepository requerimientoRepository) {
        this.requerimientoRepository = requerimientoRepository;
    }
    
    public List<Requerimiento> getRequerimientosByUser(Usuario usuario) {
        return requerimientoRepository.findAllByUserOrderByDate(usuario);
    }
    
    public List<Requerimiento> getActiveRequerimientosByUser(Usuario usuario) {
        return requerimientoRepository.findActiveByUser(usuario);
    }


}