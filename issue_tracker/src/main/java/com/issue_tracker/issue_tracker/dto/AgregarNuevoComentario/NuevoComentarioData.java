package com.issue_tracker.issue_tracker.dto.AgregarNuevoComentario;

import com.issue_tracker.issue_tracker.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NuevoComentarioData {
    private String asunto;
    private String descripcion;
    private Usuario emisor;
    private Integer requerimientoId;
}
