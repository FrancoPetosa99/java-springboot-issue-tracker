package com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest;

import java.util.List;
import com.issue_tracker.issue_tracker.model.ArchivoAdjunto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewRequerimientoRequest {

    @NotBlank(message = "La Descripcion es obligatoria")
    private String descripcion;

    @NotBlank(message = "El Asunto es obligatorio")
    private String asunto;

    @NotBlank(message = "La Prioridad es obligatoria")
    private String prioridad;

    @NotBlank(message = "El Tipo de Requerimiento es obligatorio")
    private int tipoRequerimientoId;

    @NotBlank(message = "La categoria es obligatoria")
    private int categoriaRequerimientoId;

    private int usuarioPropietarioId;
    private List<ArchivoAdjunto> listaArchivos;
    private List<Integer> listaRequerimientosId;
}