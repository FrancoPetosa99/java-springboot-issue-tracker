package com.issue_tracker.issue_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.issue_tracker.issue_tracker.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    List<Comentario> findByRequerimientoId(Integer requerimientoId);
}