package com.issue_tracker.issue_tracker.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "archivos")
@Data
@NoArgsConstructor
public class ArchivoAdjunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "extension")
    private String extension;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "contenido_base64", columnDefinition = "LONGTEXT")
    private String contenido;
    
    @ManyToOne
    @JoinColumn(name = "comentario_id")
    private Comentario comentario;

    @ManyToOne
    @JoinColumn(name = "requerimiento_id", nullable = false)
    private Requerimiento requerimiento;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", updatable = false)
    private LocalDateTime deletedAt;
}