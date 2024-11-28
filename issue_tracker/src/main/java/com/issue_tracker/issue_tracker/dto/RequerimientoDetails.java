package com.issue_tracker.issue_tracker.dto;

import java.time.LocalDateTime;
import com.issue_tracker.issue_tracker.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Constructor sin parámetros
@AllArgsConstructor // Constructor con todos los parámetros
public class RequerimientoDetails {
    private Integer id;
    private String codigo;
    private String descripcion;
    private String asunto;
    private String prioridad;
    private String estado;
    private String tipoRequerimiento;
    private Usuario usuarioPropietario;
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
