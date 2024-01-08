package com.facturacion.plasticsdeharo.entity;

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
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;
    private String concepto;
    private Double precio;
}