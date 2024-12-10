package com.issue_tracker.issue_tracker.dto.AgregarNuevoComentario;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NuevoComentarioBodyResponse {
    private Integer id;
    private String asunto;
    private String descripcion;
    private LocalDateTime createdAt;
}
