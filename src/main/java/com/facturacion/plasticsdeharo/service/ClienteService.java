package com.facturacion.plasticsdeharo.service;

import org.springframework.stereotype.Service;

import com.facturacion.plasticsdeharo.entity.Cliente;
import com.facturacion.plasticsdeharo.repository.ClienteRepository;

import lombok.AllArgsConstructor;

import java.util.List;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Cliente no encontrado"));
    }

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(Cliente cliente) {
        if (clienteRepository.findById(cliente.getCodigoCliente()).isPresent()) {
            return clienteRepository.save(cliente);
        }
        throw new EntityNotFoundException("Cliente no encontrado");
    }

    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }

}
