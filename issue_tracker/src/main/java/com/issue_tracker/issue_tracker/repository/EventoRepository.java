package com.issue_tracker.issue_tracker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.issue_tracker.issue_tracker.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findByRequerimientoId(Integer requerimientoId);
}