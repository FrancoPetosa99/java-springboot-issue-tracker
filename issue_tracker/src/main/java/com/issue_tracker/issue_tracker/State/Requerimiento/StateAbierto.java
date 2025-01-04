package com.issue_tracker.issue_tracker.State.Requerimiento;

import org.springframework.beans.factory.annotation.Autowired;

import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;

public class StateAbierto extends RequerimientoState {
    
    @Autowired
    private RequerimientoRepository requerimientoRepository;

    public StateAbierto(Requerimiento requerimiento) {
        super(requerimiento);
    }

    public void asignarNuevoPropietario(Usuario nuevoPropietario) {
        this.requerimiento.setUsuarioPropietario(nuevoPropietario);
        this.requerimiento.setEstado("Asignado");
        requerimientoRepository.save(this.requerimiento);
    }

    public void agregarComentario(Comentario comentario) 
    throws BadRequestException {
        throw new BadRequestException("No se admiten comentarios hasta que haya un Propietario asignado");
    }

    public void cerrarRequerimiento() 
    throws BadRequestException {
        throw new BadRequestException("Primero debe asignarse el requerimiento antes de cerrarse");        
    }
}