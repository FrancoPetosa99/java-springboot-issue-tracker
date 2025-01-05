package com.issue_tracker.issue_tracker.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.repository.TipoRequerimientoRepository;

@Service
public class TipoRequerimientoService {

    @Autowired
    private TipoRequerimientoRepository tipoRequerimientoRepository;

    public List<TipoRequerimiento> getTipoRequerimientos() {
        return tipoRequerimientoRepository.findAll();
    }

    public TipoRequerimiento getTipoRequerimientoById(Integer tipoRequerimientoId) 
    throws NotFoundException {

        TipoRequerimiento tipoRequerimiento = tipoRequerimientoRepository.findById(tipoRequerimientoId).orElse(null);

        if(tipoRequerimiento == null) 
            throw new NotFoundException("No se ha encontrado Tipo Requerimiento con id: " + tipoRequerimientoId);

        return tipoRequerimiento;
    }
}