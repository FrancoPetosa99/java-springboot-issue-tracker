package com.issue_tracker.issue_tracker.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "requerimientos")
@Data
@NoArgsConstructor
public class Requerimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "codigo", nullable = false)
    private String codigo;
    
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

    @OneToMany(mappedBy = "requerimiento", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Evento> listaEventos;
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "tipo_requerimiento_id")
    private TipoRequerimiento tipoRequerimiento;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "categoria_requerimiento_id")
    private CategoriaRequerimiento categoriaRequerimiento;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "usuario_emisor_id")
    private Usuario usuarioEmisor;

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

    @Column(name = "deleted_at", updatable = false)
    private LocalDateTime deletedAt;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void addRequerimiento(Requerimiento requerimiento) {
        this.requerimientosRelacionados.add(requerimiento);
    }

    public void addArchivoAdjunto(ArchivoAdjunto archivo) {
        archivo.setRequerimiento(this);
        if(this.listaArchivos == null) {
            this.listaArchivos = new ArrayList<>();
        }
        this.listaArchivos.add(archivo);
    }

    public void asignarNuevoPropietario(Usuario usuarioPropietario) {
        this.usuarioPropietario = usuarioPropietario;
    }

    public void agregarComentario(Comentario comentario) {
        comentario.setRequerimiento(this);
        this.listaComentarios.add(comentario);
    }

    public void addEvento(Evento evento) {
        evento.setRequerimiento(this);
        this.listaEventos.add(evento);
    }
}