package com.issue_tracker.issue_tracker.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.issue_tracker.issue_tracker.dto.RecuperarTipoRequerimientos.TipoRequerimientoDTO;
import com.issue_tracker.issue_tracker.dto.RecuperarTipoRequerimientos.TipoRequerimientoMapper;
import com.issue_tracker.issue_tracker.response.HttpBodyResponse;
import com.issue_tracker.issue_tracker.response.ResponseFactory;
import com.issue_tracker.issue_tracker.service.TipoRequerimientoService;

@RestController
@RequestMapping("/api/tipos_requerimientos")
@CrossOrigin(origins = "*")
public class TipoRequerimientoController {

    @Autowired
    private TipoRequerimientoService tipoRequerimientoService;

    @GetMapping("/")
    public ResponseEntity<HttpBodyResponse> getTipoRequerimientos() {

        try {

            List<TipoRequerimientoDTO> tipoRequerimientos = tipoRequerimientoService
            .getTipoRequerimientos()
            .stream()
            .map(tipoRequerimiento -> TipoRequerimientoMapper.mapTipoRequerimientoToDTO(tipoRequerimiento))
            .toList();
            
            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(200)
            .message("Se han encontrado tipo de requerimientos")
            .data(tipoRequerimientos)
            .build();
            
            return ResponseEntity
            .status(200)
            .body(response);
                
        } catch (Exception e) {
            return ResponseFactory.internalServerError();
        }
    }
}