package com.issue_tracker.issue_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.issue_tracker.issue_tracker.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

    @Query("SELECT c FROM Comentario c JOIN FETCH c.usuarioEmisor WHERE c.requerimiento.id = :requerimientoId")
    List<Comentario> findByRequerimientoId(@Param("requerimientoId") Integer requerimientoId);
    
}