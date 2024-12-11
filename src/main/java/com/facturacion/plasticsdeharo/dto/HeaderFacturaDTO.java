package com.facturacion.plasticsdeharo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeaderFacturaDTO {
    private String fecha;
    private Long codigoCliente;
    private Integer iva;
    private String fechaVencimiento;
    private Long idFactura;

}