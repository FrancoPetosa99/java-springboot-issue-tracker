package com.issue_tracker.issue_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.issue_tracker.issue_tracker.jwt.JwtToken;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.model.UsuarioExterno;
import com.issue_tracker.issue_tracker.repository.UsuarioExternoRepository;

@Service
public class AuthService {

    @Autowired
    private UsuarioExternoRepository userRepository;

    public UsuarioExterno registerNewUser(UsuarioExterno usuario) {
        
        // verify does not exist another user with same email
        Usuario existEmail = userRepository.findByEmail(usuario.getEmail());
        if (existEmail != null) return null;

        // verify does not exist another user with same CUIL
        Usuario existCUIL = userRepository.findByCuil(usuario.getCuil());
        if (existCUIL != null) return null;

        userRepository.save(usuario);
        return usuario;
    }
    
    public String login(String email, String password) {

        // check if user with passed in email exists
        Usuario user = userRepository.findByEmail(email);
        if (user == null) return null;

        String storedPasssword = user.getHashedPassword();
        // String paramPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // check if passwords are matching
        if (!BCrypt.checkpw(password, storedPasssword)) return null;

        // create new jwt token
        String token = JwtToken
        .generateToken()
        .addClaim("email", user.getEmail())
        .addClaim("nombre", user.getNombre())
        .addClaim("apellido", user.getApellido())
        .addClaim("id", user.getId())
        .setSubject(user.getEmail())
        .setTimeHours(200) // establecer tiempo de expiración del token
        .build();

        return token;
    }
}
