package com.issue_tracker.issue_tracker.Builder.Requerimiento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.issue_tracker.issue_tracker.model.CategoriaRequerimiento;
import com.issue_tracker.issue_tracker.model.Evento;
import com.issue_tracker.issue_tracker.model.Requerimiento;
import com.issue_tracker.issue_tracker.model.TipoRequerimiento;
import com.issue_tracker.issue_tracker.model.Usuario;

public class RequerimientoBuilder {
    
    private String codigo;
    private String descripcion;
    private String asunto;
    private String prioridad;
    private String estado = "Abierto";
    private TipoRequerimiento tipoRequerimiento;
    private CategoriaRequerimiento categoriaRequerimiento;
    private Usuario usuarioEmisor;
    private Usuario usuarioPropietario;
    // private List<ArchivoAdjunto> archivosAdjuntos = new ArrayList<>();
    // private List<Requerimiento> requerimientosRelacionados = new ArrayList<>();
    // private List<Comentario> listaComentarios = new ArrayList<>();
    private List<Evento> listaEventos = new ArrayList<>();

    public RequerimientoBuilder() { }

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

    public RequerimientoBuilder setTipoRequerimiento(TipoRequerimiento value) {
        this.tipoRequerimiento = value;
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

    public RequerimientoBuilder setUsuarioPropietario(Usuario value) {
        this.usuarioPropietario = value;
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

    public RequerimientoBuilder setEstado(String estado) {
        this.estado = estado;
        return this;
    }

    public RequerimientoBuilder buildEvento(Evento evento) {
        this.listaEventos.add(evento);
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
        requerimiento.setCreatedAt(LocalDateTime.now());
        requerimiento.setUpdatedAt(LocalDateTime.now());

        return requerimiento;
    }
}