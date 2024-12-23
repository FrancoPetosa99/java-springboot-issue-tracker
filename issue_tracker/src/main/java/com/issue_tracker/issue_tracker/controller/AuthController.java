package com.issue_tracker.issue_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.issue_tracker.issue_tracker.dto.LoginRequest;
import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno.BodyResponse;
import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno.UsuarioExternoData;
import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno.UsuarioExternoRequest;
import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno.UsuarioMapper;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.model.Empresa;
import com.issue_tracker.issue_tracker.model.UsuarioExterno;
import com.issue_tracker.issue_tracker.response.HttpBodyResponse;
import com.issue_tracker.issue_tracker.response.ResponseFactory;
import com.issue_tracker.issue_tracker.service.AuthService;
import com.issue_tracker.issue_tracker.service.EmpresaService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/ping")
    public ResponseEntity<HttpBodyResponse> checkHealth() {

        HttpBodyResponse response = new HttpBodyResponse
        .Builder()
        .status("Success")
        .statusCode(200)
        .message("API says: Hello!" )
        .build();

        return ResponseEntity
        .status(200)
        .body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpBodyResponse> createNewUsuarioExterno(
        @RequestBody UsuarioExternoRequest bodyRequest
    ) {
        
        try {

            String password = bodyRequest.getPassword();
            String confimPassword = bodyRequest.getConfirmPassword();
    
            if (!password.equals(confimPassword)) throw new BadRequestException("Las contraseñas ingresadas no son iguales");

            Integer empresaId = bodyRequest.getEmpresaId();
            Empresa empresa = empresaService.getEmpresaById(empresaId);

            UsuarioMapper mapper = new UsuarioMapper();

            UsuarioExternoData data = mapper.mapRequestToData(bodyRequest, empresa);

            UsuarioExterno nuevoUsuario = authService.registerNewExternalUser(data);

            BodyResponse bodyResonse = mapper.mapUsuarioExternoToResponse(nuevoUsuario);

            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(201)
            .message("Usuario registeado con exito")
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

    @PostMapping("/login")
    public ResponseEntity<HttpBodyResponse> login(
        @RequestBody LoginRequest credentials
    ) {
        
        try {
            
            String email = credentials.getEmail();
            String password = credentials.getPassword();
            
            String authToken = authService.login(email, password);
            
            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(200)
            .message("Usuario autenticado con exito")
            .data(authToken)
            .build();
    
            return ResponseEntity
            .status(200)
            .body(response);

        }   catch (BadRequestException e) {
                return responseFactory.badRequest(e.getMessage());
        }   catch (Exception e) {
                return responseFactory.internalServerError();
        }
    }
}