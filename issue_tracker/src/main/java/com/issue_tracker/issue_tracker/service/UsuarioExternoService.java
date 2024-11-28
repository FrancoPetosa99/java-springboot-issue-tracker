package com.issue_tracker.issue_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.repository.UsuarioExternoRepository;

@Service
public class UsuarioExternoService {

    @Autowired
    private UsuarioExternoRepository repository;

}