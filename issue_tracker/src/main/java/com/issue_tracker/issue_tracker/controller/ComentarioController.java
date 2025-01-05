package com.issue_tracker.issue_tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.issue_tracker.issue_tracker.Builder.Comentario.ComentarioBuilder;
import com.issue_tracker.issue_tracker.config.CustomUserDetails;
import com.issue_tracker.issue_tracker.dto.AgregarNuevoComentario.NewComentarioRequest;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.model.Comentario;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.response.HttpBodyResponse;
import com.issue_tracker.issue_tracker.response.ResponseFactory;
import com.issue_tracker.issue_tracker.service.ComentarioService;
import com.issue_tracker.issue_tracker.service.RequerimientoService;
import com.issue_tracker.issue_tracker.service.UsuarioService;
import com.issue_tracker.issue_tracker.service.ComentarRequerimiento.ComentarRequerimientoService;

@RestController
@RequestMapping("/api/comentarios")
@CrossOrigin(origins = "*")
public class ComentarioController {

    @Autowired
    private RequerimientoService requerimientoService;

    @Autowired
    private ComentarRequerimientoService comentarRequerimientoService;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired 
    private UsuarioService usuarioService;

    @Autowired
    private ResponseFactory responseFactory;

    @PostMapping("/requerimientos/{requerimientoId}")
    public ResponseEntity<HttpBodyResponse> registrarComentarioEnRequerimiento(
        @PathVariable Integer requerimientoId,
        @RequestBody NewComentarioRequest requestBody
    ) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
          
            Integer emisorId = currentUser.getUserId();
            Usuario usuarioEmisor = usuarioService.getUsuarioById(emisorId);

            Requerimiento requerimiento = requerimientoService.getRequerimientoById(requerimientoId);

            Comentario comentario = new ComentarioBuilder()
            .buildAsunto(requestBody.getAsunto())
            .buildDescripcion(requestBody.getDescripcion())
            .buildUsuarioEmisor(usuarioEmisor)
            .buildRequerimiento(requerimiento)
            .build();
            
            comentarRequerimientoService.comentarRequerimiento(comentario, requerimiento);

            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(200)
            .message("Se ha registrado el comentario con exito")
            .data(comentario)
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

    @GetMapping("/requerimientos/{requerimientoId}")
    public ResponseEntity<HttpBodyResponse> getComentariosByRequerimiento(
        @PathVariable Integer requerimientoId
    ) {

        try {
            
            List<Comentario> listaComentarios = comentarioService.getComentariosByRequerimientoId(requerimientoId);

            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(200)
            .message("Se ha registrado el comentario con exito")
            .data(listaComentarios)
            .build();
            
            return ResponseEntity
            .status(200)
            .body(response);
                
        } catch (Exception e) {
            return responseFactory.internalServerError();
        }
    }
}