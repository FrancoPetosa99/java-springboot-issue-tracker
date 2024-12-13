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

import com.issue_tracker.issue_tracker.State.Requerimiento.RequerimientoState;
import com.issue_tracker.issue_tracker.State.Requerimiento.StateFactory;
import com.issue_tracker.issue_tracker.config.CustomUserDetails;
import com.issue_tracker.issue_tracker.dto.AgregarNuevoComentario.NuevoComentarioData;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.ArchivoAdjuntoData;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.NewRequerimientoData;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.exception.ForbiddenException;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.model.ArchivoAdjunto;
import com.issue_tracker.issue_tracker.model.CategoriaRequerimiento;
import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.repository.CategoriaRequerimientoRepository;
import com.issue_tracker.issue_tracker.repository.ComentarioRepository;
import com.issue_tracker.issue_tracker.repository.RequerimientoCodigoRepository;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;
import com.issue_tracker.issue_tracker.repository.TipoRequerimientoRepository;
import jakarta.transaction.Transactional;

@Service
public class RequerimientoService {

    private static final List<String> EXTENSIONES_VALIDAS = Arrays.asList("word", "excel", "pdf");

    @Autowired 
    private StateFactory stateFactory;
    
    @Autowired
    private RequerimientoRepository requerimientoRepository;

    @Autowired
    private TipoRequerimientoRepository tipoRequerimientoRepository;

    @Autowired
    private RequerimientoCodigoRepository requerimientoCodigoRepository;

    @Autowired
    private CategoriaRequerimientoRepository categoriaRequerimientoRepository;

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

    public Page<Requerimiento> getRequerimientos(Pageable pageable) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        String role = currentUser.getRole();
        
        if (role.equalsIgnoreCase("INTERNO")) {
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

        if (!EXTENSIONES_VALIDAS.contains(extension)) throw new BadRequestException("Extensión de archivo no válida: " + extension);
        
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

        RequerimientoState state = this.stateFactory.getStateContext(requerimiento);
        
        requerimiento.setStateContext(state);

        requerimiento.asignarNuevoPropietario(nuevoPropietario);

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

        RequerimientoState state = this.stateFactory.getStateContext(requerimiento);

        requerimiento.setStateContext(state);

        requerimiento.agregarComentario(comentario);

        return comentarioRepository.save(comentario);
    }
}