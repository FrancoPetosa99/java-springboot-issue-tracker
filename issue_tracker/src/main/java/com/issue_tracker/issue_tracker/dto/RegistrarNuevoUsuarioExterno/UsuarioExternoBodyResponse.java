package com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioExternoBodyResponse {
    private String nombre;
    private String apellido;
    private String email;
    private String nombreUsuario;
    private boolean destacado;
    private String empresa;
}