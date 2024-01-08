package com.facturacion.plasticsdeharo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.facturacion.plasticsdeharo.entity.FacturaClientesHeader;

@Repository
public interface FacturaClientesHeaderRepository extends JpaRepository<FacturaClientesHeader, Long> {
}