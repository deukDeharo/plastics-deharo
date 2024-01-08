package com.facturacion.plasticsdeharo.service;

import org.springframework.stereotype.Service;

import com.facturacion.plasticsdeharo.entity.FacturaClientesHeader;
import com.facturacion.plasticsdeharo.repository.FacturaClientesHeaderRepository;
import lombok.AllArgsConstructor;

import java.util.List;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class FacturaClientesHeaderService {

    private final FacturaClientesHeaderRepository fHeaderRepository;

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

}
