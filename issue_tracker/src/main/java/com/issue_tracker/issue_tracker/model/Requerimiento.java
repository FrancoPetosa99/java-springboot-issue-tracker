package com.issue_tracker.issue_tracker.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "requerimientos")
@Data
@RequiredArgsConstructor
public class Requerimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "codigo", nullable = false)
    private final String codigo;
    
    @Column(name = "descripcion", nullable = false, length = 5000)
    private final String descripcion;

    @Column(name = "asunto", nullable = false, length = 50)
    private final String asunto;
    
    @Column(name = "prioridad", nullable = false)
    private final String prioridad;
    
    @Column(name = "estado", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'Abierto'")
    private String estado;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "tipo_requerimiento_id")
    private final TipoRequerimiento tipoRequerimiento;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "usuario_emisor_id")
    private final Usuario usuarioEmisor;

    @ManyToOne
    @JoinColumn(name = "usuario_propietario_id")
    private Usuario usuarioPropietario;
    
    @Column(name = "deleted_at", updatable = false)
    private LocalDateTime deletedAt;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private final LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Builder pattern
    public static class Builder {

        private String codigo;
        private String descripcion;
        private String asunto;
        private String prioridad;
        private String estado = "Abierto"; // Estado por defecto
        private TipoRequerimiento tipoRequerimiento;
        private Usuario usuarioEmisor;
        private Usuario usuarioPropietario;

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

        public Builder setUsuarioEmisor(Usuario value) {
            this.usuarioEmisor = value;
            return this;
        }

        public Builder setUsuarioPropietario(Usuario value) {
            this.usuarioPropietario = value;
            this.estado = "Asignado";
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

        public Requerimiento build() {

            // Verificar si los valores obligatorios están establecidos
            // if (this.codigo == null || this.descripcion == null || this.asunto == null || this.prioridad == null || 
            //     this.estado == null || this.tipoRequerimiento == null || this.usuarioEmisor == null) {
            //     throw new IllegalStateException("Faltan valores obligatorios para construir el requerimiento.");
            // }
    
            Requerimiento requerimiento = new Requerimiento(
                this.codigo,
                this.descripcion,
                this.asunto,
                this.prioridad,
                this.tipoRequerimiento,
                this.usuarioEmisor,
                LocalDateTime.now()
            );

            if (this.usuarioPropietario != null) requerimiento.setUsuarioPropietario(this.usuarioPropietario);
            
            requerimiento.setEstado(this.estado);
            requerimiento.setUpdatedAt(LocalDateTime.now());

            return requerimiento;
        }
    }
}