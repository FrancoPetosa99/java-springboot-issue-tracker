package com.issue_tracker.issue_tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno.UsuarioExternoBodyRequest;
import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno.UsuarioExternoBodyResponse;
import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioInterno.UsuarioInternoBodyRequest;
import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioInterno.UsuarioInternoBodyResponse;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.mapper.RegistrarUsuarioExterno.UsuarioExternoMapper;
import com.issue_tracker.issue_tracker.mapper.RegistrarUsuarioInterno.UsuarioInternoMapper;
import com.issue_tracker.issue_tracker.model.Empresa;
import com.issue_tracker.issue_tracker.model.UsuarioExterno;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;
import com.issue_tracker.issue_tracker.repository.UsuarioExternoRepository;
import com.issue_tracker.issue_tracker.repository.UsuarioInternoRepository;
import com.issue_tracker.issue_tracker.response.HttpBodyResponse;
import com.issue_tracker.issue_tracker.response.ResponseFactory;
import com.issue_tracker.issue_tracker.service.EmpresaService;
import com.issue_tracker.issue_tracker.service.RegistrarUsuario.RegistrarUsuarioExternoService;
import com.issue_tracker.issue_tracker.service.RegistrarUsuario.RegistrarUsuarioInternoService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private RegistrarUsuarioInternoService registrarUsuarioInternoService;

    @Autowired
    private RegistrarUsuarioExternoService registrarUsuarioExternoService;

    @Autowired
    private UsuarioExternoRepository usuarioExternoRepository;

    @Autowired
    private UsuarioInternoRepository usuarioInternoRepository;

    @GetMapping("/externo")
    public ResponseEntity<HttpBodyResponse> getUsuariosExternos() {

        try {

            List<UsuarioExterno> usuarios = usuarioExternoRepository.findAll();
            
            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(201)
            .message("Se han encontrado los usuarios externos")
            .data(usuarios)
            .build();
    
            return ResponseEntity
            .status(200)
            .body(response);

        } catch (Exception e) {
            return ResponseFactory.internalServerError();
        }
    }

    @GetMapping("/interno")
    public ResponseEntity<HttpBodyResponse> getUsuariosInternos() {

        try {

            List<UsuarioInterno> usuarios = usuarioInternoRepository.findAll();
            
            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(201)
            .message("Se han encontrado los usuarios internos")
            .data(usuarios)
            .build();
    
            return ResponseEntity
            .status(200)
            .body(response);

        } catch (Exception e) {
            return ResponseFactory.internalServerError();
        }
    }

    @PostMapping("/interno")
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
                return ResponseFactory.badRequest(e.getMessage());
        }   catch(NotFoundException e) {
                return ResponseFactory.errorNotFound(e.getMessage());
        }   catch(Exception e) {
                return ResponseFactory.internalServerError();
        }
    }

    @PostMapping("/externo")
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
                return ResponseFactory.badRequest(e.getMessage());
        }   catch(NotFoundException e) {
                return ResponseFactory.errorNotFound(e.getMessage());
        }   catch(Exception e) {
                return ResponseFactory.internalServerError();
        }
    }
}