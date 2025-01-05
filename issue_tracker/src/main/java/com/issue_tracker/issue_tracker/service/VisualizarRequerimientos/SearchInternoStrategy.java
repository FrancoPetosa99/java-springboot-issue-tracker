package com.issue_tracker.issue_tracker.service.VisualizarRequerimientos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.issue_tracker.issue_tracker.model.Requerimiento;

public class SearchInternoStrategy extends SearchStrategy {

    public SearchInternoStrategy() {
        super();
    }

    public Page<Requerimiento> buscarRequerimientos(Pageable pageable) {
        Page<Requerimiento> requerimientos = this.requerimientoRepository.findAll(pageable);
        return requerimientos;
    }
}
