package com.facturacion.plasticsdeharo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.facturacion.plasticsdeharo.entity.Proveedor;
import com.facturacion.plasticsdeharo.repository.ProveedorRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public List<Proveedor> getAllProveedores() {
        return proveedorRepository.findAll();
    }

    public Proveedor getProveedorById(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));
    }

    public Proveedor createProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public Proveedor updateProveedor(Proveedor proveedor) {
        if (proveedorRepository.findById(proveedor.getId()).isPresent()) {
            return proveedorRepository.save(proveedor);
        }
        throw new EntityNotFoundException("Proveedor no encontrado");
    }

    public void deleteProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }
}