package com.facturacion.plasticsdeharo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleFacturaDTO {
    private String codigoArticulo;
    private String unidades;
    private BigDecimal precio;
    private Integer unidad;
    private BigDecimal importe;
    private LocalDate createdAt;
    private String conceptoArticulo;
}
