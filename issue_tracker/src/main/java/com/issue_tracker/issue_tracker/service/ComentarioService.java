package com.issue_tracker.issue_tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.repository.ComentarioRepository;

@Service
public class ComentarioService {
    
    @Autowired
    private ComentarioRepository comentarioRepository;

    public List<Comentario> getComentariosByRequerimientoId(Integer requerimientoId) {

        return comentarioRepository.findByRequerimientoId(requerimientoId);
    }
}