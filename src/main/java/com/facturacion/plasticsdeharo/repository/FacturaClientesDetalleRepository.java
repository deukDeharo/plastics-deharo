package com.facturacion.plasticsdeharo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.facturacion.plasticsdeharo.entity.FacturaClientesDetalle;

@Repository
public interface FacturaClientesDetalleRepository extends JpaRepository<FacturaClientesDetalle, Long> {
    List<FacturaClientesDetalle> findByCodigoFacturaHeader(Long codigo);

    @Modifying
    @Query("DELETE FROM FacturaClientesDetalle fd WHERE fd.codigoFacturaHeader = :codigoFacturaHeader")
    void deleteByCodigoFacturaHeader(@Param("codigoFacturaHeader") Long codigoFacturaHeader);
}