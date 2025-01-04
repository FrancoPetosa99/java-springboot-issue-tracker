package com.issue_tracker.issue_tracker.service.ComentarRequerimiento;

import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.State.Requerimiento.RequerimientoHandler;
import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;

@Service
public class ComentarRequerimientoService {
    
    public void comentarRequerimiento(Comentario comentario, Requerimiento requerimiento) 
    throws Exception {

        RequerimientoHandler handler = new RequerimientoHandler(requerimiento);
        
        handler.agregarComentario(comentario);
    }
}