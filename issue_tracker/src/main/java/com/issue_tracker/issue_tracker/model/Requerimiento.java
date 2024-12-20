package com.issue_tracker.issue_tracker.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.issue_tracker.issue_tracker.State.Requerimiento.RequerimientoState;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "requerimientos")
@Data
@AllArgsConstructor
public class Requerimiento {

    @Transient
    private RequerimientoState stateContext;

    public void setStateContext(RequerimientoState stateContext) {
        this.stateContext = stateContext;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Integer id;
    
    @Column(name = "codigo", nullable = false)
    private final String codigo;
    
    @Column(name = "descripcion", nullable = false, length = 5000)
    private String descripcion;

    @Column(name = "asunto", nullable = false, length = 50)
    private String asunto;
    
    @Column(name = "prioridad", nullable = false)
    private String prioridad;
    
    @Column(name = "estado", nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'Abierto'")
    private String estado;

    @OneToMany(mappedBy = "requerimiento", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ArchivoAdjunto> listaArchivos;
    
    @OneToMany(mappedBy = "requerimiento", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comentario> listaComentarios;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "tipo_requerimiento_id")
    private final TipoRequerimiento tipoRequerimiento;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "categoria_requerimiento_id")
    private final CategoriaRequerimiento categoriaRequerimiento;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "usuario_emisor_id")
    private final Usuario usuarioEmisor;

    @ManyToMany
    @JoinTable(
        name = "requerimiento_relacionado",
        joinColumns = @JoinColumn(name = "requerimiento_id"),
        inverseJoinColumns = @JoinColumn(name = "requerimiento_relacionado_id")
    )
    private List<Requerimiento> requerimientosRelacionados;

    @ManyToOne
    @JoinColumn(name = "usuario_propietario_id")
    private Usuario usuarioPropietario;

    public void asignarNuevoPropietario(Usuario usuarioPropietario) 
    throws Exception {
        this.stateContext.asignarNuevoPropietario(usuarioPropietario);
    }

    public void agregarComentario(Comentario comentario) 
    throws Exception {
        this.stateContext.agregarComentario(comentario);
    }
    
    @Column(name = "deleted_at", updatable = false)
    private LocalDateTime deletedAt;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private final LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // constructor protegido para que JPA instancie objetos
    protected Requerimiento() {
        this.id = null;
        this.codigo = null;
        this.createdAt = null;
        this.tipoRequerimiento = null;
        this.categoriaRequerimiento = null;
        this.usuarioEmisor = null;
    }

    public void addRequerimiento(Requerimiento requerimiento) {
        this.requerimientosRelacionados.add(requerimiento);
    }

    public void addArchivoAdjunto(ArchivoAdjunto archivo) {
        archivo.setRequerimiento(this);
        this.listaArchivos.add(archivo);
    }
    
    public static class Builder {

        private String codigo;
        private String descripcion;
        private String asunto;
        private String prioridad;
        private String estado = "Abierto";
        private TipoRequerimiento tipoRequerimiento;
        private CategoriaRequerimiento categoriaRequerimiento;
        private Usuario usuarioEmisor;
        private Usuario usuarioPropietario;
        private List<ArchivoAdjunto> archivosAdjuntos = new ArrayList<>();
        private List<Requerimiento> requerimientosRelacionados = new ArrayList<>();
        private List<Comentario> listaComentarios = new ArrayList<>();

        public Builder() { }

        public Builder setDescripcion(String value) {
            this.descripcion = value;
            return this;
        }

        public Builder setAsunto(String value) {
            this.asunto = value;
            return this;
        }

        public Builder setPrioridad(String value) {
            this.prioridad = value;
            return this;
        }

        public Builder setTipoRequerimiento(TipoRequerimiento value) {
            this.tipoRequerimiento = value;
            return this;
        }

        public Builder setCategoriaRequerimiento(CategoriaRequerimiento value) {
            this.categoriaRequerimiento = value;
            return this;
        }

        public Builder setUsuarioEmisor(Usuario value) {
            this.usuarioEmisor = value;
            return this;
        }

        public Builder setUsuarioPropietario(Usuario value) {
            this.usuarioPropietario = value;
            return this;
        }

        public Builder setCodigo(TipoRequerimiento tipo, Integer number) {
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

        public Builder setArchivosAdjuntos(List<ArchivoAdjunto> listaArchivos) {
            this.archivosAdjuntos = listaArchivos;
            return this;
        }

        public Builder setRequerimientosRelacionados(List<Requerimiento> listaRequerimientos) {
            this.requerimientosRelacionados = listaRequerimientos;
            return this;
        }

        public Builder setEstado(String estado) {
            this.estado = estado;
            return this;
        }

        public Requerimiento build() {

            Requerimiento requerimiento = new Requerimiento(
                null,
                null,
                this.codigo,
                this.descripcion,
                this.asunto,
                this.prioridad,
                this.estado,
                this.archivosAdjuntos,
                this.listaComentarios,
                this.tipoRequerimiento,
                this.categoriaRequerimiento,
                this.usuarioEmisor,
                this.requerimientosRelacionados,
                this.usuarioPropietario,
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
            );

            return requerimiento;
        }
    }
}