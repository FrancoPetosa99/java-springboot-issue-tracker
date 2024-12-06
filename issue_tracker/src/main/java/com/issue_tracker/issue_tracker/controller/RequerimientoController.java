package com.issue_tracker.issue_tracker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.issue_tracker.issue_tracker.dto.RequerimientoDetails;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.NewRequerimientoRequest;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.RequerimientoMapper;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.RequerimientoResponse;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.exception.UnauthorizedException;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.NewRequerimientoData;
import com.issue_tracker.issue_tracker.jwt.JwtToken;
import com.issue_tracker.issue_tracker.model.CategoriaRequerimiento;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.response.HttpBodyResponse;
import com.issue_tracker.issue_tracker.response.ResponseFactory;
import com.issue_tracker.issue_tracker.service.RequerimientoService;
import com.issue_tracker.issue_tracker.service.UsuarioService;

@RestController
@RequestMapping("/api/requerimientos")
@CrossOrigin(origins = "*")
public class RequerimientoController {

    @Autowired
    private RequerimientoService requerimientoService;

    @Autowired 
    private UsuarioService usuarioService;

    @Autowired
    private ResponseFactory responseFactory;
    
    @GetMapping("/")
    public ResponseEntity<HttpBodyResponse> getRequerimientos(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size,
        @RequestParam(defaultValue = "DESC") String order,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestHeader(value = "Authorization", required = true) String authToken ) {

        try {
            
            authToken = authToken.substring(7);
            Map<String, Object> payload = JwtToken.getPayload(authToken);
            Integer loggedInUserId = (Integer) payload.get("id");

            Sort sort = order.equalsIgnoreCase("ASC") 
            ? Sort.by(sortBy).ascending() 
            : Sort.by(sortBy).descending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Requerimiento> requerimientos = requerimientoService.getRequerimientoByUsuarioEmisorId(loggedInUserId, pageable);

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

            if (usuarioEmisorId == null) throw new UnauthorizedException();

            String tipoUsuario = (String) payload.get("tipo");

            Usuario emisor = usuarioService.getUsuarioById(usuarioEmisorId);

            Integer tipoRequerimientoId = requestBody.getTipoRequerimientoId();
            TipoRequerimiento tipoRequerimiento = requerimientoService.getTipoRequerimientoById(tipoRequerimientoId);

            Integer categoriaRequerimientoId = requestBody.getCategoriaRequerimientoId();
            CategoriaRequerimiento categoriaRequerimiento = requerimientoService.getCategoriaRequerimientoById(categoriaRequerimientoId);
        
            Usuario propietario = null;
            Integer usuarioPropietarioId = requestBody.getUsuarioPropietarioId();
            if (usuarioPropietarioId != 0 && usuarioPropietarioId != null) { 
                propietario = usuarioService.getUsuarioById(usuarioPropietarioId);
            }
        
            List<Requerimiento> listaRequerimientosRelacionados = new ArrayList<>();
            List<Integer> listaRequerimientosId = requestBody.getListaRequerimientosId();
            for (Integer id : listaRequerimientosId) {
                Requerimiento requerimiento = requerimientoService.getRequerimientoById(id);
                listaRequerimientosRelacionados.add(requerimiento);
            }
                    
            RequerimientoMapper mapper = new RequerimientoMapper();

            NewRequerimientoData data = mapper.mapBodyRequestToData(
                requestBody, 
                propietario, 
                emisor, 
                tipoRequerimiento,
                categoriaRequerimiento,
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
                
        } 
        catch(BadRequestException e) {
                return responseFactory.badRequest(e.getMessage());
        }   catch (UnauthorizedException e) {
                return responseFactory.unauthorizedError();
        }   catch (NotFoundException e) {
                return responseFactory.errorNotFound(e.getMessage());
        } catch (Exception e) {
                return responseFactory.internalServerError();
        }
    }
}