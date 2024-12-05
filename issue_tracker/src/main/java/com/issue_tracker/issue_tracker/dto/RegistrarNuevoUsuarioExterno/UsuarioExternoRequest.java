package com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioExternoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
    private String confirmPassword;

    @NotBlank(message = "El CUIL es obligatorio")
    private String cuil;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    private Boolean destacadado;

    @NotBlank(message = "La empresa es obligatoria")
    private Integer empresaId;
}