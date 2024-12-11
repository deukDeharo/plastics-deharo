package com.facturacion.plasticsdeharo.service;

import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import com.facturacion.plasticsdeharo.dto.HeaderFacturaDTO;
import com.facturacion.plasticsdeharo.entity.Cliente;
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
        return fHeaderRepository.findAll();
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


}
