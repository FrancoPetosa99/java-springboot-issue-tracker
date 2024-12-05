package com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest;

import lombok.Data;

@Data
public class RequerimientoResponse {
    private Integer id;
    private String codigo;
    private String descripcion;
    private String asunto;
    private String prioridad;
    private String  tipoRequerimiento;
    private String categoriaRequerimiento;
}