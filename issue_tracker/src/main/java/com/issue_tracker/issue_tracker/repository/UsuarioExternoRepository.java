package com.issue_tracker.issue_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.issue_tracker.issue_tracker.model.UsuarioExterno;

@Repository
public interface UsuarioExternoRepository extends JpaRepository<UsuarioExterno, Integer> {
    UsuarioExterno findByEmail(String email);
    UsuarioExterno findByCuil(String cuil);
    boolean existsByEmail(String email);
    boolean existsByCuil(String cuil);
}