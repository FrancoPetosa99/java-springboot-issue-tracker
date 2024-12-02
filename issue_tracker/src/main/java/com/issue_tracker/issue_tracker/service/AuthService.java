package com.issue_tracker.issue_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.jwt.JwtToken;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.model.UsuarioExterno;
import com.issue_tracker.issue_tracker.repository.UsuarioExternoRepository;
import com.issue_tracker.issue_tracker.repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioExternoRepository usuarioExternoRepository;

    public UsuarioExterno registerNewExternalUser(UsuarioExterno usuario) {
        
        UsuarioExterno existEmail = usuarioExternoRepository.findByEmail(usuario.getEmail());
        if (existEmail != null) {
            // TODO
            // agregar logica para devolver error
            return null;
        }

        UsuarioExterno existCUIL = usuarioExternoRepository.findByCuil(usuario.getCuil());
        if (existCUIL != null) {
            // TODO
            // agregar logica para devolver error
            return null;
        }

        usuarioExternoRepository.save(usuario);
        return usuario;
    }
    
    public String login(String email, String password) {

        Usuario user = usuarioRepository.findByEmail(email);
        if (user == null) {
            // TODO
            // agregar logica para devolver un error
            return null;
        }

        String storedPasssword = user.getHashedPassword();

        if (!BCrypt.checkpw(password, storedPasssword)) {
            // TODO
            // agregar logica para devolver error
            return null;
        }

        String token = JwtToken
        .generateToken()
        .addClaim("email", user.getEmail())
        .addClaim("nombre", user.getNombre())
        .addClaim("apellido", user.getApellido())
        .addClaim("tipo", user.getTipo())
        .addClaim("id", user.getId())
        .setSubject(user.getEmail())
        .setTimeHours(200) // establecer tiempo de expiración del token
        .build();

        return token;
    }
}