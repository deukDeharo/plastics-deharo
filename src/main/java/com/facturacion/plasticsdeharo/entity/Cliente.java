package com.facturacion.plasticsdeharo.entity;

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
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigoCliente;

    private String nif;
    private String nombre;
    private String localidad;
    private String domicilio;
    private String formaDePago;
    private String numeroDeCuenta;

}