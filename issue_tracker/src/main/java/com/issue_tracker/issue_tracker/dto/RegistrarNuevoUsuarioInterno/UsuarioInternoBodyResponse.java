package com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioInterno;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioInternoBodyResponse {
    private String nombre;
    private String apellido;
    private String email;
    private String nombreUsuario;
}