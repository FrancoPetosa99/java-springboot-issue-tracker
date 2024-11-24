package com.issue_tracker.issue_tracker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.response.HttpBodyResponse;
import com.issue_tracker.issue_tracker.service.RequerimientoService;

@RestController
@RequestMapping("/api/requerimientos")
@CrossOrigin(origins = "*")
public class RequerimientoController {
    
    private final RequerimientoService requerimientoService;

    public RequerimientoController(RequerimientoService requerimientoService) {
        this.requerimientoService = requerimientoService;
    }

    @GetMapping("/ping")
    public ResponseEntity<HttpBodyResponse> checkHealth(){
        HttpBodyResponse response = new HttpBodyResponse
                .Builder()
                .status("Success")
                .statusCode(200)
                .message("Holaa " )
                .build();

        return ResponseEntity
                .status(200)
                .body(response);
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<HttpBodyResponse> getRequerimientosByUser(@PathVariable Usuario usuario) {
        try {

            List<Requerimiento> requerimientos = requerimientoService.getRequerimientosByUser(usuario);
            
            HttpBodyResponse response = new HttpBodyResponse
                .Builder()
                .status("Success")
                .statusCode(200)
                .message("Requerimientos encontrados para el usuario: " + usuario)
                .data(requerimientos)
                .build();

            return ResponseEntity
                .status(200)
                .body(response);
                
        } catch (Exception e) {
            HttpBodyResponse errorResponse = new HttpBodyResponse
                .Builder()
                .status("Error")
                .statusCode(500)
                .message("Error al buscar requerimientos: " + e.getMessage())
                .build();

            return ResponseEntity
                .status(500)
                .body(errorResponse);
        }
    }

    

    @GetMapping("/active/user/{userId}")
    public ResponseEntity<HttpBodyResponse> getActiveRequerimientosByUser(@PathVariable Usuario usuario) {
        try {
            List<Requerimiento> activeRequerimientos = requerimientoService.getActiveRequerimientosByUser(usuario);
            
            HttpBodyResponse response = new HttpBodyResponse
                .Builder()
                .status("Success")
                .statusCode(200)
                .message("Requerimientos activos encontrados para el usuario: " + usuario)
                .data(activeRequerimientos)
                .build();

            return ResponseEntity
                .status(200)
                .body(response);
                
        } catch (Exception e) {
            HttpBodyResponse errorResponse = new HttpBodyResponse
                .Builder()
                .status("Error")
                .statusCode(500)
                .message("Error al buscar requerimientos activos: " + e.getMessage())
                .build();

            return ResponseEntity
                .status(500)
                .body(errorResponse);
        }
    }
}