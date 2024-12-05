package com.issue_tracker.issue_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.issue_tracker.issue_tracker.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

}