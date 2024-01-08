package com.facturacion.plasticsdeharo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.facturacion.plasticsdeharo.entity.Cliente;
import com.facturacion.plasticsdeharo.service.ClienteService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ClienteController {

    @Autowired
    ClienteService clienteService;
    
    @GetMapping("/clientes")
    public String getClientes(Model model) {
        model.addAttribute("clientes", clienteService.getAllClientes());
        return "clientes";
    }

    @GetMapping("/cliente")
    public String getCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "createCliente";
    }

    @PostMapping("/cliente")
    public String postCliente(@ModelAttribute Cliente cliente) {
        clienteService.createCliente(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/cliente/{id}")
    public String getCliente(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", clienteService.getClienteById(id));  // Reemplaza esto por el cliente obtenido
        return "editCliente";
    }

    @PostMapping("/cliente/{id}")
    public String updateCliente(@PathVariable Long id, @ModelAttribute Cliente cliente) {
        clienteService.updateCliente(cliente);
        return "redirect:/clientes";
    }

    @DeleteMapping("/cliente/{codigoCliente}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long codigoCliente) {
        Long id = clienteService.getClienteById(codigoCliente).getCodigoCliente();
        clienteService.deleteCliente(id);
        return ResponseEntity.ok().build();
    }
}