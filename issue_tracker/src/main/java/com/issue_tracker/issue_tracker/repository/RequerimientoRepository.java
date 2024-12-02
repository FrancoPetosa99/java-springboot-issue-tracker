package com.issue_tracker.issue_tracker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.issue_tracker.issue_tracker.model.Requerimiento;

@Repository
public interface RequerimientoRepository extends JpaRepository<Requerimiento, Integer> {
    
    List<Requerimiento> findByUsuarioEmisorId(Integer userId);
}