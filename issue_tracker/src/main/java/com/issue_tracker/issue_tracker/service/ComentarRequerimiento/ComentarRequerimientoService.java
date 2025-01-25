package com.issue_tracker.issue_tracker.service.ComentarRequerimiento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.repository.ComentarioRepository;

@Service
public class ComentarRequerimientoService {

    @Autowired
    private ComentarioRepository comentarioRepository;
    
    public void comentarRequerimiento(Comentario comentario, Requerimiento requerimiento) 
    throws Exception {

        RequerimientoState stateContext = StateFactory.createRequerimientoState(requerimiento, comentarioRepository);
        stateContext.agregarComentario(comentario);
    }
}