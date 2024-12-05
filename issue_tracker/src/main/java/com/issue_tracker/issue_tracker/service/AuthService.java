package com.issue_tracker.issue_tracker.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.issue_tracker.issue_tracker.dto.RegistrarNuevoUsuarioExterno.UsuarioExternoData;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
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

    public UsuarioExterno registerNewExternalUser(UsuarioExternoData data) 
    throws BadRequestException {

        String email = data.getEmail();
        UsuarioExterno existEmail = usuarioExternoRepository.findByEmail(email);
        if (existEmail != null) throw new BadRequestException("Ya existe un usuario registrado estos datos");

        String cuil = data.getCuil();
        UsuarioExterno existCUIL = usuarioExternoRepository.findByCuil(cuil);
        if (existCUIL != null) throw new BadRequestException("Ya existe un usuario registrado estos datos");

        String password = data.getPassword();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        UsuarioExterno usuario = new UsuarioExterno();

        usuario.setNombre(data.getNombre());
        usuario.setApellido(data.getApellido());
        usuario.setEmail(email);
        usuario.setNombreUsuario(data.getNombreUsuario());
        usuario.setHashedPassword(hashedPassword);
        usuario.setCuil(cuil);
        usuario.setEmpresa(data.getEmpresa());
        usuario.setDescripcion(data.getDescripcion());
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());
        
        usuarioExternoRepository.save(usuario);

        return usuario;
    }
    
    public String login(String email, String password) 
    throws BadRequestException {

        Usuario user = usuarioRepository.findByEmail(email);
        if (user == null) throw new BadRequestException("Email o Contraseña invalidos");

        String storedPasssword = user.getHashedPassword();
        if (!BCrypt.checkpw(password, storedPasssword)) throw new BadRequestException("Email o Contraseña invalidos");

        String token = JwtToken
        .generateToken()
        .addClaim("email", user.getEmail())
        .addClaim("nombre", user.getNombre())
        .addClaim("apellido", user.getApellido())
        .addClaim("tipo", user.getTipo())
        .addClaim("id", user.getId())
        .setSubject(user.getEmail())
        .setTimeHours(200)
        .build();

        return token;
    }
}