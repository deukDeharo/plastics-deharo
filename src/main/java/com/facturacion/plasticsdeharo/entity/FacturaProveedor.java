package com.facturacion.plasticsdeharo.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FacturaProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;
    private Long codigoProveedor;
    private LocalDate fecha;
    private Double total;
}