package com.issue_tracker.issue_tracker.dto.RecuperarListaRequerimientos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleRequerimiento {
    private Integer id;
    private String codigo;
    private String prioridad;
    private String estado;
    private String tipoRequerimiento;
    private String categoriaRequerimiento;
    private String usuarioPropietario;
    private String usuarioEmisor;
    private String asunto;
    private LocalDateTime createdAt;
    private boolean canViewDetails = false;
}