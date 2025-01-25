package com.facturacion.plasticsdeharo.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.facturacion.plasticsdeharo.dto.DetalleFacturaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FacturaClientesDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long detalleId;
    private Long codigoFacturaHeader;
    private Long codigoArticulo;
    private String conceptoArticulo;
    private BigDecimal  precio;
    private Integer unidad;
    private BigDecimal importe;
    private LocalDate createdAt;
    private LocalDateTime updatedAt;

    public FacturaClientesDetalle(DetalleFacturaDTO dto) {
        this.codigoArticulo = Long.parseLong(dto.getCodigoArticulo());
        this.unidad = Integer.parseInt(dto.getUnidades());
    }

        @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now(); // Fecha de creación
        this.updatedAt = LocalDateTime.now(); // Fecha y hora de creación
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now(); // Fecha y hora de actualización
    }

}
