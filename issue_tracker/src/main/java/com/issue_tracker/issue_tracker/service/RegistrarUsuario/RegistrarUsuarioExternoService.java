package com.issue_tracker.issue_tracker.service.RegistrarUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.model.UsuarioExterno;
import com.issue_tracker.issue_tracker.repository.UsuarioExternoRepository;

@Service
public class RegistrarUsuarioExternoService extends RegistrarUsuarioService {

    @Autowired
    private UsuarioExternoRepository usuarioExternoRepository;

    protected void checkForDuplicates(Usuario usuario)
    throws BadRequestException {

        UsuarioExterno usuarioExterno = (UsuarioExterno) usuario;

        String email = usuarioExterno.getEmail();
        boolean existEmail = usuarioExternoRepository.existsByEmail(email);
       
        String cuil = usuarioExterno.getCuil();
        boolean existCUIL = usuarioExternoRepository.existsByCuil(cuil);
    
        if (existEmail || existCUIL) 
            throw new BadRequestException("Ya existe un usuario registrado con estos datos");

    }

    protected String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    protected Usuario saveUsuario(Usuario usuario) {
        UsuarioExterno usuarioExterno = (UsuarioExterno) usuario;
        return usuarioExternoRepository.save(usuarioExterno);
    }
}