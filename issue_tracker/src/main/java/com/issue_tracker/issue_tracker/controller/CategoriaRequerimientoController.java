package com.issue_tracker.issue_tracker.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.issue_tracker.issue_tracker.model.CategoriaRequerimiento;
import com.issue_tracker.issue_tracker.response.HttpBodyResponse;
import com.issue_tracker.issue_tracker.response.ResponseFactory;
import com.issue_tracker.issue_tracker.service.CategoriaRequerimientoService;

@RestController
@RequestMapping("/api/categorias_requerimiento")
@CrossOrigin(origins = "*")
public class CategoriaRequerimientoController {

    @Autowired
    private CategoriaRequerimientoService categoriaRequerimientoService;

    @GetMapping("/tipos_requerimientos/{tipoRequerimientoId}")
    public ResponseEntity<HttpBodyResponse> getCategoriasByTipoRequerimiento(
        @PathVariable Integer tipoRequerimientoId
    ) {

        try {

            List<CategoriaRequerimiento> categoriaRequerimientos = categoriaRequerimientoService.getCategoriasByTipoRequerimiento(tipoRequerimientoId);
            
            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(200)
            .message("Se han encontrado tipo de requerimientos")
            .data(categoriaRequerimientos)
            .build();
            
            return ResponseEntity
            .status(200)
            .body(response);
                
        } catch (Exception e) {
            return ResponseFactory.internalServerError();
        }
    }
}