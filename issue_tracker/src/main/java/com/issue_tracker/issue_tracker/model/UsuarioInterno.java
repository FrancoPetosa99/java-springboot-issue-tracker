package com.issue_tracker.issue_tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@DiscriminatorValue("interno")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UsuarioInterno extends Usuario {

    @Column(unique = true)
    private Integer legajo;

    @Column(name = "cargo_id", nullable = false)
    private Integer cargo;

    @Column(name = "departamento_id", nullable = false)
    private Integer departamento;
}