package com.facturacion.plasticsdeharo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.plasticsdeharo.entity.FacturaProveedor;

import java.time.LocalDate;
import java.util.List;

public interface FacturaProveedorRepository extends JpaRepository<FacturaProveedor, Long> {
    List<FacturaProveedor> findByCodigoProveedor(Long codigoProveedor);
    List<FacturaProveedor> findByFechaBetween(LocalDate startDate, LocalDate endDate);
}

