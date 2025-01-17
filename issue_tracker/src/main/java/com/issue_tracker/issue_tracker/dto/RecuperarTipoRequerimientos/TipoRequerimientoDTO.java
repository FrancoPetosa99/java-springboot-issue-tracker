package com.issue_tracker.issue_tracker.dto.RecuperarTipoRequerimientos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TipoRequerimientoDTO {
    private Integer id;
    private String codigo;
    private String descripcion;
}