package com.issue_tracker.issue_tracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.issue_tracker.issue_tracker.model.Requerimiento;

@Repository
public interface RequerimientoRepository extends JpaRepository<Requerimiento, Integer> {
    Page<Requerimiento> findByUsuarioEmisorId(Integer usuarioEmisorId, Pageable pageable);
    // Page<Requerimiento> findAll(Pageable pageable);
}