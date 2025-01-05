package com.issue_tracker.issue_tracker.service.VisualizarRequerimientos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;

public abstract class SearchStrategy {

    @Autowired
    protected RequerimientoRepository requerimientoRepository;

    public abstract Page<Requerimiento> buscarRequerimientos(Pageable pageable);
}