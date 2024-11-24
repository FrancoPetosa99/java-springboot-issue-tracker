package com.issue_tracker.issue_tracker.model;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuarios_externos")
@PrimaryKeyJoinColumn(name = "usuario_id")
@Getter
@Setter
@ToString(callSuper = true)  
@EqualsAndHashCode(callSuper = true)
public class UsuarioExterno extends Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true, nullable = false)
    private Integer cuil;
    
    private String descripcion;
    private String destacado;
    private String empresa;
    
    @OneToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Usuario usuario;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
