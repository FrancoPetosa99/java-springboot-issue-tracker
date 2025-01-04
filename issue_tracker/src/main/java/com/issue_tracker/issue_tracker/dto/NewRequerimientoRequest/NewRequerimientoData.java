package com.issue_tracker.issue_tracker.dto.NewRequerimientoRequest;

import java.util.List;

import com.issue_tracker.issue_tracker.model.ArchivoAdjunto;
import com.issue_tracker.issue_tracker.model.CategoriaRequerimiento;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;

import lombok.Data;

@Data
public class NewRequerimientoData {
    private String asunto;
    private String descripcion;
    private String estado;
    private String prioridad;
    private String tipoUsuario;
    private TipoRequerimiento tipoRequerimiento;
    private CategoriaRequerimiento categoriaRequerimiento;
    private Usuario usuarioEmisor;
    private UsuarioInterno usuarioPropietario;
    private List<ArchivoAdjunto> listaArchivos;
    private List<Requerimiento> listaRequerimientos;
}