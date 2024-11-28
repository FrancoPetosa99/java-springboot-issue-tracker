package com.issue_tracker.issue_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Constructor sin parámetros
@AllArgsConstructor // Constructor con todos los parámetros
public class NewUsuarioExternoRequest {
    private String nombre;
    private String apellido;
    private String email;
    private String nombreUsuario;
    private String password;
    private String confirmPassword;
    private String cuil;
    private String descripcion;
    private String destacado;
    private String empresa;
}