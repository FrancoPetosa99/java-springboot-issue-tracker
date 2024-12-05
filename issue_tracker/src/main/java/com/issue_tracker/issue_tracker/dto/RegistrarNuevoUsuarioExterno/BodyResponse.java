package com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BodyResponse {
    private String nombre;
    private String apellido;
    private String email;
    private String nombreUsuario;
    private boolean descatado;
    private String empresa;
}