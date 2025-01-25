package com.facturacion.plasticsdeharo.dto;

import lombok.Data;

@Data
public class FacturaProveedorDTO {
    private Long codigo;
    private Long codigoProveedor;
    private String fecha;
    private Double total;
    private String proveedor;
}
