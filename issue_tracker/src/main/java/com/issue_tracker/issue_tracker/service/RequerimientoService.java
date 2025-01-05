package com.issue_tracker.issue_tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;

@Service
public class RequerimientoService {

    @Autowired
    private RequerimientoRepository requerimientoRepository;

    public Requerimiento getRequerimientoById(Integer requerimientoId) 
    throws NotFoundException {

        Requerimiento requerimiento = requerimientoRepository.findById(requerimientoId).orElse(null);

        if (requerimiento == null) 
            throw new NotFoundException("No se ha encontrado Requerimiento con id: " + requerimientoId);

        return requerimiento;
    }
}