package com.facturacion.plasticsdeharo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.facturacion.plasticsdeharo.entity.Proveedor;
import com.facturacion.plasticsdeharo.service.ProveedorService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
@Controller
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;
    
    @GetMapping("/proveedores")
    public String getProveedores(Model model) {
        model.addAttribute("proveedores", proveedorService.getAllProveedores());
        return "proveedores";
    }

    @GetMapping("/proveedor")
    public String getProveedor(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "createProveedor";
    }

    @PostMapping("/proveedor")
    public String postProveedor(@ModelAttribute Proveedor proveedor) {
        proveedorService.createProveedor(proveedor);
        return "redirect:/proveedores";
    }

    @GetMapping("/proveedor/{id}")
    public String getProveedor(@PathVariable Long id, Model model) {
        model.addAttribute("proveedor", proveedorService.getProveedorById(id));  
        return "editProveedor";
    }

    @PostMapping("/proveedor/{id}")
    public String updateProveedor(@PathVariable Long id, @ModelAttribute Proveedor proveedor) {
        proveedorService.updateProveedor(proveedor);
        return "redirect:/proveedores";
    }

    @DeleteMapping("/proveedor/{id}")
    public ResponseEntity<?> deleteProveedor(@PathVariable Long id) {
        proveedorService.deleteProveedor(id);
        return ResponseEntity.ok().build();
    }
}