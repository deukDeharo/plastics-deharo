package com.facturacion.plasticsdeharo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.facturacion.plasticsdeharo.entity.FacturaClientesHeader;

@Repository
public interface FacturaClientesHeaderRepository extends JpaRepository<FacturaClientesHeader, Long> {
     List<FacturaClientesHeader> findByCodigoFactura(Long codigoFactura);

     List<FacturaClientesHeader> findByCodigoCliente(Long codigoCliente);
 
     List<FacturaClientesHeader> findByCodigoFacturaAndCodigoCliente(Long codigoFactura, Long codigoCliente);
}