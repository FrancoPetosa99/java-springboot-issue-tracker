package com.issue_tracker.issue_tracker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.issue_tracker.issue_tracker.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    List<Empresa> findAllByOrderByNombreAsc();
}