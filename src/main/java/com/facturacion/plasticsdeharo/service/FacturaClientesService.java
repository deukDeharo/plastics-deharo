package com.facturacion.plasticsdeharo.service;

import java.util.List;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.facturacion.plasticsdeharo.dto.FacturaDTO;
import com.facturacion.plasticsdeharo.entity.FacturaClientesDetalle;
import com.facturacion.plasticsdeharo.entity.FacturaClientesHeader;
import com.facturacion.plasticsdeharo.exceptions.FacturaCreationException;
import com.facturacion.plasticsdeharo.repository.FacturaClientesDetalleRepository;
import com.facturacion.plasticsdeharo.repository.FacturaClientesHeaderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FacturaClientesService {
    
    private final FacturaClientesDetalleService fDetalleService;
    private final FacturaClientesHeaderService fHeaderService;
    public List<FacturaClientesHeader> getAllFacturas() {
        return fHeaderService.getAllFacturaClientesHeader();
    }

    public Boolean createFacturaByFacturaDTO(FacturaDTO dto){
        FacturaClientesHeader header = fHeaderService.serializeHeader(dto.getHeader());
        List<FacturaClientesDetalle> detalle = fDetalleService.serializeDetalles(dto.getDetalle());
        return createFactura(header, detalle);
    }

    @Transactional
    public Boolean createFactura(FacturaClientesHeader header, List<FacturaClientesDetalle> detalle){
        try{
            FacturaClientesHeader newHeader = fHeaderService.createFacturaClientesHeader(header);
            List<FacturaClientesDetalle> newDetalles = new ArrayList<>();
            for (FacturaClientesDetalle facturaClientesDetalle : detalle) {
                facturaClientesDetalle.setCodigoFacturaHeader(newHeader.getCodigoFactura());
                fDetalleService.createFacturaClientesDetalle(facturaClientesDetalle);
            }
            return true;
        } catch (Exception e) {
            throw new FacturaCreationException("Error creating factura", e);
        }
    }
}
