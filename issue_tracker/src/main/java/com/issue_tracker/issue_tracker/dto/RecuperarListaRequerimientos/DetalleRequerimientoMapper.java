package com.issue_tracker.issue_tracker.dto.RecuperarListaRequerimientos;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.issue_tracker.issue_tracker.config.CustomUserDetails;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;

public class DetalleRequerimientoMapper {
    
    static public DetalleRequerimiento mapRequerimientoToDetalle(Requerimiento requerimiento) {

        DetalleRequerimiento dto = new DetalleRequerimiento();

        String tipo = requerimiento.getTipoRequerimiento().getCodigo();
        dto.setTipoRequerimiento(tipo);

        dto.setAsunto(requerimiento.getAsunto());

        String categoria = requerimiento.getCategoriaRequerimiento().getDescripcion();
        dto.setCategoriaRequerimiento(categoria);

        Usuario usuarioPropietario = requerimiento.getUsuarioPropietario();
        if (usuarioPropietario != null) {
            String nombreUsuario = usuarioPropietario.getNombreUsuario();
            dto.setUsuarioPropietario(nombreUsuario);
        }

        Usuario usuarioEmisor = requerimiento.getUsuarioEmisor();
        dto.setUsuarioEmisor(usuarioEmisor.getNombreUsuario());

        dto.setId(requerimiento.getId());
        dto.setCodigo(requerimiento.getCodigo());
        dto.setPrioridad(requerimiento.getPrioridad());
        dto.setEstado(requerimiento.getEstado());
        dto.setCreatedAt(requerimiento.getCreatedAt());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        Integer currentUserId = currentUser.getUserId();
        String currentUserRole = currentUser.getRole();

        if (
            currentUserRole.equalsIgnoreCase("interno") &&
            usuarioPropietario != null &&
            currentUserId == usuarioPropietario.getId()
        ) {
            dto.setCanViewDetails(true);
        }

        if (
            currentUserRole.equalsIgnoreCase("interno") &&
            usuarioEmisor != null &&
            currentUserId == usuarioEmisor.getId()
        ) {
            dto.setCanViewDetails(true);
        }

        if (currentUserRole.equalsIgnoreCase("externo")) {
            dto.setCanViewDetails(true);
        }

        return dto;
    }
}