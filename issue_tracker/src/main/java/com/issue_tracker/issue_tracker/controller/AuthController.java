package com.issue_tracker.issue_tracker.controller;


import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issue_tracker.issue_tracker.dto.NewUsuarioExternoRequestDTO;
import com.issue_tracker.issue_tracker.exception.CustomException;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.model.UsuarioExterno;
import com.issue_tracker.issue_tracker.repository.UsuarioExternoRepository;
import com.issue_tracker.issue_tracker.response.HttpBodyResponse;
import com.issue_tracker.issue_tracker.service.UsuarioExternoService;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;;

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


    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @PostMapping("/register")
    public ResponseEntity<HttpBodyResponse> createNewUsuarioExterno(
        @RequestBody @Valid NewUsuarioExternoRequestDTO usuarioExternoRequestDTO
    ) throws IOException {

    
        if (usuarioExternoRepository.findByUsuarioEmail(usuarioExternoRequestDTO.getEmail()).isPresent()) {
            throw new CustomException("Email already registered");
        }
        
        if (usuarioExternoRepository.findByCuil(usuarioExternoRequestDTO.getCuil()).isPresent()) {
            throw new CustomException("CUIL already registered");
        }

        // 2. Create new Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioExternoRequestDTO.getNombre());
        usuario.setApellido(usuarioExternoRequestDTO.getApellido());
        usuario.setEmail(usuarioExternoRequestDTO.getEmail());
        usuario.setNombreUsuario(usuarioExternoRequestDTO.getNombreUsuario());
        usuario.setHashedPassword(hashPassword(usuarioExternoRequestDTO.getPassword())); // Implement password hashing
        usuario.setTipo("EXTERNO");
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());

        // 3. Create new UsuarioExterno
        UsuarioExterno usuarioExterno = new UsuarioExterno();
        usuarioExterno.setUsuario(usuario);
        usuarioExterno.setCuil(usuarioExternoRequestDTO.getCuil());
        usuarioExterno.setDescripcion(usuarioExternoRequestDTO.getDescripcion());
        usuarioExterno.setDestacado(usuarioExternoRequestDTO.getDestacado());
        usuarioExterno.setEmpresa(usuarioExternoRequestDTO.getEmpresa());
        usuarioExterno.setCreatedAt(LocalDateTime.now());
        usuarioExterno.setUpdatedAt(LocalDateTime.now());

        // 4. Save to database
        usuarioExternoRepository.save(usuarioExterno);

        // 5. Build response
        HttpBodyResponse response = new HttpBodyResponse
                .Builder()
                .status("Success")
                .statusCode(HttpStatus.CREATED.value())
                .message("User registered successfully: " + usuarioExternoRequestDTO.getNombre())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    

}