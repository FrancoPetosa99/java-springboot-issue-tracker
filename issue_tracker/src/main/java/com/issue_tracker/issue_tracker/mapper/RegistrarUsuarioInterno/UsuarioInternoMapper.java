package com.issue_tracker.issue_tracker.mapper.RegistrarUsuarioInterno;

import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioInterno.UsuarioInternoBodyRequest;
import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioInterno.UsuarioInternoBodyResponse;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;

public class UsuarioInternoMapper {
    
    public static UsuarioInterno mapRequestToUser(UsuarioInternoBodyRequest body) {

        UsuarioInterno usuario = new UsuarioInterno();

        usuario.setNombre(body.getNombre());
        usuario.setApellido(body.getApellido());
        usuario.setEmail(body.getEmail());
        usuario.setNombreUsuario(body.getNombreUsuario());
        usuario.setHashedPassword(body.getPassword());

        return usuario;
    }

    public static UsuarioInternoBodyResponse mapUsuarioToBodyResponse(UsuarioInterno usuario) {

        UsuarioInternoBodyResponse dto = new UsuarioInternoBodyResponse();

        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
       
        return dto;
    }
}