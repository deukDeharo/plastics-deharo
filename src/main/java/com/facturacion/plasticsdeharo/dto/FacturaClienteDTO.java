package com.facturacion.plasticsdeharo.dto;

import java.time.LocalDate;
import java.util.List;

import com.facturacion.plasticsdeharo.entity.FacturaClientesDetalle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaClienteDTO {
    private Long codigoFactura;
    private LocalDate date;
    private Long codigoCliente;
    private String nifCliente;
    private String nombreCliente;
    private String localidadCliente;
    private String domicilioCliente;
    private String formaDePagoCliente;
    private String numeroDeCuentaCliente;
    private Long total;
    private Long totalConIva;
    private String formaPago;
    private LocalDate dateVencimiento;
    private String banco;
    private String cuentaCte;
    private Boolean isGenerated; 

    private List<FacturaClientesDetalle> detalle;
}
