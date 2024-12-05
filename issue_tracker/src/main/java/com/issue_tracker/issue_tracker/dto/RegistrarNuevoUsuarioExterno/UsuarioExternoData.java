package com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno;

import com.issue_tracker.issue_tracker.model.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioExternoData {
    private String nombre;
    private String apellido;
    private String email;
    private String nombreUsuario;
    private String password;
    private String cuil;
    private String descripcion;
    private Boolean destacadado;
    private Empresa empresa;
}