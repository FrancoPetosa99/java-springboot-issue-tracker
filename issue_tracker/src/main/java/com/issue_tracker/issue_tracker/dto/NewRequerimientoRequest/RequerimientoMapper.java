package com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest;

import java.util.ArrayList;
import java.util.List;

import com.issue_tracker.issue_tracker.model.CategoriaRequerimiento;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;

public class RequerimientoMapper {
    
    public static NewRequerimientoData mapBodyRequestToData(
        NewRequerimientoRequest request,
        UsuarioInterno propietario,
        Usuario emisor,
        TipoRequerimiento tipoRequerimiento,
        CategoriaRequerimiento categoriaRequerimiento,
        List<Requerimiento> requerimientosRelacionados
    ) {

        NewRequerimientoData data = new NewRequerimientoData();
        
        data.setAsunto(request.getAsunto());
        data.setDescripcion(request.getDescripcion());
        data.setPrioridad(request.getPrioridad());
        data.setTipoRequerimiento(tipoRequerimiento);
        data.setCategoriaRequerimiento(categoriaRequerimiento);
        data.setUsuarioEmisor(emisor);
        data.setUsuarioPropietario(propietario);
        data.setListaArchivos(request.getListaArchivos());  
        data.setListaRequerimientos(requerimientosRelacionados);
        
        return data;
    }

    public static RequerimientoResponse mapRequerimientoToResonse(Requerimiento requerimiento) {
        RequerimientoResponse dto = new RequerimientoResponse();
        dto.setId(requerimiento.getId());
        dto.setCodigo(requerimiento.getCodigo());
        dto.setAsunto(requerimiento.getAsunto());
        dto.setDescripcion(requerimiento.getDescripcion());
        dto.setPrioridad(requerimiento.getPrioridad());
        TipoRequerimiento tipo = requerimiento.getTipoRequerimiento();
        dto.setTipoRequerimiento(tipo.getCodigo());
        CategoriaRequerimiento categoria = requerimiento.getCategoriaRequerimiento();
        dto.setCategoriaRequerimiento(categoria.getDescripcion());
        return dto;
    }
}