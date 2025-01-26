package com.facturacion.plasticsdeharo.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import com.facturacion.plasticsdeharo.dto.HeaderFacturaDTO;
import com.facturacion.plasticsdeharo.entity.Cliente;
import com.facturacion.plasticsdeharo.entity.FacturaClientesDetalle;
import com.facturacion.plasticsdeharo.entity.FacturaClientesHeader;
import com.facturacion.plasticsdeharo.repository.FacturaClientesHeaderRepository;
import lombok.AllArgsConstructor;

import java.util.List;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class FacturaClientesHeaderService {

    private final FacturaClientesHeaderRepository fHeaderRepository;
    private final ClienteService clienteService;

    public List<FacturaClientesHeader> getAllFacturaClientesHeader() {
        Sort sort = getSort();

        return fHeaderRepository.findAll(sort);
    }

    private Sort getSort() {
        Sort sort = Sort.by(
        Order.asc("isGenerated"), // Orden descendente por 'isGenerated'
        Order.desc("date")        // Orden descendente por 'fecha'
        );
        return sort;
    }

    public FacturaClientesHeader getFacturaClientesHeaderById(Long id) {
        return fHeaderRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("FacturaClientesHeader no encontrado"));
    }

    public FacturaClientesHeader createFacturaClientesHeader(FacturaClientesHeader fHeader) {
        return fHeaderRepository.save(fHeader);
    }

    public FacturaClientesHeader updateFacturaClientesHeader(FacturaClientesHeader fHeader) {
        if (fHeaderRepository.findById(fHeader.getCodigoFactura()).isPresent()) {
            return fHeaderRepository.save(fHeader);
        }
        throw new EntityNotFoundException("FacturaClientesHeader no encontrado");
    }

    public void deleteFacturaClientesHeader(Long id) {
        fHeaderRepository.deleteById(id);
    }

    public  FacturaClientesHeader serializeHeader(HeaderFacturaDTO dto){
        FacturaClientesHeader header = new FacturaClientesHeader();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        header.setDate(LocalDate.parse(dto.getFecha(),formatter));
        header.setDateVencimiento(LocalDate.parse(dto.getFechaVencimiento(),formatter));
        header.setIsGenerated(false);
        header.setIva(dto.getIva());
        Cliente cliente = clienteService.getClienteById(dto.getCodigoCliente());
        header.setCodigoCliente(dto.getCodigoCliente());
        header.setNumeroDeCuentaCliente(cliente.getNumeroDeCuenta());
        header.setBanco("");
        header.setDomicilioCliente(cliente.getDomicilio());
        header.setFormaDePagoCliente(cliente.getFormaDePago());
        header.setNifCliente(cliente.getNif());
        header.setNombreCliente(cliente.getNombre());
        header.setLocalidadCliente(cliente.getLocalidad());
        return header;
    }

    public List<FacturaClientesHeader> getByCodigoFacturaAndCodigoCliente(Long codigoFactura, Long codigoCliente) {
        return fHeaderRepository.findByCodigoFacturaAndCodigoCliente(codigoFactura,codigoCliente);
    }

    public List<FacturaClientesHeader> getByCodigoFactura(Long codigoFactura) {
        return fHeaderRepository.findByCodigoFactura(codigoFactura);
    }

    public List<FacturaClientesHeader> getByCodigoCliente(Long codigoCliente) {
        return fHeaderRepository.findByCodigoCliente(codigoCliente);
    }

    public Cliente getClienteById(Long id){
            return clienteService.getClienteById(id);
    }

    public void generateFactura(Long id,List<FacturaClientesDetalle> detalles) {
        FacturaClientesHeader header = getFacturaClientesHeaderById(id);
        header.setIsGenerated(true);
        calculateTotals(header,detalles);
        updateFacturaClientesHeader(header);
    }

    private void calculateTotals(FacturaClientesHeader header,List<FacturaClientesDetalle> detalles) {
        BigDecimal acumuladoTotal = BigDecimal.ZERO;
        BigDecimal acumuladoTotalConIva = BigDecimal.ZERO;
        BigDecimal ivaAcumuladoTotal = BigDecimal.ZERO;

        for (FacturaClientesDetalle detalle : detalles) {
            if (detalle.getImporte() != null) {
                // Sumar el importe al total acumulado
                acumuladoTotal = acumuladoTotal.add(detalle.getImporte());
            }
        }

        // Calcular el total con IVA
        acumuladoTotal = acumuladoTotal.setScale(2, RoundingMode.DOWN);
        ivaAcumuladoTotal = ivaAcumuladoTotal.add(acumuladoTotal.multiply(BigDecimal.valueOf(header.getIva().floatValue()/100)));
        acumuladoTotalConIva = acumuladoTotal.add(ivaAcumuladoTotal);

        // Actualizar los campos
        header.setTotal(acumuladoTotal.setScale(2, RoundingMode.DOWN));
        header.setTotalConIva(acumuladoTotalConIva.setScale(2, RoundingMode.DOWN));
        header.setImporteIva(ivaAcumuladoTotal.setScale(2, RoundingMode.DOWN));
    }

    public List<FacturaClientesHeader> getByIsGenerated(boolean isGenerated) {
        return fHeaderRepository.findByIsGenerated(isGenerated,getSort());
    }

    public List<FacturaClientesHeader> getByDateRange(LocalDate fechaDesde, LocalDate fechaHasta) {
        return fHeaderRepository.findByDateBetween(fechaDesde, fechaHasta,getSort());
    }

    public List<FacturaClientesHeader> getByDateRangeAndIsGenerated(LocalDate fechaDesde, LocalDate fechaHasta, boolean isGenerated) {
        return fHeaderRepository.findByDateBetweenAndIsGenerated(fechaDesde, fechaHasta, isGenerated,getSort());
    }
        
            


}
