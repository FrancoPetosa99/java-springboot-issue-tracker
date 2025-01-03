package com.issue_tracker.issue_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.issue_tracker.issue_tracker.model.Evento;
import com.issue_tracker.issue_tracker.repository.EventoRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public Evento registrarEvento(Evento evento) {
        return eventoRepository.save(evento);
    }
    
}