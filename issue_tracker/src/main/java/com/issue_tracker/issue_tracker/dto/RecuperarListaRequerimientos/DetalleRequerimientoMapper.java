package com.issue_tracker.issue_tracker.dto.RecuperarListaRequerimientos;

import java.util.List;
import java.util.stream.Collectors;
import com.issue_tracker.issue_tracker.model.ArchivoAdjunto;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;

public class DetalleRequerimientoMapper {
    
    public DetalleRequerimiento mapRequerimientoToDetalle(Requerimiento requerimiento) {

        DetalleRequerimiento dto = new DetalleRequerimiento();

        String tipo = requerimiento.getTipoRequerimiento().getCodigo();
        dto.setTipoRequerimiento(tipo);

        String categoria = requerimiento.getCategoriaRequerimiento().getDescripcion();
        dto.setCategoriaRequerimiento(categoria);

        Usuario usuarioPropietario = requerimiento.getUsuarioPropietario();
        if (usuarioPropietario != null) {
            String nombreUsuario = usuarioPropietario.getNombreUsuario();
            dto.setUsuarioPropietario(nombreUsuario);
        }

        dto.setId(requerimiento.getId());
        dto.setCodigo(requerimiento.getCodigo());
        dto.setAsunto(requerimiento.getAsunto());
        dto.setDescripcion(requerimiento.getDescripcion());
        dto.setPrioridad(requerimiento.getPrioridad());
        dto.setEstado(requerimiento.getEstado());
        dto.setCreatedAt(requerimiento.getCreatedAt());

        List<Requerimiento> requerimientosRelacionados = requerimiento.getRequerimientosRelacionados();
        List<DetalleRequerimiento> listaDetalleRequerimientos = requerimientosRelacionados
        .stream()
        .map(requerimientoRelacionado -> {

            DetalleRequerimiento detalle = new DetalleRequerimiento();

            String tipoRequerimiento = requerimientoRelacionado.getTipoRequerimiento().getCodigo();
            detalle.setTipoRequerimiento(tipoRequerimiento);

            String categoriaRequerimiento = requerimientoRelacionado.getCategoriaRequerimiento().getDescripcion();
            detalle.setCategoriaRequerimiento(categoriaRequerimiento);

            Usuario propietario = requerimientoRelacionado.getUsuarioPropietario();
            if (propietario != null) {
                String nombreUsuario = propietario.getNombreUsuario();
                detalle.setUsuarioPropietario(nombreUsuario);
            }

            detalle.setId(requerimientoRelacionado.getId());
            detalle.setCodigo(requerimientoRelacionado.getCodigo());
            detalle.setAsunto(requerimientoRelacionado.getAsunto());
            detalle.setDescripcion(requerimientoRelacionado.getDescripcion());
            detalle.setPrioridad(requerimientoRelacionado.getPrioridad());
            detalle.setEstado(requerimientoRelacionado.getEstado());
            detalle.setCreatedAt(requerimientoRelacionado.getCreatedAt());
            
            return detalle;
        })
        .collect(Collectors.toList());

        dto.setRequerimientosRelacionados(listaDetalleRequerimientos);

        List<ArchivoAdjunto> archivosAdjuntos = requerimiento.getListaArchivos();
        List<DetalleArchivo> listaArchivos = archivosAdjuntos
        .stream()
        .map(archivo -> {

            DetalleArchivo detalleArchivo = new DetalleArchivo();

            detalleArchivo.setId(archivo.getId());
            detalleArchivo.setNombre(archivo.getNombre());
            detalleArchivo.setExtension(archivo.getExtension());

            return detalleArchivo;

        })
        .collect(Collectors.toList());

        dto.setListaArchivosAdjuntos(listaArchivos);

        return dto;
    }
}