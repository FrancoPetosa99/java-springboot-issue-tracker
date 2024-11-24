package com.issue_tracker.issue_tracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUsuarioExternoRequestDTO {
    @NotNull
    private String nombre;
    
    @NotNull
    private String apellido;
    
    @NotNull
    @Email
    private String email;
    
    @NotNull
    private String nombreUsuario;
    
    @NotNull
    private String password;
    
    @NotNull
    private String cuil;
    
    private String descripcion;
    private String destacado;
    private String empresa;
}