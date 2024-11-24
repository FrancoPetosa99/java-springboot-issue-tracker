package com.issue_tracker.issue_tracker.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.issue_tracker.issue_tracker.exception.CustomException;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.model.UsuarioExterno;
import com.issue_tracker.issue_tracker.repository.UsuarioExternoRepository;

import jakarta.persistence.EntityManager;

@Service
public class UsuarioExternoService {
    private final UsuarioExternoRepository repository;

    @Autowired
    private EntityManager entityManager;
    // or using repositories
    @Autowired
    private UsuarioExternoRepository usuarioExternoRepository;

    public UsuarioExternoService(UsuarioExternoRepository repository) {
        this.repository = repository;
    }
    
    public UsuarioExterno findByEmail(String email) {
        return repository.findByUsuarioEmail(email)
            .orElseThrow(() -> new CustomException("Usuario not found with email: " + email));
    }

    public UsuarioExterno findByCuil(Integer cuil) {
        return repository.findByCuil(cuil)
            .orElseThrow(() -> new CustomException("Usuario not found with CUIL: " + cuil));
    }

    // Method 1: Using EntityManager
    public void saveUsuarioExterno() {
        // Create base Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setApellido("Doe");
        usuario.setEmail("john@example.com");
        usuario.setNombreUsuario("johndoe");
        usuario.setHashedPassword("hashedpass123");
        usuario.setTipo("EXTERNO");
        usuario.setCreatedAt(LocalDateTime.now());

        // Create UsuarioExterno
        UsuarioExterno usuarioExterno = new UsuarioExterno();
        usuarioExterno.setCuil(12345678);
        usuarioExterno.setEmpresa("ACME Corp");
        usuarioExterno.setDescripcion("Contractor");
        usuarioExterno.setCreatedAt(LocalDateTime.now());
        
        // Set relationship
        usuarioExterno.setUsuario(usuario);
        usuario.setUsuarioExterno(usuarioExterno);

        // Persist
        entityManager.persist(usuario);
        entityManager.persist(usuarioExterno);
    }


}