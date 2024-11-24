package com.issue_tracker.issue_tracker.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.issue_tracker.issue_tracker.model.UsuarioExterno;
import com.issue_tracker.issue_tracker.model.Usuario;

import java.util.Optional;


@Repository
public interface UsuarioExternoRepository extends JpaRepository<UsuarioExterno, Integer> {
    Optional<UsuarioExterno> findByCuil(Integer cuil);
    Optional<UsuarioExterno> findByUsuario(Usuario usuario);
    
    @Query("SELECT ue FROM UsuarioExterno ue JOIN ue.usuario u WHERE u.email = ?1")
    Optional<UsuarioExterno> findByUsuarioEmail(String email);
}

