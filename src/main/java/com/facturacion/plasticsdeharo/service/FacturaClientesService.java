package com.facturacion.plasticsdeharo.service;

import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.facturacion.plasticsdeharo.dto.DetalleFacturaDTO;
import com.facturacion.plasticsdeharo.dto.FacturaDTO;
import com.facturacion.plasticsdeharo.dto.HeaderFacturaDTO;
import com.facturacion.plasticsdeharo.entity.FacturaClientesDetalle;
import com.facturacion.plasticsdeharo.entity.FacturaClientesHeader;
import com.facturacion.plasticsdeharo.exceptions.FacturaCreationException;
import com.facturacion.plasticsdeharo.xls.FacturasClienteXLS;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class FacturaClientesService {

    private final FacturaClientesDetalleService fDetalleService;
    private final FacturaClientesHeaderService fHeaderService;
    private final FacturasClienteXLS xlsBuilder;

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

    public ByteArrayOutputStream printFacturaXls(Long id) throws Exception {
        // Usar ClassLoader para cargar el archivo desde el classpath
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("static/template.xlsx");

        if (inputStream == null) {
            throw new RuntimeException("No se pudo encontrar el archivo template.xlsx en static.");
        }

        // Crear un flujo de salida en memoria
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            // Llenar las celdas en las hojas
            xlsBuilder.llenarHojaFactura(workbook);
            xlsBuilder.llenarHojaAlbaran(workbook);

            // Escribir el contenido del archivo Excel en el flujo de salida
            workbook.write(outputStream);
        }

        return outputStream;
    }


}
