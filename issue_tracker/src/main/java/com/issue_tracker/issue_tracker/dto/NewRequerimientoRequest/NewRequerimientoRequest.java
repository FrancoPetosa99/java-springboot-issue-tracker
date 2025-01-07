package com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewRequerimientoRequest {

    @NotBlank(message = "La Descripcion es obligatoria")
    private String descripcion;

    @NotBlank(message = "El Asunto es obligatorio")
    private String asunto;

    @NotBlank(message = "La Prioridad es obligatoria")
    private String prioridad;

    @NotNull(message = "El Tipo de Requerimiento es obligatorio")
    private int tipoRequerimientoId;

    @NotNull(message = "La categoria es obligatoria")
    private int categoriaRequerimientoId;

    private int usuarioPropietarioId;

    private List<ArchivoAdjuntoData> listaArchivos;

    private List<Integer> listaRequerimientosId;
}