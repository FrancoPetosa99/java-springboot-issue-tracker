package com.issue_tracker.issue_tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@DiscriminatorValue("externo")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UsuarioExterno extends Usuario {

    @Column(name = "cuil", unique = true, nullable = false, length = 11)
    private String cuil;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "destacado", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean destacado;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
}