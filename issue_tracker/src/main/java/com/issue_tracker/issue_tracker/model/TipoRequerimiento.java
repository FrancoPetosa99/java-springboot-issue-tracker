package com.issue_tracker.issue_tracker.model;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tipos_de_requerimientos")
@Data
public class TipoRequerimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true, nullable = false)
    private String codigo;
    
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "tipoRequerimiento", fetch = FetchType.LAZY)
    private List<Requerimiento> requerimientos;

    @OneToMany(mappedBy = "tipoRequerimiento")
    private List<CategoriaRequerimiento> categorias;

}