package com.issue_tracker.issue_tracker.dto.RecuperarCategoriasRequerimiento;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoriaDTO {
    private Integer id;
    private String descripcion;
}