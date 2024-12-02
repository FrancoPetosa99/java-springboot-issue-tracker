package com.issue_tracker.issue_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Usuario getUsuarioById(Integer id) {
        
        Usuario user = usuarioRepository.findById(id).orElse(null);

        if (user == null) {
            // TODO
            // agregar logica para devolver un error
            return null;
        }

        return user;
    }
}