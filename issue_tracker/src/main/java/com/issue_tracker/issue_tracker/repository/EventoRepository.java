package com.issue_tracker.issue_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.issue_tracker.issue_tracker.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
}