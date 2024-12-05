package com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest;

import java.util.List;
import lombok.Data;

@Data
public class NewRequerimientoRequest {
    private String descripcion;
    private String asunto;
    private String prioridad;
    private int tipoRequerimientoId;
    private int categoriaRequerimientoId;
    private int usuarioPropietarioId;
    private int usuarioEmisorId;
    private List<ArchivoAdjuntoData> listaArchivos;
    private List<Integer> listaRequerimientosId;
}