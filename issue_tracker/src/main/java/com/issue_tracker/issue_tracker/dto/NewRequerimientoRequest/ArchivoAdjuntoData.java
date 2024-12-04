package com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArchivoAdjuntoData {
    private String nombre;
    private String extension;
    private String contenido;
}