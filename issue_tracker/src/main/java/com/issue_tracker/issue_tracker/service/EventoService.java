package com.issue_tracker.issue_tracker.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.Builder.Evento.EventoBuilder;
import com.issue_tracker.issue_tracker.model.Evento;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.repository.EventoRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public Evento registrarEvento(Requerimiento requerimiento, Usuario emisor, String action) {

        Evento evento = new EventoBuilder()
        .buildAccion(action)
        .buildRequerimeiento(requerimiento)
        .buildUsuarioEmisor(emisor)
        .build();

        return eventoRepository.save(evento);
    }

    public List<Evento> getEventosByRequerimientoById(Integer requerimientoId) {
        return eventoRepository.findByRequerimientoId(requerimientoId);
    }
}