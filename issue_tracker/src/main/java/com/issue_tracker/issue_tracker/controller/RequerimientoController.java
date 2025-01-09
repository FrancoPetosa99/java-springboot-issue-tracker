package com.issue_tracker.issue_tracker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.NewRequerimientoRequest;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.RequerimientoMapper;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.RequerimientoResponse;
import com.issue_tracker.issue_tracker.dto.RecuperarListaRequerimientos.DetalleRequerimiento;
import com.issue_tracker.issue_tracker.dto.RecuperarListaRequerimientos.DetalleRequerimientoMapper;
import com.issue_tracker.issue_tracker.dto.RecuperarListaRequerimientos.ResponsePagination;
import com.issue_tracker.issue_tracker.dto.VisualizarRequerimiento.VisualizarRequerimiento;
import com.issue_tracker.issue_tracker.dto.VisualizarRequerimiento.VisualizarRequerimientoMapper;
import com.issue_tracker.issue_tracker.exception.BadRequestException;
import com.issue_tracker.issue_tracker.exception.ForbiddenException;
import com.issue_tracker.issue_tracker.exception.NotFoundException;
import com.issue_tracker.issue_tracker.exception.UnauthorizedException;
import com.issue_tracker.issue_tracker.config.CustomUserDetails;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest.NewRequerimientoData;
import com.issue_tracker.issue_tracker.model.CategoriaRequerimiento;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;
import com.issue_tracker.issue_tracker.response.HttpBodyResponse;
import com.issue_tracker.issue_tracker.response.ResponseFactory;
import com.issue_tracker.issue_tracker.service.CategoriaRequerimientoService;
import com.issue_tracker.issue_tracker.service.EventoService;
import com.issue_tracker.issue_tracker.service.RequerimientoService;
import com.issue_tracker.issue_tracker.service.TipoRequerimientoService;
import com.issue_tracker.issue_tracker.service.UsuarioService;
import com.issue_tracker.issue_tracker.service.AsignarRequerimiento.AsignarRequerimientoService;
import com.issue_tracker.issue_tracker.service.CerrarRequerimiento.CerrarRequerimientoService;
import com.issue_tracker.issue_tracker.service.EnviarEmail.Email;
import com.issue_tracker.issue_tracker.service.EnviarEmail.EmailBuilder;
import com.issue_tracker.issue_tracker.service.EnviarEmail.EmailService;
import com.issue_tracker.issue_tracker.service.RegistrarRequerimiento.RegistrarRequerimientoService;
import com.issue_tracker.issue_tracker.service.VisualizarRequerimientos.BuscarRequerimientosService;

@RestController
@RequestMapping("/api/requerimientos")
@CrossOrigin(origins = "*")
public class RequerimientoController {

    @Autowired
    private RequerimientoService requerimientoService;

    @Autowired
    private BuscarRequerimientosService buscarRequerimientoService;

    @Autowired
    private RegistrarRequerimientoService registrarRequerimientoService;

    @Autowired
    private CerrarRequerimientoService cerrarRequerimientoService;

    @Autowired AsignarRequerimientoService AsignarRequerimientoService;

    @Autowired
    private TipoRequerimientoService tipoRequerimientoService;

    @Autowired
    private CategoriaRequerimientoService categoriaRequerimientoService;

    @Autowired 
    private UsuarioService usuarioService;

    @Autowired 
    private EventoService eventoService;

    @Autowired
    private EmailService emailService;
 
