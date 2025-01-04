package com.issue_tracker.issue_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Usuario getUsuarioById(Integer id) 
    throws NotFoundException {
        
        Usuario user = usuarioRepository.findById(id).orElse(null);

        if (user == null) 
            throw new NotFoundException("No se ha encontrado usuario con id: " + id);

        return user;
    }
}