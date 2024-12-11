package com.facturacion.plasticsdeharo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private Double precio;
    private Integer unidad;
    private Double importe;

    public FacturaClientesDetalle(DetalleFacturaDTO dto) {
        this.codigoArticulo = Long.parseLong(dto.getCodigoArticulo());
        this.unidad = Integer.parseInt(dto.getUnidades());
    }

}
