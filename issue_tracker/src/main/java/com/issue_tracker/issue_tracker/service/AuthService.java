package com.issue_tracker.issue_tracker.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // @Autowired
    // private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioExternoService usuarioExternoService;

}
