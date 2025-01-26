package com.facturacion.plasticsdeharo.service;

import org.springframework.stereotype.Service;

import com.facturacion.plasticsdeharo.entity.Articulo;
import com.facturacion.plasticsdeharo.repository.ArticuloRepository;

import lombok.AllArgsConstructor;
import javax.persistence.EntityNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class ArticuloService {

    private final ArticuloRepository articuloRepository;

    public List<Articulo> getAllArticulos() {
        return articuloRepository.findAll();
    }

    public List<Articulo> getArticulosByConcepto(String concepto) {
        return articuloRepository.findByConceptoContainingIgnoreCase(concepto);
    }

    public Articulo getArticuloById(Long id) {
        return articuloRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Articulo no encontrado"));
    }

    public Articulo createArticulo(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    public Articulo updateArticulo(Articulo articulo) {
        if (articuloRepository.findById(articulo.getCodigo()).isPresent()) {
            return articuloRepository.save(articulo);
        }
        throw new EntityNotFoundException("Articulo no encontrado");
    }

    public void deleteArticulo(Long id) {
        articuloRepository.deleteById(id);
    }
}