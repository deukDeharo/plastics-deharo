package com.facturacion.plasticsdeharo.service;



import org.springframework.stereotype.Service;

import com.facturacion.plasticsdeharo.entity.FacturaClientesDetalle;
import com.facturacion.plasticsdeharo.repository.FacturaClientesDetalleRepository;
import lombok.AllArgsConstructor;

import java.util.List;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class FacturaClientesDetalleService {

    private final FacturaClientesDetalleRepository fDetalleRepository;

    public List<FacturaClientesDetalle> getAllFacturaClientesDetalle() {
        return fDetalleRepository.findAll();
    }

    public FacturaClientesDetalle getFacturaClientesDetalleById(Long id) {
        return fDetalleRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("FacturaClientesDetalle no encontrado"));
    }

    public FacturaClientesDetalle createFacturaClientesDetalle(FacturaClientesDetalle fDetalle) {
        return fDetalleRepository.save(fDetalle);
    }

    public FacturaClientesDetalle updateFacturaClientesDetalle(FacturaClientesDetalle fDetalle) {
        if (fDetalleRepository.findById(fDetalle.getDetalleId()).isPresent()) {
            return fDetalleRepository.save(fDetalle);
        }
        throw new EntityNotFoundException("FacturaClientesDetalle no encontrado");
    }

    public void deleteFacturaClientesDetalle(Long id) {
        fDetalleRepository.deleteById(id);
    }

}