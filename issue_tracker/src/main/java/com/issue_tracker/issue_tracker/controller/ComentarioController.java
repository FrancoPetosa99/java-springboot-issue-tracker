package com.issue_tracker.issue_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.issue_tracker.issue_tracker.config.CustomUserDetails;
import com.issue_tracker.issue_tracker.dto.AgregarNuevoComentario.ComentarioMapper;
import com.issue_tracker.issue_tracker.dto.AgregarNuevoComentario.NewComentarioRequest;
import com.issue_tracker.issue_tracker.dto.AgregarNuevoComentario.NuevoComentarioBodyResponse;
import com.issue_tracker.issue_tracker.dto.AgregarNuevoComentario.NuevoComentarioData;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.response.HttpBodyResponse;
import com.issue_tracker.issue_tracker.response.ResponseFactory;
import com.issue_tracker.issue_tracker.service.RequerimientoService;
import com.issue_tracker.issue_tracker.service.UsuarioService;

@RestController
@RequestMapping("/api/requerimientos")
@CrossOrigin(origins = "*")
public class ComentarioController {

    @Autowired
    private RequerimientoService requerimientoService;

    @Autowired 
    private UsuarioService usuarioService;

    @Autowired
    private ResponseFactory responseFactory;

    @PostMapping("/{requerimientoId}/comentarios")
    public ResponseEntity<HttpBodyResponse> agregarNuevoComentario(
        @PathVariable Integer requerimientoId,
        @RequestBody NewComentarioRequest requestBody
    ) {

        try {

            CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();

            Integer emisorId = currentUser.getUserId();
            Usuario usuarioEmisor = usuarioService.getUsuarioById(emisorId);

            ComentarioMapper mapper = new ComentarioMapper();

            NuevoComentarioData data = mapper.mapRequestToData(requestBody, usuarioEmisor, requerimientoId);
            
            Comentario comentario = requerimientoService.crearNuevoComentario(data);

            NuevoComentarioBodyResponse responseData = mapper.mapComentarioToResponse(comentario);
            
            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(200)
            .message("Se ha registrado el comentario con exito")
            .data(responseData)
            .build();
            
            return ResponseEntity
            .status(200)
            .body(response);
                
        } 
        catch(BadRequestException e) {
                return responseFactory.badRequest(e.getMessage());
        }   catch (NotFoundException e) {
                return responseFactory.errorNotFound(e.getMessage());
        } catch (Exception e) {
                return responseFactory.internalServerError();
        }
    }
}