package com.issue_tracker.issue_tracker.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.model.CategoriaRequerimiento;
import com.issue_tracker.issue_tracker.repository.CategoriaRequerimientoRepository;

@Service
public class CategoriaRequerimientoService {

    @Autowired
    private CategoriaRequerimientoRepository categoriaRequerimientoRepository;
    
    public List<CategoriaRequerimiento> getCategoriasByTipoRequerimiento(Integer tipoRequerimientoId) {
        return categoriaRequerimientoRepository.findAllByTipoRequerimientoId(tipoRequerimientoId);
    }

    public CategoriaRequerimiento getCategoriaRequerimientoById(Integer categoriaRequerimientoId) 
    throws NotFoundException {

        CategoriaRequerimiento categoriaRequerimiento = categoriaRequerimientoRepository.findById(categoriaRequerimientoId).orElse(null);

        if(categoriaRequerimiento == null) throw new NotFoundException("No se ha encontrado Categoria Requerimiento con id: " + categoriaRequerimientoId);

        return categoriaRequerimiento;
    }
}