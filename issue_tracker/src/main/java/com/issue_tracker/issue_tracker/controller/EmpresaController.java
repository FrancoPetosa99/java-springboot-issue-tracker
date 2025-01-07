package com.issue_tracker.issue_tracker.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.issue_tracker.issue_tracker.dto.RecuperarListaEmpresas.EmpresaDTO;
import com.issue_tracker.issue_tracker.dto.RecuperarListaEmpresas.EmpresaMapper;
import com.issue_tracker.issue_tracker.model.Empresa;
import com.issue_tracker.issue_tracker.response.HttpBodyResponse;
import com.issue_tracker.issue_tracker.response.ResponseFactory;
import com.issue_tracker.issue_tracker.service.EmpresaService;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/")
    public ResponseEntity<HttpBodyResponse> getEmpresas() {
        
        try {
            
            List<Empresa> listaEmpresas = empresaService.getEmpresas();

            EmpresaMapper mapper = new EmpresaMapper();

            List<EmpresaDTO> listaEmpresasDTO = listaEmpresas
            .stream()
            .map(empresa -> mapper.mapEmpresaToDTO(empresa))
            .collect(Collectors.toList());
            
            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(200)
            .message("Se han encontrado empresas")
            .data(listaEmpresasDTO)
            .build();
    
            return ResponseEntity
            .status(200)
            .body(response);

        } catch (Exception e) {
            return ResponseFactory.internalServerError();
        }
    }
}