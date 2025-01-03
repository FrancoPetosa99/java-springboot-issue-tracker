package com.issue_tracker.issue_tracker.mapper.RegistrarUsuarioExterno;

import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno.UsuarioExternoBodyResponse;
import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno.UsuarioExternoBodyRequest;
import com.issue_tracker.issue_tracker.model.Empresa;
import com.issue_tracker.issue_tracker.model.UsuarioExterno;

public class UsuarioExternoMapper {
    
    public static UsuarioExterno mapRequestToUser(UsuarioExternoBodyRequest body, Empresa empresa) {

        UsuarioExterno usuario = new UsuarioExterno();

        usuario.setNombre(body.getNombre());
        usuario.setApellido(body.getApellido());
        usuario.setEmail(body.getEmail());
        usuario.setNombreUsuario(body.getNombreUsuario());
        usuario.setDescripcion(body.getDescripcion());
        usuario.setCuil(body.getCuil());
        usuario.setHashedPassword(body.getPassword());
        usuario.setDestacado(body.getDestacadado());
        usuario.setEmpresa(empresa);
        
        return usuario;
    }

    public static UsuarioExternoBodyResponse mapUsuarioToBodyResponse(UsuarioExterno usuario) {

        UsuarioExternoBodyResponse dto = new UsuarioExternoBodyResponse();

        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setDescatado(false);
        Empresa empresa = usuario.getEmpresa();
        String nombreEmpresa = empresa.getNombre();
        dto.setEmpresa(nombreEmpresa);

        return dto;
    }
}