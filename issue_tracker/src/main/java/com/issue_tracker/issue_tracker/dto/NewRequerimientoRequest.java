package com.issue_tracker.issue_tracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Genera getters, setters, equals, hashCode, toString
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
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