package com.issue_tracker.issue_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.jwt.JwtToken;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;
 
    public Usuario login(String email, String password) 
    throws BadRequestException {

        Usuario user = usuarioRepository.findByEmail(email);
        if (user == null) 
            throw new BadRequestException("Email o Contraseña invalidos");

        String storedPasssword = user.getHashedPassword();
        if (!BCrypt.checkpw(password, storedPasssword)) 
            throw new BadRequestException("Email o Contraseña invalidos");

        return user;
    }

    public String generateToken(Usuario user) {

        String token = JwtToken
        .generateToken()
        .addClaim("email", user.getEmail())
        .addClaim("nombre", user.getNombre())
        .addClaim("apellido", user.getApellido())
        .addClaim("tipo", user.getTipo())
        .addClaim("nombreUsuario", user.getNombreUsuario())
        .addClaim("id", user.getId())
        .setSubject(user.getEmail())
        .setTimeHours(200)
        .build();

        return token;
    }
}