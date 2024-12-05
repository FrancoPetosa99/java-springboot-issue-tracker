package com.issue_tracker.issue_tracker.service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.ArchivoAdjuntoData;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.NewRequerimientoData;
import com.issue_tracker.issue_tracker.model.ArchivoAdjunto;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.repository.RequerimientoCodigoRepository;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;
import com.issue_tracker.issue_tracker.repository.TipoRequerimientoRepository;
import jakarta.transaction.Transactional;

@Service
public class RequerimientoService {
    
    @Autowired
    private RequerimientoRepository requerimientoRepository;

    @Autowired
    private TipoRequerimientoRepository tipoRequerimientoRepository;

    @Autowired
    private RequerimientoCodigoRepository requerimientoCodigoRepository;
  
    @Transactional
    public Requerimiento createNewRequerimiento(NewRequerimientoData data) {
        
        List<ArchivoAdjuntoData> archivosAdjuntosData = data.getListaArchivos();
        Integer cantidad = archivosAdjuntosData.size();
        if (cantidad > 5) {
            // TODO
            // agregar logica para devolver un error
            return null;
        }
        
        TipoRequerimiento tipoRequerimiento = data.getTipoRequerimiento();
        int currentYear = Year.now().getValue();
        String codigo = tipoRequerimiento.getCodigo();
        Integer requerimentNumber = requerimientoCodigoRepository.getCodigo(codigo, currentYear);
        
        Requerimiento.Builder builder = new Requerimiento
        .Builder()
        .setAsunto(data.getAsunto())
        .setDescripcion(data.getDescripcion())
        .setPrioridad(data.getPrioridad())
        .setUsuarioEmisor(data.getUsuarioEmisor())
        .setCodigo(tipoRequerimiento, requerimentNumber)
        .setRequerimientosRelacionados(data.getListaRequerimientos());

        String tipoUsuario = data.getTipoUsuario();
        Usuario propietario = data.getUsuarioPropietario();
        if ("interno".equals(tipoUsuario)&& propietario != null) {
            builder
            .setUsuarioPropietario(propietario)
            .setEstado("Asignado");
        }
        
        Requerimiento requerimiento = builder.build();

        archivosAdjuntosData
        .forEach(archivoData -> {
            ArchivoAdjunto archivo = this.createArchivoAdjunto(archivoData);
            requerimiento.addArchivoAdjunto(archivo);
        });
        

        return requerimientoRepository.save(requerimiento);
    }

    public TipoRequerimiento getTipoRequerimientoById(Integer tipoRequerimientoId) {

        TipoRequerimiento tipoRequerimiento = tipoRequerimientoRepository.findById(tipoRequerimientoId).orElse(null);

        if(tipoRequerimiento == null) {
            // TODO
            // agregar logica para devolver un error
            return null;
        }

        return tipoRequerimiento;
    }

    public List<Requerimiento> getRequerimientoByUsuarioEmisorId(Integer userId) {
        List<Requerimiento> requerimientos = requerimientoRepository.findByUsuarioEmisorId(userId);
        return requerimientos;
    }

    public Requerimiento getRequerimientoById(Integer requerimientoId) {
        Requerimiento requerimiento = requerimientoRepository.findById(requerimientoId).orElse(null);
        if (requerimiento == null) {
            // TODO
            // agregar logica para devolver un error si no existe el requerimiento
        }
        return requerimiento;
    }

    private ArchivoAdjunto createArchivoAdjunto(ArchivoAdjuntoData data) {

        String extension = data.getExtension();
        if (
            !extension.equalsIgnoreCase("word") &&
            !extension.equalsIgnoreCase("excel") &&
            !extension.equalsIgnoreCase("pdf")
        ) {
            // TODO
            // agregar logica para devolver un error
            return null;    
        }
        
        ArchivoAdjunto archivoAdjunto = new ArchivoAdjunto();

        archivoAdjunto.setNombre(data.getNombre());
        archivoAdjunto.setExtension(data.getExtension());
        archivoAdjunto.setContenido(data.getContenido());
        archivoAdjunto.setCreatedAt(LocalDateTime.now());
        archivoAdjunto.setUpdatedAt(LocalDateTime.now());

        return archivoAdjunto;
    }
}