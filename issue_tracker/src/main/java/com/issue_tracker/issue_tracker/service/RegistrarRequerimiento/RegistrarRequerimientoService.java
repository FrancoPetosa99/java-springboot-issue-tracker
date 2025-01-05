package com.issue_tracker.issue_tracker.service.RegistrarRequerimiento;

import java.time.Year;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.issue_tracker.issue_tracker.Builder.Requerimiento.RequerimientoBuilder;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.NewRequerimientoData;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.model.ArchivoAdjunto;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.repository.RequerimientoCodigoRepository;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;
import jakarta.transaction.Transactional;

public class RegistrarRequerimientoService {
    
    @Autowired
    private RequerimientoRepository requerimientoRepository;

    @Autowired
    private RequerimientoCodigoRepository requerimientoCodigoRepository;

    @Transactional
    public Requerimiento registrarRequerimiento(NewRequerimientoData data) 
    throws Exception {

        List<ArchivoAdjunto> listaArchivos = data.getListaArchivos();
        Integer cantidad = listaArchivos.size();

        if (cantidad > 5) 
            throw new BadRequestException("No se pueden adjuntar más de 5 archivos");

        TipoRequerimiento tipoRequerimiento = data.getTipoRequerimiento();
        String codigo = tipoRequerimiento.getCodigo();

        Integer currentYear = Year.now().getValue();

        Integer requerimientoNumber = requerimientoCodigoRepository.getCodigo(codigo, currentYear);

        Requerimiento requerimiento = new RequerimientoBuilder()
        .setAsunto(data.getAsunto())
        .setDescripcion(data.getDescripcion())
        .setPrioridad(data.getPrioridad())
        .setUsuarioEmisor(data.getUsuarioEmisor())
        .setCodigo(tipoRequerimiento, requerimientoNumber)
        .setCategoriaRequerimiento(data.getCategoriaRequerimiento())
        .setUsuarioPropietario(data.getUsuarioPropietario())
        .setListaArchivosAdjuntos(listaArchivos)
        .setListaRequerimientos(data.getListaRequerimientos())
        .build();

        requerimiento = requerimientoRepository.save(requerimiento);

        return requerimiento;
    }
}