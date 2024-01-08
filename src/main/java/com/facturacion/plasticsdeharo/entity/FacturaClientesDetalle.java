package com.facturacion.plasticsdeharo.entity;


import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FacturaClientesDetalle {
    @Id
    private Long detalleId;
    private Long codigoFacturaHeader;
    private Long codigoArticulo;
    private String conceptoArticulo;
    private Double precio;
    private Integer unidad;
    private Long importe;
}
