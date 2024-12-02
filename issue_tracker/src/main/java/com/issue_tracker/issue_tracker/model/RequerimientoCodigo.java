package com.issue_tracker.issue_tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "requerimiento_codigos")
@Data
@NoArgsConstructor
public class RequerimientoCodigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "year", nullable = false)
    private Integer year;
    
    @Column(name = "tipo")
    private String tipo;

    @Column(name = "contador", nullable = false)
    private Integer contador;
}