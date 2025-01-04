package com.issue_tracker.issue_tracker.Builder.Requerimiento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.issue_tracker.issue_tracker.model.ArchivoAdjunto;
import com.issue_tracker.issue_tracker.model.CategoriaRequerimiento;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;
import com.issue_tracker.issue_tracker.model.UsuarioInterno;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequerimientoBuilder {
    
    private String codigo;
    private String descripcion;
    private String asunto;
    private String prioridad;
    private String estado = "Abierto";
    private TipoRequerimiento tipoRequerimiento;
    private CategoriaRequerimiento categoriaRequerimiento;
    private Usuario usuarioEmisor;
    private UsuarioInterno usuarioPropietario;
    private List<Requerimiento> listaRequerimientos = new ArrayList<>();
    private List<ArchivoAdjunto> listaArchivosAdjuntos = new ArrayList<>();

    public RequerimientoBuilder setDescripcion(String value) {
        this.descripcion = value;
        return this;
    }

    public RequerimientoBuilder setAsunto(String value) {
        this.asunto = value;
        return this;
    }

    public RequerimientoBuilder setPrioridad(String value) {
        this.prioridad = value;
        return this;
    }

    public RequerimientoBuilder setCategoriaRequerimiento(CategoriaRequerimiento value) {
        this.categoriaRequerimiento = value;
        return this;
    }

    public RequerimientoBuilder setUsuarioEmisor(Usuario value) {
        this.usuarioEmisor = value;
        return this;
    }

    public RequerimientoBuilder setUsuarioPropietario(UsuarioInterno propietario) {
        this.usuarioPropietario = propietario;
        if (propietario != null) this.estado = "Asignado";
        return this;
    }

    public RequerimientoBuilder setCodigo(TipoRequerimiento tipo, Integer number) {

        this.tipoRequerimiento = tipo;

        Integer digits = String.valueOf(Math.abs(number)).length();
        String secuence = "";
        for (int i = 0; i < 10 - digits; i++) {
            secuence = secuence + "0";
        }

        secuence = secuence + number;
        this.codigo = tipoRequerimiento.getCodigo() + "-" + LocalDateTime.now().getYear() + "-" + secuence;
        return this;
    }

    public RequerimientoBuilder setListaRequerimientos(List<Requerimiento> lista) {
        this.listaRequerimientos = lista;
        return this;
    }

    public RequerimientoBuilder setListaArchivosAdjuntos(List<ArchivoAdjunto> lista) {
        this.listaArchivosAdjuntos = lista;
        return this;
    }

    public Requerimiento build() {

        Requerimiento requerimiento = new Requerimiento();
        
        requerimiento.setCodigo(this.codigo);
        requerimiento.setDescripcion(this.descripcion);
        requerimiento.setAsunto(this.asunto);
        requerimiento.setPrioridad(this.prioridad);
        requerimiento.setEstado(this.estado);
        requerimiento.setTipoRequerimiento(this.tipoRequerimiento);
        requerimiento.setCategoriaRequerimiento(this.categoriaRequerimiento);
        requerimiento.setUsuarioEmisor(this.usuarioEmisor);
        requerimiento.setUsuarioPropietario(this.usuarioPropietario);
        requerimiento.setListaArchivos(this.listaArchivosAdjuntos);
        requerimiento.setRequerimientosRelacionados(this.listaRequerimientos);
        requerimiento.setListaEventos(new ArrayList<>());
        requerimiento.setListaComentarios(new ArrayList<>());
        requerimiento.setCreatedAt(LocalDateTime.now());
        requerimiento.setUpdatedAt(LocalDateTime.now());

        return requerimiento;
    }
}