package com.facturacion.plasticsdeharo.service;

import org.springframework.stereotype.Service;

import com.facturacion.plasticsdeharo.dto.FacturaProveedorDTO;
import com.facturacion.plasticsdeharo.entity.FacturaProveedor;
import com.facturacion.plasticsdeharo.entity.Proveedor;
import com.facturacion.plasticsdeharo.repository.FacturaProveedorRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class FacturaProveedorService {

    private final FacturaProveedorRepository facturaProveedorRepository;
    private final ProveedorService proveedorService;

    public List<FacturaProveedor> getAllFacturas() {
        return facturaProveedorRepository.findAll();
    }


    public FacturaProveedor createFactura(FacturaProveedorDTO dto) {
        FacturaProveedor facturaProveedor = mapToEntity(dto);
        Proveedor proveedor = getProveedorFromFacturaProveedor(facturaProveedor);
        facturaProveedor.setProveedor(proveedor.getNombre());
        return facturaProveedorRepository.save(facturaProveedor);
    }

    public FacturaProveedor updateFactura(Long id, FacturaProveedorDTO dto) {
        FacturaProveedor facturaProveedor = facturaProveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FacturaProveedor no encontrada con ID: " + id));
        facturaProveedor.setCodigoProveedor(dto.getCodigoProveedor());
        facturaProveedor.setFecha(parseStringToLocalDate(dto.getFecha()));
        facturaProveedor.setTotal(dto.getTotal());
        Proveedor proveedor = getProveedorFromFacturaProveedor(facturaProveedor);
        facturaProveedor.setProveedor(proveedor.getNombre());
        return facturaProveedorRepository.save(facturaProveedor);
    }

    public FacturaProveedor getFacturaById(Long id) {
        return facturaProveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FacturaProveedor no encontrada con ID: " + id));
    }

    public void deleteFactura(Long id) {
        facturaProveedorRepository.deleteById(id);
    }


    public List<FacturaProveedor> getFacturasByProveedor(Long codigoProveedor) {
        return facturaProveedorRepository.findByCodigoProveedor(codigoProveedor);
    }

    public List<FacturaProveedor> getFacturasByFechaBetween(LocalDate startDate, LocalDate endDate) {
        return facturaProveedorRepository.findByFechaBetween(startDate, endDate);
    }

    public Proveedor getProveedorFromFacturaProveedor(FacturaProveedor facturaProveedor){
        return proveedorService.getProveedorById(facturaProveedor.getCodigoProveedor());
    }

    private FacturaProveedor mapToEntity(FacturaProveedorDTO dto) {
        FacturaProveedor facturaProveedor = new FacturaProveedor();
        facturaProveedor.setCodigoProveedor(dto.getCodigoProveedor());
        facturaProveedor.setFecha(parseStringToLocalDate(dto.getFecha()));
        facturaProveedor.setTotal(dto.getTotal());
        return facturaProveedor;
    }

    private LocalDate parseStringToLocalDate(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fecha, formatter);
    }
}
