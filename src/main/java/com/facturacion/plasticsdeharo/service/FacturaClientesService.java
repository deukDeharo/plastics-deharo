package com.facturacion.plasticsdeharo.service;

import java.util.List;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.facturacion.plasticsdeharo.dto.DetalleFacturaDTO;
import com.facturacion.plasticsdeharo.dto.FacturaDTO;
import com.facturacion.plasticsdeharo.dto.HeaderFacturaDTO;
import com.facturacion.plasticsdeharo.entity.FacturaClientesDetalle;
import com.facturacion.plasticsdeharo.entity.FacturaClientesHeader;
import com.facturacion.plasticsdeharo.exceptions.FacturaCreationException;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class FacturaClientesService {

    private final FacturaClientesDetalleService fDetalleService;
    private final FacturaClientesHeaderService fHeaderService;

    public List<FacturaClientesHeader> filterFacturas(Long codigoFactura, Long codigoCliente) {
        if (codigoFactura != null && codigoCliente != null) {
            return fHeaderService.getByCodigoFacturaAndCodigoCliente(codigoFactura, codigoCliente);
        } else if (codigoFactura != null) {
            return fHeaderService.getByCodigoFactura(codigoFactura);
        } else if (codigoCliente != null) {
            return fHeaderService.getByCodigoCliente(codigoCliente);
        } else {
            return fHeaderService.getAllFacturaClientesHeader();
        }
    }

    public Boolean createFacturaByFacturaDTO(FacturaDTO dto) {
        FacturaClientesHeader header = fHeaderService.serializeHeader(dto.getHeader());
        List<FacturaClientesDetalle> detalle = fDetalleService.serializeDetalles(dto.getDetalle());
        return createFactura(header, detalle);
    }

    @Transactional
    public Boolean createFactura(FacturaClientesHeader header, List<FacturaClientesDetalle> detalle) {
        try {
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

    public FacturaDTO getFacturaById(Long id) {
        FacturaDTO facturaDTO = new FacturaDTO();
        FacturaClientesHeader entity = fHeaderService.getFacturaClientesHeaderById(id);
        HeaderFacturaDTO dto = serializeHeaderDto(entity);
        List<DetalleFacturaDTO> detalleDto = serializeDetailDto(entity);
        facturaDTO.setHeader(dto);
        facturaDTO.setDetalle(detalleDto);
        facturaDTO.setIsGenerated(entity.getIsGenerated());
        return facturaDTO;
    }

    private List<DetalleFacturaDTO> serializeDetailDto(FacturaClientesHeader entity) {
        List<DetalleFacturaDTO> detalles = new ArrayList<>();
        List<FacturaClientesDetalle> facturaDetalles = fDetalleService
                .getFacturaClientesDetalleByHeaderId(entity.getCodigoFactura());
        for (FacturaClientesDetalle facturaClientesDetalle : facturaDetalles) {
            DetalleFacturaDTO dto = new DetalleFacturaDTO();
            dto.setUnidades(facturaClientesDetalle.getUnidad().toString());
            dto.setCodigoArticulo(facturaClientesDetalle.getCodigoArticulo().toString());
            detalles.add(dto);
        }
        return detalles;
    }

    private HeaderFacturaDTO serializeHeaderDto(FacturaClientesHeader entity) {
        HeaderFacturaDTO dto = new HeaderFacturaDTO();
        dto.setCodigoCliente(entity.getCodigoCliente());
        dto.setFecha(entity.getDate().toString());
        dto.setFechaVencimiento(entity.getDateVencimiento().toString());
        dto.setIdFactura(entity.getCodigoFactura());
        dto.setIva(entity.getIva());
        return dto;
    }

    @Transactional
    public Boolean updateFacturaByFacturaDTO(FacturaDTO dto) {
        try {
            
            FacturaClientesHeader updatedHeader = fHeaderService.serializeHeader(dto.getHeader());
            List<FacturaClientesDetalle> updatedDetalle = fDetalleService.serializeDetalles(dto.getDetalle());
            updatedHeader.setCodigoFactura(dto.getHeader().getIdFactura());

            // Update header and detalle
            FacturaClientesHeader existingHeader = fHeaderService
                    .getFacturaClientesHeaderById(updatedHeader.getCodigoFactura());
            updatedHeader.setCodigoFactura(existingHeader.getCodigoFactura()); // Maintain existing ID
            fHeaderService.updateFacturaClientesHeader(updatedHeader);

            // Delete old detalle entries and add updated ones
            List<FacturaClientesDetalle> existingDetalles = fDetalleService
                    .getFacturaClientesDetalleByHeaderId(existingHeader.getCodigoFactura());
            for (FacturaClientesDetalle detalle : existingDetalles) {
                fDetalleService.deleteFacturaClientesDetalle(detalle.getDetalleId());
            }
            for (FacturaClientesDetalle detalle : updatedDetalle) {
                detalle.setCodigoFacturaHeader(existingHeader.getCodigoFactura());
                fDetalleService.createFacturaClientesDetalle(detalle);
            }
            return true;
        } catch (Exception e) {
            throw new FacturaCreationException("Error updating factura", e);
        }
    }

    @Transactional
    public boolean deleteFacturaCliente(Long id) {
        try {
            fHeaderService.deleteFacturaClientesHeader(id);
            fDetalleService.deleteFacturaClientesDetallesByCodigoFactura(id);
            return true;
        } catch (Exception e) {
            log.info("error de actualizacion debido a " + e.getMessage());
            return false;
        }
    }

}
