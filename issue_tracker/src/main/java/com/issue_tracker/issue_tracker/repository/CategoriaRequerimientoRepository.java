package com.issue_tracker.issue_tracker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.issue_tracker.issue_tracker.model.CategoriaRequerimiento;

public interface CategoriaRequerimientoRepository extends JpaRepository<CategoriaRequerimiento, Integer> {
    public List<CategoriaRequerimiento> findAllByTipoRequerimientoId(Integer TipoRequerimientoId);
}