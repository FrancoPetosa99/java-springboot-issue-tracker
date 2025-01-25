package com.issue_tracker.issue_tracker.dto.RecuperarComentarios;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComentarioResponse {
    private String descripcion;
    private String emisor;
    private LocalDateTime createdAt;
}