package com.issue_tracker.issue_tracker.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.model.UsuarioExterno;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;
import com.issue_tracker.issue_tracker.repository.TipoRequerimientoRepository;
import com.issue_tracker.issue_tracker.repository.UsuarioExternoRepository;

@Service
public class RequerimientoService {
    
    @Autowired
    private RequerimientoRepository requerimientoRepository;

    @Autowired
    private TipoRequerimientoRepository tipoRequerimientoRepository;

    @Autowired
    private UsuarioExternoRepository usuarioExternoRepository;
    
    public Requerimiento createNewRequerimiento(
        NewRequerimientoRequest newRequerimientoRequest,
        Integer usuarioEmisorId
        ) {
        
        Requerimiento requerimiento = new Requerimiento();
        
        // check if requirement type does exist
        Integer tipoRequerimientoId = newRequerimientoRequest.getTipoRequerimientoId();
        TipoRequerimiento tipoRequerimiento = tipoRequerimientoRepository.findById(tipoRequerimientoId).orElse(null);
        requerimiento.setTipoRequerimiento(tipoRequerimiento);
        
        // check if user does exist
        UsuarioExterno usuarioEmisor = usuarioExternoRepository.findById(usuarioEmisorId).orElse(null);
        if (usuarioEmisor == null) return null;
        requerimiento.setUsuarioEmisor(usuarioEmisor);

        // assign code to requirement
        Long requerimentNumber = requerimientoRepository.count() + 1;
        Integer digits = String.valueOf(Math.abs(requerimentNumber)).length();
        String secuence = "";
        for (int i = 0; i < 10 - digits; i++) {
            secuence = secuence + "0";
        }
        secuence = secuence + requerimentNumber;
        String code = tipoRequerimiento.getCodigo() + "-" + LocalDateTime.now().getYear() + "-" + secuence;
        requerimiento.setCodigo(code);

        requerimiento.setDescripcion(newRequerimientoRequest.getDescripcion());
        requerimiento.setPrioridad(newRequerimientoRequest.getPrioridad());
        requerimiento.setAsunto(newRequerimientoRequest.getAsunto());
        requerimiento.setEstado(newRequerimientoRequest.getEstado());
        requerimiento.setCreatedAt(LocalDateTime.now());
        requerimiento.setUpdatedAt(LocalDateTime.now());

        requerimientoRepository.save(requerimiento);

        return requerimiento;
    }

    public List<Requerimiento> getRequerimientoByUsuarioEmisorId(Integer userId) {
        List<Requerimiento> requerimientos = requerimientoRepository.findByUsuarioEmisorId(userId);
        return requerimientos;
    }
}