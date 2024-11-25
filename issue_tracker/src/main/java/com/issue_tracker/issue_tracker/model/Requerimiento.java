package com.issue_tracker.issue_tracker.model;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "requerimientos")
@Data
public class Requerimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length = 50)
    private String codigo;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String asunto;
    
    @Column(length = 50)
    private String prioridad;
    
    @Column(length = 50)
    private String estado;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "tipo_requerimiento_id")
    private TipoRequerimiento tipoRequerimiento;
    
    @ManyToOne
    @JoinColumn(name = "usuario_destinatario_id")
    private Usuario usuarioDestinatario;
    
    @ManyToOne
    @JoinColumn(name = "usuario_propietario_id")
    private Usuario usuarioPropietario;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "usuario_emisor_id")
    private Usuario usuarioEmisor;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

