package com.facturacion.plasticsdeharo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturacion.plasticsdeharo.entity.FacturaClientesDetalle;

@Repository
public interface FacturaClientesDetalleRepository extends JpaRepository<FacturaClientesDetalle, Long> {
}