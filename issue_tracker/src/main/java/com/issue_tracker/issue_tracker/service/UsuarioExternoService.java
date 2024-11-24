package com.issue_tracker.issue_tracker.service;

import org.springframework.stereotype.Service;

import com.issue_tracker.issue_tracker.exception.CustomException;
import com.issue_tracker.issue_tracker.model.UsuarioExterno;
import com.issue_tracker.issue_tracker.repository.UsuarioExternoRepository;

@Service
public class UsuarioExternoService {
    private final UsuarioExternoRepository repository;

    public UsuarioExternoService(UsuarioExternoRepository repository) {
        this.repository = repository;
    }
    
    public UsuarioExterno findByEmail(String email) {
        return repository.findByUsuarioEmail(email)
            .orElseThrow(() -> new CustomException("Usuario not found with email: " + email));
    }

    public UsuarioExterno findByCuil(String cuil) {
        return repository.findByCuil(cuil)
            .orElseThrow(() -> new CustomException("Usuario not found with CUIL: " + cuil));
    }

}