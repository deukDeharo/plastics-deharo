package com.facturacion.plasticsdeharo.service;



import org.springframework.stereotype.Service;

import com.facturacion.plasticsdeharo.dto.DetalleFacturaDTO;
import com.facturacion.plasticsdeharo.entity.Articulo;
import com.facturacion.plasticsdeharo.entity.Cliente;
import com.facturacion.plasticsdeharo.entity.FacturaClientesDetalle;
import com.facturacion.plasticsdeharo.entity.FacturaClientesHeader;
import com.facturacion.plasticsdeharo.repository.FacturaClientesDetalleRepository;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class FacturaClientesDetalleService {

    private final FacturaClientesDetalleRepository fDetalleRepository;

    private final ArticuloService articuloService;

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

    public List<FacturaClientesDetalle> serializeDetalles(List<DetalleFacturaDTO> detalles){
        List<FacturaClientesDetalle> detallesFactura = new ArrayList<FacturaClientesDetalle>();
        for (DetalleFacturaDTO dto : detalles) {
            FacturaClientesDetalle detalle = new FacturaClientesDetalle(dto);
            Articulo articulo = articuloService.getArticuloById(detalle.getCodigoArticulo());
            detalle.setConceptoArticulo(articulo.getConcepto());
            detalle.setPrecio(articulo.getPrecio());
            BigDecimal precio = detalle.getPrecio();
            BigDecimal unidad = BigDecimal.valueOf(detalle.getUnidad());

            BigDecimal importe = precio.multiply(unidad).setScale(2, RoundingMode.DOWN);
            detalle.setImporte(importe);
            detallesFactura.add(detalle);
        }
        return detallesFactura;
    }

    public List<FacturaClientesDetalle> getFacturaClientesDetalleByHeaderId(Long id) {
       return fDetalleRepository.findByCodigoFacturaHeader(id);
    }

    public void deleteFacturaClientesDetallesByCodigoFactura(Long id) {
        fDetalleRepository.deleteByCodigoFacturaHeader(id);
    }

    

}