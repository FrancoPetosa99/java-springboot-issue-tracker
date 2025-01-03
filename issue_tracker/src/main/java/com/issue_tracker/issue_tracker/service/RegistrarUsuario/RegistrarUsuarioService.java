package com.issue_tracker.issue_tracker.service.RegistrarUsuario;

import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.model.Usuario;

public abstract class RegistrarUsuarioService {

    public Usuario registrarUsuario(Usuario usuario) 
    throws Exception {

        this.checkForDuplicates(usuario);

        String password = usuario.getHashedPassword();
        String hashedPassword = this.hashPassword(password);
        usuario.setHashedPassword(hashedPassword);

        return this.saveUsuario(usuario);
    }

    protected abstract void checkForDuplicates(Usuario usuario) throws BadRequestException;
    protected abstract String hashPassword(String password);
    protected abstract Usuario saveUsuario(Usuario usuario);
}