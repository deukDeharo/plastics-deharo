package com.facturacion.plasticsdeharo.service;

import org.springframework.stereotype.Service;

import com.facturacion.plasticsdeharo.repository.FacturaClientesDetalleRepository;
import com.facturacion.plasticsdeharo.repository.FacturaClientesHeaderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FacturaClientesService {
    
    private final FacturaClientesDetalleRepository fDetalleRepository;
    private final FacturaClientesHeaderRepository fHeaderRepository;
}
