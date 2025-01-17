package com.issue_tracker.issue_tracker.dto.RecuperarTipoRequerimientos;

import com.issue_tracker.issue_tracker.model.TipoRequerimiento;

public class TipoRequerimientoMapper {
    
    public static TipoRequerimientoDTO mapTipoRequerimientoToDTO(TipoRequerimiento tipoRequerimiento) {

        TipoRequerimientoDTO dto = new TipoRequerimientoDTO(
            tipoRequerimiento.getId(),
            tipoRequerimiento.getCodigo(),
            tipoRequerimiento.getDescripcion()
        );

        return dto;
    }
}