package com.facturacion.plasticsdeharo.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FacturaClientesHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private LocalDate dateVencimiento;
    private String banco;
    private Boolean isGenerated;
    private Integer iva;

}
