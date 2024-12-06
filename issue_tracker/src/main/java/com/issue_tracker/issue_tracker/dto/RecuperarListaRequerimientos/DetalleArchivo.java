package com.issue_tracker.issue_tracker.dto.RecuperarListaRequerimientos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleArchivo {
    private Integer id;
    private String nombre;
    private String extension;
}