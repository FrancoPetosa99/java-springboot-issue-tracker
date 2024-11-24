package com.issue_tracker.issue_tracker.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;


@Repository
public interface RequerimientoRepository extends JpaRepository<Requerimiento, Integer> {
    // Basic find by user ordered by date
    List<Requerimiento> findByUsuarioEmisorOrderByCreatedAtDesc(Usuario usuario);
    
    // Alternative using @Query
    @Query("SELECT r FROM Requerimiento r WHERE r.usuarioEmisor = ?1 ORDER BY r.createdAt DESC")
    List<Requerimiento> findAllByUserOrderByDate(Usuario usuario);
    
    // If you need active (not deleted) only
    @Query("SELECT r FROM Requerimiento r WHERE r.usuarioEmisor = ?1 AND r.deletedAt IS NULL ORDER BY r.createdAt DESC")
    List<Requerimiento> findActiveByUser(Usuario usuario);
}