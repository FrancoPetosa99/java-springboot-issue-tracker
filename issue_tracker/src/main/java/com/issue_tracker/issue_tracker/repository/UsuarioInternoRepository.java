package com.issue_tracker.issue_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;

@Repository
public interface UsuarioInternoRepository extends JpaRepository<UsuarioInterno, Integer> {
    
}