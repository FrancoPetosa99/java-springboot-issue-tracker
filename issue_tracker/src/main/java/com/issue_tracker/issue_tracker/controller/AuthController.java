package com.issue_tracker.issue_tracker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issue_tracker.issue_tracker.model.UsuarioExterno;
import com.issue_tracker.issue_tracker.repository.UsuarioExternoRepository;
import com.issue_tracker.issue_tracker.response.HttpBodyResponse;
import com.issue_tracker.issue_tracker.service.UsuarioExternoService;

import jakarta.persistence.EntityManager;


@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private EntityManager entityManager;
    // or using repositories
    @Autowired
    private UsuarioExternoRepository usuarioExternoRepository;

    private final UsuarioExternoService usuarioExternoService;

    public AuthController(UsuarioExternoService usuarioExternoService) {
        this.usuarioExternoService = usuarioExternoService;
    }

    @GetMapping("/ping")
    public ResponseEntity<HttpBodyResponse> checkHealth(){
        HttpBodyResponse response = new HttpBodyResponse
                .Builder()
                .status("Success")
                .statusCode(200)
                .message("Holaa " )
                .build();

        return ResponseEntity
                .status(200)
                .body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpBodyResponse> register(@RequestBody RegistrationRequest request) {
        try {
            // Validate request
            if (request.getEmail() == null || request.getPassword() == null) {
                return ResponseEntity
                    .status(400)
                    .body(new HttpBodyResponse.Builder()
                        .status("Error")
                        .statusCode(400)
                        .message("Email and password are required")
                        .build());
            }

            // Check if user exists
            if (usuarioExternoRepository.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity
                    .status(409)
                    .body(new HttpBodyResponse.Builder()
                        .status("Error")
                        .statusCode(409)
                        .message("Email already registered")
                        .build());
            }

            // Create new user
            UsuarioExterno newUser = new UsuarioExterno();
            newUser.setEmail(request.getEmail());
            newUser.setPassword(passwordEncoder.encode(request.getPassword())); // Remember to inject PasswordEncoder
            newUser.setNombre(request.getNombre());
            newUser.setApellido(request.getApellido());
            
            usuarioExternoRepository.save(newUser);

            return ResponseEntity
                .status(201)
                .body(new HttpBodyResponse.Builder()
                    .status("Success")
                    .statusCode(201)
                    .message("User registered successfully")
                    .data(newUser)
                    .build());

        } catch (Exception e) {
            return ResponseEntity
                .status(500)
                .body(new HttpBodyResponse.Builder()
                    .status("Error")
                    .statusCode(500)
                    .message("Registration failed: " + e.getMessage())
                    .build());
        }
    }

    

}