package com.issue_tracker.issue_tracker.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequerimientoDetails {
    private Integer id;
    private String codigo;
    private String descripcion;
    private String asunto;
    private String prioridad;
    private String estado;
    private String tipoRequerimiento;
    private String nombreUsuarioPropietario;
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}