package com.issue_tracker.issue_tracker.service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.ArchivoAdjuntoData;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.NewRequerimientoData;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.model.ArchivoAdjunto;
import com.issue_tracker.issue_tracker.model.CategoriaRequerimiento;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.repository.CategoriaRequerimientoRepository;
import com.issue_tracker.issue_tracker.repository.RequerimientoCodigoRepository;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;
import com.issue_tracker.issue_tracker.repository.TipoRequerimientoRepository;
import jakarta.transaction.Transactional;

@Service
public class RequerimientoService {

    private static final List<String> EXTENSIONES_VALIDAS = Arrays.asList("word", "excel", "pdf");
    
    @Autowired
    private RequerimientoRepository requerimientoRepository;

    @Autowired
    private TipoRequerimientoRepository tipoRequerimientoRepository;

    @Autowired
    private RequerimientoCodigoRepository requerimientoCodigoRepository;

    @Autowired
    private CategoriaRequerimientoRepository categoriaRequerimientoRepository;
  
    @Transactional
    public Requerimiento createNewRequerimiento(NewRequerimientoData data)
    throws BadRequestException {
        
        List<ArchivoAdjuntoData> archivosAdjuntosData = data.getListaArchivos();
        Integer cantidad = archivosAdjuntosData.size();
        if (cantidad > 5) throw new BadRequestException("No se pueden adjuntar más de 5 archivos");
        
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
        .setCategoriaRequerimiento(data.getCategoriaRequerimiento())
        .setRequerimientosRelacionados(data.getListaRequerimientos());

        String tipoUsuario = data.getTipoUsuario();
        Usuario propietario = data.getUsuarioPropietario();
        if ("interno".equals(tipoUsuario) && propietario != null) {
            builder
            .setUsuarioPropietario(propietario)
            .setEstado("Asignado");
        }
        
        Requerimiento requerimiento = builder.build();

        for (ArchivoAdjuntoData archivoData : archivosAdjuntosData) {
            ArchivoAdjunto archivo = this.createArchivoAdjunto(archivoData);
            requerimiento.addArchivoAdjunto(archivo);
        }
        
        return requerimientoRepository.save(requerimiento);
    }

    public TipoRequerimiento getTipoRequerimientoById(Integer tipoRequerimientoId) 
    throws NotFoundException {

        TipoRequerimiento tipoRequerimiento = tipoRequerimientoRepository.findById(tipoRequerimientoId).orElse(null);

        if(tipoRequerimiento == null) throw new NotFoundException("No se ha encontrado Tipo Requerimiento con id: " + tipoRequerimientoId);

        return tipoRequerimiento;
    }

    public CategoriaRequerimiento getCategoriaRequerimientoById(Integer categoriaRequerimientoId) 
    throws NotFoundException {

        CategoriaRequerimiento categoriaRequerimiento = categoriaRequerimientoRepository.findById(categoriaRequerimientoId).orElse(null);

        if(categoriaRequerimiento == null) throw new NotFoundException("No se ha encontrado Categoria Requerimiento con id: " + categoriaRequerimientoId);

        return categoriaRequerimiento;
    }

    public List<Requerimiento> getRequerimientoByUsuarioEmisorId(Integer userId) {
        List<Requerimiento> requerimientos = requerimientoRepository.findByUsuarioEmisorId(userId);
        return requerimientos;
    }

    public Requerimiento getRequerimientoById(Integer requerimientoId) 
    throws NotFoundException {
        Requerimiento requerimiento = requerimientoRepository.findById(requerimientoId).orElse(null);
        if (requerimiento == null) throw new NotFoundException("No se ha encontrado Requerimiento con id: " + requerimientoId);
        return requerimiento;
    }

    private ArchivoAdjunto createArchivoAdjunto(ArchivoAdjuntoData data)
    throws BadRequestException {
        
        String extension = data.getExtension();

        if (!EXTENSIONES_VALIDAS.contains(extension)) throw new BadRequestException("Extensión de archivo no válida: " + extension);
        
        ArchivoAdjunto archivoAdjunto = new ArchivoAdjunto();

        archivoAdjunto.setNombre(data.getNombre());
        archivoAdjunto.setExtension(data.getExtension());
        archivoAdjunto.setContenido(data.getContenido());
        archivoAdjunto.setCreatedAt(LocalDateTime.now());
        archivoAdjunto.setUpdatedAt(LocalDateTime.now());

        return archivoAdjunto;
    }
}