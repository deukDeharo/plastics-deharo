package com.facturacion.plasticsdeharo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturacion.plasticsdeharo.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNombreAndLocalidad(String nombre, String localidad);

    List<Cliente> findByLocalidadContainingIgnoreCase(String localidad);

    List<Cliente> findByNombreContainingIgnoreCase(String nombre);


}