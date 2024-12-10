package com.issue_tracker.issue_tracker.dto.AgregarNuevoComentario;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewComentarioRequest {
    private String asunto;
    private String descripcion;
}