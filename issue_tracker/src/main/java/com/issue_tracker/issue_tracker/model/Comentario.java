package com.issue_tracker.issue_tracker.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "comentarios")
@Data
@AllArgsConstructor
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Integer id;
    
    @Column(name = "asunto", nullable = false)
    private String asunto;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "deleted_at", updatable = false)
    private LocalDateTime deletedAt;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private final LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "requerimiento_id")
    private Requerimiento requerimiento;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "emisor_id")
    private Usuario usuarioEmisor;

    @OneToMany(mappedBy = "comentario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ArchivoAdjunto> listaArchivos;

    public static class Builder {

        private String asunto;
        private String descripcion;
        private List<ArchivoAdjunto> listaArchivos = new ArrayList<>();
        private Usuario usuarioEmisor;
        private Requerimiento requerimiento;

        public Builder() { }

        public Builder buildAsunto(String asunto) {
            this.asunto = asunto;
            return this;
        }

        public Builder buildDescripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder buildListaArchivos(List<ArchivoAdjunto> listaArchivos) {
            this.listaArchivos = listaArchivos;
            return this;
        }

        public Builder buildUsuarioEmisor(Usuario emisor) {
            this.usuarioEmisor = emisor;
            return this;
        }

        public Builder buildRequerimiento(Requerimiento requerimiento) {
            this.requerimiento = requerimiento;
            return this;
        }

        public Comentario build() {
            
            Comentario comentario = new Comentario(
                null, 
                this.asunto, 
                this.descripcion, 
                null, 
                LocalDateTime.now(), 
                LocalDateTime.now(), 
                this.requerimiento, 
                this.usuarioEmisor, 
                this.listaArchivos
            );

            return comentario;
        }
    }
}
