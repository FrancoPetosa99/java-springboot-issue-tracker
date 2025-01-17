package com.issue_tracker.issue_tracker.dto.RecuperarCategoriasRequerimiento;

import com.issue_tracker.issue_tracker.model.CategoriaRequerimiento;

public class CategoriaMapper {
    
    public static CategoriaDTO mapCategoriaToDTO(CategoriaRequerimiento categoria) {

        CategoriaDTO dto = new CategoriaDTO(categoria.getId(), categoria.getDescripcion());
        return dto;
    }
}
