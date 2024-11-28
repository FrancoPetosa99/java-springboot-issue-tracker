package com.issue_tracker.issue_tracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewRequerimientoRequest {
    private String descripcion;
    private String asunto;
    private String prioridad;
    private String estado;
    private int tipoRequerimientoId;
    private int usuarioDestinatarioId;
    private int usuarioPropietarioId;
    private int usuarioEmisorId;
}