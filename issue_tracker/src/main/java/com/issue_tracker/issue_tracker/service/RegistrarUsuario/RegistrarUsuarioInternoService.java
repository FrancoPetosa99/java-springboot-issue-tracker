package com.issue_tracker.issue_tracker.service.RegistrarUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;
import com.issue_tracker.issue_tracker.repository.UsuarioInternoRepository;


@Service
public class RegistrarUsuarioInternoService extends RegistrarUsuarioService {

    @Autowired
    private UsuarioInternoRepository usuarioInternoRepository;

    protected void checkForDuplicates(Usuario usuario)
    throws BadRequestException {

        UsuarioInterno usuarioInterno = (UsuarioInterno) usuario;

        String email = usuarioInterno.getEmail();
        boolean existEmail = usuarioInternoRepository.existsByEmail(email);
    
        Integer legajo = usuarioInterno.getLegajo();
        boolean existLegajo = usuarioInternoRepository.existsByLegajo(legajo);

        if (existEmail || existLegajo) 
            throw new BadRequestException("Ya existe un usuario registrado con estos datos");
    }

    protected String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    protected Usuario saveUsuario(Usuario usuario) {
        UsuarioInterno usuarioInterno = (UsuarioInterno) usuario;
        return usuarioInternoRepository.save(usuarioInterno);
    }
}