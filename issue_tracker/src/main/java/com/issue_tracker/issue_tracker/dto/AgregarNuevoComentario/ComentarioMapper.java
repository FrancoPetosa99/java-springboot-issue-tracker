package com.issue_tracker.issue_tracker.dto.AgregarNuevoComentario;

import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Usuario;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ComentarioMapper {

    public NuevoComentarioData mapRequestToData(NewComentarioRequest request, Usuario emisor, Integer requerimientoId) {
        
        NuevoComentarioData data = new NuevoComentarioData();

        data.setAsunto(request.getAsunto());
        data.setDescripcion(request.getDescripcion());
        data.setEmisor(emisor);
        data.setRequerimientoId(requerimientoId);

        return data;
    }

    public NuevoComentarioBodyResponse mapComentarioToResponse(Comentario comentario) {

        NuevoComentarioBodyResponse dto = new NuevoComentarioBodyResponse();

        dto.setId(comentario.getId());
        dto.setAsunto(comentario.getAsunto());
        dto.setDescripcion(comentario.getDescripcion());
        dto.setCreatedAt(comentario.getCreatedAt());
        
        return dto;
    }
}