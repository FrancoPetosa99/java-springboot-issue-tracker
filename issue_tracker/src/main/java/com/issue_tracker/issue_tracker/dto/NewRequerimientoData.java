package com.issue_tracker.issue_tracker.dto;

import java.util.List;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import lombok.Data;

@Data
public class NewRequerimientoData {
    private String asunto;
    private String descripcion;
    private String estado;
    private String prioridad;
    private String tipoUsuario;
    private TipoRequerimiento tipoRequerimiento;
    private Usuario usuarioEmisor;
    private Usuario usuarioPropietario;
    private List<String> listaArchivos;
    private List<Requerimiento> listaRequerimientos;
}
