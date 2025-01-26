package com.facturacion.plasticsdeharo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.facturacion.plasticsdeharo.entity.FacturaClientesHeader;

@Repository
public interface FacturaClientesHeaderRepository extends JpaRepository<FacturaClientesHeader, Long> {
     List<FacturaClientesHeader> findByCodigoFactura(Long codigoFactura);

     List<FacturaClientesHeader> findByCodigoCliente(Long codigoCliente);
 
     List<FacturaClientesHeader> findByCodigoFacturaAndCodigoCliente(Long codigoFactura, Long codigoCliente);

     // Filtrar por estado isGenerated
     List<FacturaClientesHeader> findByIsGenerated(boolean isGenerated, Sort sort);

     List<FacturaClientesHeader> findByDateBetween(LocalDate fechaDesde, LocalDate fechaHasta, Sort sort);

     List<FacturaClientesHeader> findByDateBetweenAndIsGenerated(LocalDate fechaDesde, LocalDate fechaHasta,
               boolean isGenerated, Sort sort);
}