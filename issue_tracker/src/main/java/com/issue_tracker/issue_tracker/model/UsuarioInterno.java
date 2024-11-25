package com.issue_tracker.issue_tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("interno")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UsuarioInterno extends Usuario {

    @Column(unique = true)
    private Integer legajo;

    private Integer cargoId;
    private Integer departamentoId;
}