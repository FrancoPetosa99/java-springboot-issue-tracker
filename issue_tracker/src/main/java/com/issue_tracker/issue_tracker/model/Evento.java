package com.issue_tracker.issue_tracker.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "eventos")
@Data
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "accion")
    private String accion;
    
    @ManyToOne
    @JoinColumn(name = "requerimiento_id")
    private Requerimiento requerimiento;
    
    @ManyToOne
    @JoinColumn(name = "emisor_usuario_id")
    private Usuario emisorUsuario;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static class Builder {

        private String accion;
        private Requerimiento requerimiento;
        private Usuario emisorUsuario;

        public Builder buildActionTypeComentario() {
            this.accion = "Emision de Respuesta";
            return this;
        }

        public Builder buildActionTypeAltaRequerimiento() {
            this.accion = "Alta de Requerimiento";
            return this;
        }

        public Builder buildActionTypeAssignacion() {
            this.accion = "Asignacion de Caso";
            return this;
        }

        public Builder buildActionTypeCierreDeCaso() {
            this.accion = "Asignación de Caso";
            return this;
        }

        public Builder buildRequerimiento(Requerimiento requerimiento) {
            this.requerimiento = requerimiento;
            return this;
        }

        public Builder buildUsuarioEmisor(Usuario emisor) {
            this.emisorUsuario = emisor;
            return this;
        }

        public Evento build() {

            Evento evento = new Evento();

            evento.setAccion(this.accion);
            evento.setRequerimiento(this.requerimiento);
            evento.setEmisorUsuario(this.emisorUsuario);
            evento.setCreatedAt(LocalDateTime.now());
            evento.setUpdatedAt(LocalDateTime.now());

            return evento;
        } 
    }
}