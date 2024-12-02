package com.issue_tracker.issue_tracker.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.issue_tracker.issue_tracker.dto.NewRequerimientoData;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.repository.RequerimientoCodigoRepository;
import com.issue_tracker.issue_tracker.repository.RequerimientoRepository;
import com.issue_tracker.issue_tracker.repository.TipoRequerimientoRepository;
import jakarta.transaction.Transactional;

@Service
public class RequerimientoService {
    
    @Autowired
    private RequerimientoRepository requerimientoRepository;

    @Autowired
    private TipoRequerimientoRepository tipoRequerimientoRepository;

    @Autowired
    private RequerimientoCodigoRepository requerimientoCodigoRepository;
  
    @Transactional
    public Requerimiento createNewRequerimiento(NewRequerimientoData data) {

        Requerimiento.Builder builder = new Requerimiento.Builder();
        
        builder.setAsunto(data.getAsunto());
        builder.setDescripcion(data.getDescripcion());
        builder.setPrioridad(data.getPrioridad());
        builder.setUsuarioEmisor(data.getUsuarioEmisor());
        
        String tipoUsuario = data.getTipoUsuario();
        if (tipoUsuario == "interno") {
            builder.setUsuarioPropietario(data.getUsuarioPropietario());
        }
             
        // Long requerimentNumber = requerimientoRepository.count() + 1;
        TipoRequerimiento tipoRequerimiento = data.getTipoRequerimiento();
        Integer requerimentNumber = requerimientoCodigoRepository.getCodigo(tipoRequerimiento.getCodigo(), 2024);
        builder.setCodigo(tipoRequerimiento, requerimentNumber);

        Requerimiento requerimiento = builder.build();

        requerimientoRepository.save(requerimiento);

        return requerimiento;
    }

    public TipoRequerimiento getTipoRequerimientoById(Integer tipoRequerimientoId) {

        TipoRequerimiento tipoRequerimiento = tipoRequerimientoRepository.findById(tipoRequerimientoId).orElse(null);

        if(tipoRequerimiento == null) {
            // TODO
            // agregar logica para devolver un error
            return null;
        }

        return tipoRequerimiento;
    }

    public List<Requerimiento> getRequerimientoByUsuarioEmisorId(Integer userId) {
        List<Requerimiento> requerimientos = requerimientoRepository.findByUsuarioEmisorId(userId);
        return requerimientos;
    }
}