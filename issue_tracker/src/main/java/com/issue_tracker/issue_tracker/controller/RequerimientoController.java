package com.issue_tracker.issue_tracker.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.issue_tracker.issue_tracker.dto.RequerimientoDetails;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.NewRequerimientoRequest;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.RequerimientoMapper;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.RequerimientoResponse;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.NewRequerimientoData;
import com.issue_tracker.issue_tracker.jwt.JwtToken;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.response.HttpBodyResponse;
import com.issue_tracker.issue_tracker.service.RequerimientoService;
import com.issue_tracker.issue_tracker.service.UsuarioService;

@RestController
@RequestMapping("/api/requerimientos")
@CrossOrigin(origins = "*")
public class RequerimientoController {

    @Autowired
    private RequerimientoService requerimientoService;

    @Autowired UsuarioService usuarioService;
    
    @GetMapping("/")
    public ResponseEntity<HttpBodyResponse> getRequerimientos(
        @RequestHeader(value = "Authorization", required = true) String authToken ) {

        try {
            
            authToken = authToken.substring(7);
            Map<String, Object> payload = JwtToken.getPayload(authToken);
            Integer loggedInUserId = (Integer) payload.get("id");

            List<Requerimiento> requerimientos = requerimientoService.getRequerimientoByUsuarioEmisorId(loggedInUserId);

            List<RequerimientoDetails> requerimientosDetailList = requerimientos
            .stream()
            .map(requerimiento -> {
                RequerimientoDetails detail = new RequerimientoDetails();
                detail.setId(requerimiento.getId());
                detail.setCodigo(requerimiento.getCodigo());
                detail.setDescripcion(requerimiento.getDescripcion());
                detail.setAsunto(requerimiento.getAsunto());
                detail.setPrioridad(requerimiento.getPrioridad());
                detail.setEstado(requerimiento.getEstado());
                detail.setTipoRequerimiento(requerimiento.getTipoRequerimiento().getCodigo());
                detail.setUsuarioPropietario(requerimiento.getUsuarioPropietario());
                detail.setDeletedAt(requerimiento.getDeletedAt());
                detail.setCreatedAt(requerimiento.getCreatedAt());
                detail.setUpdatedAt(requerimiento.getUpdatedAt());
                return detail;
            })
            .collect(Collectors.toList());
        
            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(200)
            .message("Se han encontrado los requerimientos pertenecientes al usuario con id: " + loggedInUserId)
            .data(requerimientosDetailList)
            .build();

            return ResponseEntity
            .status(200)
            .body(response);
                
        } catch (Exception e) {
            HttpBodyResponse errorResponse = new HttpBodyResponse
            .Builder()
            .status("Error")
            .statusCode(500)
            .message("Ha ocurrido un error: " + e.getMessage())
            .build();

            return ResponseEntity
            .status(500)
            .body(errorResponse);
        }
    }

    @PostMapping("/")
    public ResponseEntity<HttpBodyResponse> createNewRequerimiento(
        @RequestHeader(value = "Authorization", required = true) String authToken,
        @RequestBody NewRequerimientoRequest requestBody
        ) {

        try {
            
            authToken = authToken.substring(7);
            Map<String, Object> payload = JwtToken.getPayload(authToken);
            
            Integer usuarioEmisorId = (Integer) payload.get("id");
            String tipoUsuario = (String) payload.get("tipo");

            Usuario emisor = usuarioService.getUsuarioById(usuarioEmisorId);

            Integer tipoRequerimientoId = requestBody.getTipoRequerimientoId();
            TipoRequerimiento tipoRequerimiento = requerimientoService.getTipoRequerimientoById(tipoRequerimientoId);
        
            Usuario propietario = null;
            Integer usuarioPropietarioId = requestBody.getUsuarioPropietarioId();
            if (usuarioPropietarioId != 0 && usuarioPropietarioId != null) { 
                propietario = usuarioService.getUsuarioById(usuarioPropietarioId);
            }
            
            List<Integer> listaRequerimientosId = requestBody.getListaRequerimientosId();
            List<Requerimiento> listaRequerimientosRelacionados = null;
            if (listaRequerimientosId != null) {
                listaRequerimientosRelacionados = listaRequerimientosId
                .stream()
                .map(id -> requerimientoService.getRequerimientoById(id))
                .collect(Collectors.toList());
            }
            
            RequerimientoMapper mapper = new RequerimientoMapper();

            NewRequerimientoData data = mapper.mapBodyRequestToData(
                requestBody, 
                propietario, 
                emisor, 
                tipoRequerimiento,
                listaRequerimientosRelacionados,
                tipoUsuario
            );
            
            Requerimiento requerimiento = requerimientoService.createNewRequerimiento(data);
            
            RequerimientoResponse bodyResponse = mapper.mapRequerimientoToResonse(requerimiento);
            
            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(200)
            .message("Se ha creado el requerimiento con exito")
            .data(bodyResponse)
            .build();
            
            return ResponseEntity
            .status(200)
            .body(response);
                
        } catch (Exception e) {
            HttpBodyResponse errorResponse = new HttpBodyResponse
            .Builder()
            .status("Error")
            .statusCode(500)
            .message("Ha ocurrido un error: " + e.getMessage())
            .build();

            return ResponseEntity
            .status(500)
            .body(errorResponse);
        }
    }
}