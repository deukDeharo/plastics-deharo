package com.facturacion.plasticsdeharo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturacion.plasticsdeharo.entity.Articulo;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {
}