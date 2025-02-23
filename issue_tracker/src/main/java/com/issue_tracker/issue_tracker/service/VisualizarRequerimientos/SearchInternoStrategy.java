package com.issue_tracker.issue_tracker.service.VisualizarRequerimientos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;

public class SearchInternoStrategy extends SearchStrategy {

    public SearchInternoStrategy(RequerimientoRepository requerimientoRepository) {
        super(requerimientoRepository);
    }

    public Page<Requerimiento> buscarRequerimientos(Pageable pageable) {
        Page<Requerimiento> requerimientos = this.requerimientoRepository.findAll(pageable);
        return requerimientos;
    }
}
