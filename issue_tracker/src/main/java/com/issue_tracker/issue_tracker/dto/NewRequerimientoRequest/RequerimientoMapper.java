package com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest;

import java.util.ArrayList;
import java.util.List;

import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;

public class RequerimientoMapper {
    
    public NewRequerimientoData mapBodyRequestToData(
        NewRequerimientoRequest request,
        Usuario propietario,
        Usuario emisor,
        TipoRequerimiento tipoRequerimiento,
        List<Requerimiento> requerimientosRelacionados,
        String tipoUsuario
    ) {

        NewRequerimientoData data = new NewRequerimientoData();
        
        data.setAsunto(request.getAsunto());
        data.setDescripcion(request.getDescripcion());
        data.setPrioridad(request.getPrioridad());
        data.setTipoRequerimiento(tipoRequerimiento);
        data.setUsuarioEmisor(emisor);
        data.setUsuarioPropietario(propietario);
        
        data.setListaArchivos(new ArrayList<ArchivoAdjuntoData>());
        List<ArchivoAdjuntoData> listaArchivos = request.getListaArchivos();
        if (listaArchivos != null) data.setListaArchivos(listaArchivos);

        data.setListaRequerimientos(new ArrayList<Requerimiento>());
        if (requerimientosRelacionados != null) data.setListaRequerimientos(requerimientosRelacionados);

        data.setTipoUsuario(tipoUsuario);
        
        return data;
    }
}