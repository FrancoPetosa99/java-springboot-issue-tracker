package com.issue_tracker.issue_tracker.service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.State.Requerimiento.RequerimientoHandler;
import com.issue_tracker.issue_tracker.config.CustomUserDetails;
import com.issue_tracker.issue_tracker.dto.AgregarNuevoComentario.NuevoComentarioData;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.ArchivoAdjuntoData;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.NewRequerimientoData;
import com.issue_tracker.issue_tracker.enums.ExtensionesArchivos;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.exception.ForbiddenException;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.model.ArchivoAdjunto;
import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.repository.ComentarioRepository;
import com.issue_tracker.issue_tracker.repository.RequerimientoCodigoRepository;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;
import jakarta.transaction.Transactional;

@Service
public class RequerimientoService {

    @Autowired
    private RequerimientoRepository requerimientoRepository;
    
    @Autowired
    private RequerimientoCodigoRepository requerimientoCodigoRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;
  
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

    public Page<Requerimiento> getRequerimientos(Pageable pageable) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        String role = currentUser.getRole();
        
        if (role.equalsIgnoreCase("EXTERNO")) {
            Integer userId = currentUser.getUserId();
            Page<Requerimiento> requerimientos = this.getRequerimientoByUsuarioEmisorId(userId, pageable);
            return requerimientos;
        }

        Page<Requerimiento> requerimientos = requerimientoRepository.findAll(pageable);
        return requerimientos;
    }

    private Page<Requerimiento> getRequerimientoByUsuarioEmisorId(Integer userId, Pageable pageable) {
        Page<Requerimiento> requerimientos = requerimientoRepository.findByUsuarioEmisorId(userId, pageable);
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

        boolean esValida = Arrays
        .stream(ExtensionesArchivos.values())
        .anyMatch(ext -> ext.name().equalsIgnoreCase(extension));

        if (esValida) throw new BadRequestException("Extensión de archivo no válida: " + extension);
        
        ArchivoAdjunto archivoAdjunto = new ArchivoAdjunto();

        archivoAdjunto.setNombre(data.getNombre());
        archivoAdjunto.setExtension(data.getExtension());
        archivoAdjunto.setContenido(data.getContenido());
        archivoAdjunto.setCreatedAt(LocalDateTime.now());
        archivoAdjunto.setUpdatedAt(LocalDateTime.now());

        return archivoAdjunto;
    }

    public void asignarNuevoPropietario(Integer requerimientoId, Usuario nuevoPropietario) 
    throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        String role = currentUser.getRole();
        
        if (role.equalsIgnoreCase("EXTERNO")) throw new ForbiddenException();

        Requerimiento requerimiento = requerimientoRepository.findById(requerimientoId).orElse(null);

        if (requerimiento == null) throw new NotFoundException("No se ha encontrado requerimiento con id: " + requerimientoId);

        RequerimientoHandler handler = new RequerimientoHandler(requerimiento);

        handler.asignarPropietario(nuevoPropietario);

        requerimientoRepository.save(requerimiento);
    }

    public Comentario crearNuevoComentario(NuevoComentarioData data) 
    throws Exception {

        Integer requerimientoId = data.getRequerimientoId();
        Requerimiento requerimiento = requerimientoRepository.findById(requerimientoId).orElse(null);

        if (requerimiento == null) throw new NotFoundException("No se ha encontrado requerimiento con id: " + requerimientoId);

        Comentario comentario = new Comentario
        .Builder()
        .buildAsunto(data.getAsunto())
        .buildDescripcion(data.getDescripcion())
        .buildUsuarioEmisor(data.getEmisor())
        .buildRequerimiento(requerimiento)
        .build();

        RequerimientoHandler handler = new RequerimientoHandler(requerimiento);

        handler.agregarComentario(comentario);

        return comentarioRepository.save(comentario);
    }

    public void cerrarRequerimiento(Requerimiento requerimiento) 
    throws Exception {

        RequerimientoHandler handler = new RequerimientoHandler(requerimiento);

        handler.cerrarRequerimiento();

        requerimientoRepository.save(requerimiento);
    }
}