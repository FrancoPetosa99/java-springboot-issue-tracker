package com.issue_tracker.issue_tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("externo")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UsuarioExterno extends Usuario {

    @Column(unique = true, nullable = false, length = 11)
    private String cuil;

    private String descripcion;
    private String destacado;
    private String empresa;
}