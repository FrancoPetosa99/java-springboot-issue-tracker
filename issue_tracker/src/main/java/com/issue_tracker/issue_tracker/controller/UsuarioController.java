package com.issue_tracker.issue_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno.UsuarioExternoBodyResponse;
import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno.UsuarioExternoBodyRequest;
import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioInterno.UsuarioInternoBodyRequest;
import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioInterno.UsuarioInternoBodyResponse;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.mapper.RegistrarUsuarioExterno.UsuarioExternoMapper;
import com.issue_tracker.issue_tracker.mapper.RegistrarUsuarioInterno.UsuarioInternoMapper;
import com.issue_tracker.issue_tracker.model.Empresa;
import com.issue_tracker.issue_tracker.model.UsuarioExterno;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;
import com.issue_tracker.issue_tracker.response.HttpBodyResponse;
import com.issue_tracker.issue_tracker.response.ResponseFactory;
import com.issue_tracker.issue_tracker.service.EmpresaService;
import com.issue_tracker.issue_tracker.service.RegistrarUsuario.RegistrarUsuarioExternoService;
import com.issue_tracker.issue_tracker.service.RegistrarUsuario.RegistrarUsuarioInternoService;

public class UsuarioController {

    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private RegistrarUsuarioInternoService registrarUsuarioInternoService;

    @Autowired
    private RegistrarUsuarioExternoService registrarUsuarioExternoService;

    @PostMapping("/usuario/interno")
    public ResponseEntity<HttpBodyResponse> createNewUsuarioInterno(
        @RequestBody UsuarioInternoBodyRequest bodyRequest
    ) {
        
        try {

            String password = bodyRequest.getPassword();
            String confimPassword = bodyRequest.getConfirmPassword();
    
            if (!password.equals(confimPassword)) 
                throw new BadRequestException("Las contraseñas ingresadas no son iguales");

            UsuarioInterno usuario = UsuarioInternoMapper.mapRequestToUser(bodyRequest);

            UsuarioInterno nuevoUsuario = (UsuarioInterno) registrarUsuarioInternoService.registrarUsuario(usuario);

            UsuarioInternoBodyResponse bodyResonse = UsuarioInternoMapper.mapUsuarioToBodyResponse(nuevoUsuario);

            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(201)
            .message("Usuario registrado con exito")
            .data(bodyResonse)
            .build();
    
            return ResponseEntity
            .status(201)
            .body(response);

        }   catch(BadRequestException e) {
                return responseFactory.badRequest(e.getMessage());
        }   catch(NotFoundException e) {
                return responseFactory.errorNotFound(e.getMessage());
        }   catch(Exception e) {
                return responseFactory.internalServerError();
        }
    }

    @PostMapping("/usuario/externo")
    public ResponseEntity<HttpBodyResponse> createNewUsuarioExterno(
        @RequestBody UsuarioExternoBodyRequest bodyRequest
    ) {
        
        try {

            String password = bodyRequest.getPassword();
            String confimPassword = bodyRequest.getConfirmPassword();
    
            if (!password.equals(confimPassword)) 
                throw new BadRequestException("Las contraseñas ingresadas no son iguales");

            Integer empresaId = bodyRequest.getEmpresaId();
            Empresa empresa = empresaService.getEmpresaById(empresaId);

            UsuarioExterno usuario = UsuarioExternoMapper.mapRequestToUser(bodyRequest, empresa);

            UsuarioExterno usuarioRegistrado = (UsuarioExterno) registrarUsuarioExternoService.registrarUsuario(usuario);

            UsuarioExternoBodyResponse bodyResonse = UsuarioExternoMapper.mapUsuarioToBodyResponse(usuarioRegistrado);

            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(201)
            .message("Usuario registrado con exito")
            .data(bodyResonse)
            .build();
    
            return ResponseEntity
            .status(201)
            .body(response);

        }   catch(BadRequestException e) {
                return responseFactory.badRequest(e.getMessage());
        }   catch(NotFoundException e) {
                return responseFactory.errorNotFound(e.getMessage());
        }   catch(Exception e) {
                return responseFactory.internalServerError();
        }
    }
}