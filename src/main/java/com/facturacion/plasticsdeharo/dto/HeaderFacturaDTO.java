package com.facturacion.plasticsdeharo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeaderFacturaDTO {
    private String fecha;
    private String codigoCliente;
    private String iva;
    private String fechaVencimiento;

}