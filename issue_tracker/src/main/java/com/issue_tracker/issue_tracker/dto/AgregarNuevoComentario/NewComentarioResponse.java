package com.issue_tracker.issue_tracker.dto.AgregarNuevoComentario;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewComentarioResponse {
    private String descripcion;
    private String emisor;
    private LocalDateTime createdAt;
}