    @GetMapping("/")
    public ResponseEntity<HttpBodyResponse> getRequerimientos(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size,
        @RequestParam(defaultValue = "DESC") String order,
        @RequestParam(defaultValue = "createdAt") String sortBy
    ) {

        try {
            
            Sort sort = order.equalsIgnoreCase("ASC") 
            ? Sort.by(sortBy).ascending() 
            : Sort.by(sortBy).descending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Requerimiento> requerimientos = buscarRequerimientoService.buscarRequerimientos(pageable);

            DetalleRequerimientoMapper mapper = new DetalleRequerimientoMapper();

            List<DetalleRequerimiento> requerimientosDetailList = requerimientos
            .getContent()
            .stream()
            .map(requerimiento -> mapper.mapRequerimientoToDetalle(requerimiento))
            .collect(Collectors.toList());

            ResponsePagination pagination = new ResponsePagination();
            
            pagination.setListaRequerimientos(requerimientosDetailList);
            pagination.setPageSize(requerimientos.getSize());
            pagination.setCurrentPage(requerimientos.getPageable().getPageNumber());

            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(200)
            .message("Se han encontrado requerimientos")
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

    @GetMapping("/{requerimientoId}")
    public ResponseEntity<HttpBodyResponse> visualizarDetalleRequerimiento(
        @PathVariable Integer requerimientoId
    ) {

        try {

            Requerimiento requerimiento = requerimientoService.getRequerimientoById(requerimientoId);

            VisualizarRequerimientoMapper mapper = new VisualizarRequerimientoMapper();

            VisualizarRequerimiento data = mapper.mapRequerimientoToDetalle(requerimiento);

            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(200)
            .message("Se han encontrado requerimiento con id: " + requerimientoId)
            .data(data)
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
        @RequestBody NewRequerimientoRequest requestBody
    ) {

        try {
            
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
            Integer usuarioEmisorId = currentUser.getUserId();

            Usuario emisor = usuarioService.getUsuarioById(usuarioEmisorId);

            Integer tipoRequerimientoId = requestBody.getTipoRequerimientoId();
            TipoRequerimiento tipoRequerimiento = tipoRequerimientoService.getTipoRequerimientoById(tipoRequerimientoId);

            Integer categoriaRequerimientoId = requestBody.getCategoriaRequerimientoId();
            CategoriaRequerimiento categoriaRequerimiento = categoriaRequerimientoService.getCategoriaRequerimientoById(categoriaRequerimientoId);
        
            UsuarioInterno propietario = null;
            Integer usuarioPropietarioId = requestBody.getUsuarioPropietarioId();
            if (usuarioPropietarioId != 0 && usuarioPropietarioId != null) { 
                propietario = (UsuarioInterno) usuarioService.getUsuarioById(usuarioPropietarioId);
            }
        
            List<Requerimiento> listaRequerimientosRelacionados = new ArrayList<>();
            List<Integer> listaRequerimientosId = requestBody.getListaRequerimientosId();
            for (Integer id : listaRequerimientosId) {
                Requerimiento requerimiento = requerimientoService.getRequerimientoById(id);
                listaRequerimientosRelacionados.add(requerimiento);
            }
                
            NewRequerimientoData data = RequerimientoMapper.mapBodyRequestToData(
                requestBody, 
                propietario, 
                emisor, 
                tipoRequerimiento,
                categoriaRequerimiento,
                listaRequerimientosRelacionados
            );
            
            Requerimiento requerimiento = registrarRequerimientoService.registrarRequerimiento(data);

            eventoService.registrarEvento(requerimiento, emisor, "Alta Requerimiento");

            Email email = new EmailBuilder()
            .buildAsunto("Nuevo Requerimiento " + requerimiento.getCodigo() + " Registrado en el Sistema")
            .buildTemplateHtml("notificarAltaRequerimiento")
            .buildDestinatario(currentUser.getEmail())
            .buildContext("nombre", currentUser.getName())
            .buildContext("codigo", requerimiento.getCodigo())
            .buildContext("tipo", tipoRequerimiento.getDescripcion())
            .buildContext("categoria", categoriaRequerimiento.getDescripcion())
            .buildContext("asunto", requerimiento.getAsunto())
            .buildContext("descripcion", requerimiento.getDescripcion())
            .build();

            emailService.sendEmail(email);

            if (propietario != null) {
                
                email = new EmailBuilder()
                .buildAsunto("Requerimiento " + requerimiento.getCodigo() + " Asignado")
                .buildTemplateHtml("notificarAsignacionRequerimiento")
                // .buildDestinatario(propietario.getEmail())
                .buildDestinatario(currentUser.getEmail())
                .buildContext("nombre", propietario.getNombre())
                .buildContext("codigo", requerimiento.getCodigo())
                .buildContext("tipo", tipoRequerimiento.getDescripcion())
                .buildContext("categoria", categoriaRequerimiento.getDescripcion())
                .buildContext("asunto", requerimiento.getAsunto())
                .buildContext("descripcion", requerimiento.getDescripcion())
                .build();
    
                emailService.sendEmail(email);
            }

            RequerimientoResponse bodyResponse = RequerimientoMapper.mapRequerimientoToResonse(requerimiento);

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
                return ResponseFactory.badRequest(e.getMessage());
        }   catch (NotFoundException e) {
                return ResponseFactory.errorNotFound(e.getMessage());
        } catch (Exception e) {
                return ResponseFactory.internalServerError();
        }
    }

    @PutMapping("/{requerimientoId}/estado/cerrado")
    public ResponseEntity<HttpBodyResponse> cerrarRequerimiento(
        @PathVariable Integer requerimientoId
    ) {

        try {
            
            Requerimiento requerimiento = requerimientoService.getRequerimientoById(requerimientoId);
            
            cerrarRequerimientoService.cerrarRequerimiento(requerimiento);
            
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
            Integer usuarioId = currentUser.getUserId();

            Usuario usuarioEmisor = usuarioService.getUsuarioById(usuarioId);
            
            eventoService.registrarEvento(requerimiento, usuarioEmisor,"Cierre del Caso");

            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(200)
            .message("Se ha cerrado el requerimiento con exito")
            .data(null)
            .build();
            
            return ResponseEntity
            .status(200)
            .body(response);

        } catch(BadRequestException e) {
                return ResponseFactory.badRequest(e.getMessage());
        }   catch (ForbiddenException e) {
                return ResponseFactory.errorForbidden();
        }   catch (UnauthorizedException e) {
                return ResponseFactory.unauthorizedError();
        }   catch (NotFoundException e) {
                return ResponseFactory.errorNotFound(e.getMessage());
        } catch (Exception e) {
                return ResponseFactory.internalServerError();
        }
    }
    
    @PutMapping("/{requerimientoId}/propietarios/{propietarioId}")
    public ResponseEntity<HttpBodyResponse> asignarPropietario(
        @PathVariable Integer requerimientoId,
        @PathVariable Integer propietarioId
    ) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
            String role = currentUser.getRole();
            
            if (role.equalsIgnoreCase("EXTERNO")) throw new ForbiddenException();
            
            UsuarioInterno nuevoUsuarioPropietario = (UsuarioInterno) usuarioService.getUsuarioById(propietarioId);

            Requerimiento requerimiento = requerimientoService.getRequerimientoById(requerimientoId);
            
            AsignarRequerimientoService.asignarRequerimiento(requerimiento, nuevoUsuarioPropietario);

            Integer usuarioId = currentUser.getUserId();
            Usuario usuarioEmisor = usuarioService.getUsuarioById(usuarioId);

            eventoService.registrarEvento(requerimiento, usuarioEmisor, "Requerimiento Asignado");
            
            HttpBodyResponse response = new HttpBodyResponse
            .Builder()
            .status("Success")
            .statusCode(200)
            .message("El requerimiento se ha asignado con exito")
            .data(null)
            .build();
            
            return ResponseEntity
            .status(200)
            .body(response);

        } catch(BadRequestException e) {
                return ResponseFactory.badRequest(e.getMessage());
        }   catch (ForbiddenException e) {
                return ResponseFactory.errorForbidden();
        }   catch (UnauthorizedException e) {
                return ResponseFactory.unauthorizedError();
        }   catch (NotFoundException e) {
                return ResponseFactory.errorNotFound(e.getMessage());
        } catch (Exception e) {
                return ResponseFactory.internalServerError();
        }
    }
}