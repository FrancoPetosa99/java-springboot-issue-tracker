package com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno;

import com.issue_tracker.issue_tracker.model.Empresa;
import com.issue_tracker.issue_tracker.model.UsuarioExterno;

public class UsuarioMapper {

    public UsuarioExternoData mapRequestToData(UsuarioExternoRequest request, Empresa empresa) {
        
        UsuarioExternoData data = new UsuarioExternoData();

        data.setNombre(request.getNombre());
        data.setApellido(request.getApellido());
        data.setEmail(request.getEmail());
        data.setNombreUsuario(request.getNombreUsuario());
        data.setDescripcion(request.getDescripcion());
        data.setCuil(request.getCuil());
        data.setPassword(request.getPassword());
        data.setDestacadado(request.getDestacadado());
        data.setEmpresa(empresa);
        
        return data;
    }

    public BodyResponse mapUsuarioExternoToResponse(UsuarioExterno usuario) {

        BodyResponse dto = new BodyResponse();

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
