package com.issue_tracker.issue_tracker.dto;

import java.util.List;
import lombok.Data;

@Data
public class NewRequerimientoRequest {
    private String descripcion;
    private String asunto;
    private String prioridad;
    private int tipoRequerimientoId;
    private int usuarioPropietarioId;
    private int usuarioEmisorId;
    private List<String> listaArchivos;
    private List<Integer> listaRequerimientosId;
}