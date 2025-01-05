package com.issue_tracker.issue_tracker.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.model.Empresa;
import com.issue_tracker.issue_tracker.repository.EmpresaRepository;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public Empresa getEmpresaById(Integer empresaId)
    throws NotFoundException {
        
        Empresa empresa = empresaRepository.findById(empresaId).orElse(null);

        if (empresa == null) 
            throw new NotFoundException("No se ha encontrado empresa con id: " + empresaId);
            
        return empresa;
    }

    public List<Empresa> getEmpresas() {
        return empresaRepository.findAllByOrderByNombreAsc();
    } 
}