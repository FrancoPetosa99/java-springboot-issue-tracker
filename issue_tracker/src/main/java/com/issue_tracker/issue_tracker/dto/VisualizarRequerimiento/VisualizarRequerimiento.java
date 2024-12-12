package com.issue_tracker.issue_tracker.dto.VisualizarRequerimiento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisualizarRequerimiento {
    private Integer id;
    private String codigo;
    private String descripcion;
    private String asunto;
    private String prioridad;
    private String estado;
    private String tipoRequerimiento;
    private String categoriaRequerimiento;
    private String usuarioPropietario;
    private LocalDateTime createdAt;
    private List<DetalleRequerimiento> requerimientosRelacionados = new ArrayList<>();
    private List<DetalleArchivo> listaArchivosAdjuntos = new ArrayList<>();
}