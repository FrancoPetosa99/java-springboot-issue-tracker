package com.issue_tracker.issue_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.issue_tracker.issue_tracker.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

}