package com.issue_tracker.issue_tracker.dto.RecuperarListaEmpresas;

import com.issue_tracker.issue_tracker.model.Empresa;

public class EmpresaMapper {
    
    public EmpresaDTO mapEmpresaToDTO(Empresa empresa) {
        
        EmpresaDTO dto = new EmpresaDTO();

        dto.setNombre(empresa.getNombre());
        dto.setId(empresa.getId());

        return dto;
    }
}
