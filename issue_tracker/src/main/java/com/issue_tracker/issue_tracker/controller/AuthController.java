package com.issue_tracker.issue_tracker.controller;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issue_tracker.issue_tracker.dto.LoginRequest;
import com.issue_tracker.issue_tracker.dto.NewUsuarioExternoRequest;
import com.issue_tracker.issue_tracker.model.UsuarioExterno;
import com.issue_tracker.issue_tracker.response.HttpBodyResponse;
import com.issue_tracker.issue_tracker.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/ping")
    public ResponseEntity<HttpBodyResponse> checkHealth(){
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


    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @PostMapping("/register")
    public ResponseEntity<HttpBodyResponse> createNewUsuarioExterno(
        @RequestBody NewUsuarioExternoRequest usuarioExternoRequest) {

        // create new Usuario
        UsuarioExterno usuario = new UsuarioExterno();
        usuario.setNombre(usuarioExternoRequest.getNombre());
        usuario.setApellido(usuarioExternoRequest.getApellido());
        usuario.setEmail(usuarioExternoRequest.getEmail());
        usuario.setNombreUsuario(usuarioExternoRequest.getNombreUsuario());
        usuario.setHashedPassword(hashPassword(usuarioExternoRequest.getPassword()));
        usuario.setCuil(usuarioExternoRequest.getCuil());
        usuario.setEmpresa(usuarioExternoRequest.getEmpresa());
        usuario.setDescripcion(usuarioExternoRequest.getDescripcion());
        usuario.setDestacado(usuarioExternoRequest.getDestacado());
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());

        // register new user
        UsuarioExterno nuevoUsuario = authService.registerNewUser(usuario);

        // build response
        HttpBodyResponse response = new HttpBodyResponse
        .Builder()
        .status("Success")
        .statusCode(201)
        .message("User registered successfully: " + nuevoUsuario.getNombreUsuario())
        .build();

        // return response to client
        return ResponseEntity
        .status(201)
        .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<HttpBodyResponse> login(
        @RequestBody LoginRequest credentials) {
        
        String email = credentials.getEmail();
        String password = credentials.getPassword();
        String authToken = authService.login(email, password);
        
        // build response
        HttpBodyResponse response = new HttpBodyResponse
        .Builder()
        .status("Success")
        .statusCode(200)
        .message("User logged in successfully")
        .data(authToken)
        .build();

        // return response to client
        return ResponseEntity
        .status(200)
        .body(response);
    }
}