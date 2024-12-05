package com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno;

import com.issue_tracker.issue_tracker.model.Empresa;

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
}
