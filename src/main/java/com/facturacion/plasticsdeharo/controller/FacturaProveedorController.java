package com.facturacion.plasticsdeharo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.facturacion.plasticsdeharo.dto.FacturaProveedorDTO;
import com.facturacion.plasticsdeharo.entity.FacturaProveedor;
import com.facturacion.plasticsdeharo.service.FacturaProveedorService;

@Controller
public class FacturaProveedorController {

    @Autowired
    private FacturaProveedorService facturaProveedorService;

    @GetMapping("/facturasProveedor")
    public String getFacturasProveedor(Model model) {
        model.addAttribute("facturasProveedor", facturaProveedorService.getAllFacturas());
        return "facturasProveedor";
    }

    @GetMapping("/facturaProveedor")
    public String getFacturaProveedorForm(Model model) {
        model.addAttribute("facturaProveedor", new FacturaProveedorDTO());
        return "createFacturaProveedor";
    }

    @PostMapping("/facturaProveedor")
    public String postFacturaProveedor(@ModelAttribute FacturaProveedorDTO facturaProveedorDTO) {
        facturaProveedorService.createFactura(facturaProveedorDTO);
        return "redirect:/facturasProveedor";
    }

    @GetMapping("/facturaProveedor/{id}")
    public String getFacturaProveedor(@PathVariable Long id, Model model) {
        FacturaProveedor facturaProveedor = facturaProveedorService.getFacturaById(id);
        FacturaProveedorDTO facturaProveedorDTO = mapToDTO(facturaProveedor);
        model.addAttribute("facturaProveedor", facturaProveedorDTO);
        return "editFacturaProveedor";
    }

    @PostMapping("/facturaProveedor/{id}")
    public String updateFacturaProveedor(@PathVariable Long id,
            @ModelAttribute FacturaProveedorDTO facturaProveedorDTO) {
        facturaProveedorService.updateFactura(id, facturaProveedorDTO);
        return "redirect:/facturasProveedor";
    }

    @DeleteMapping("/facturaProveedor/{id}")
    public ResponseEntity<?> deleteFacturaProveedor(@PathVariable Long id) {
        facturaProveedorService.deleteFactura(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/proveedor/{codigoProveedor}")
    public List<FacturaProveedor> getFacturasProveedorByProveedor(@PathVariable Long codigoProveedor) {
        return facturaProveedorService.getFacturasByProveedor(codigoProveedor);
    }

    @GetMapping("/rango-fechas")
    public List<FacturaProveedor> getFacturasProveedorByFechaBetween(@RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return facturaProveedorService.getFacturasByFechaBetween(startDate, endDate);
    }

    private FacturaProveedorDTO mapToDTO(FacturaProveedor facturaProveedor) {
        FacturaProveedorDTO dto = new FacturaProveedorDTO();
        dto.setCodigo(facturaProveedor.getCodigo());
        dto.setCodigoProveedor(facturaProveedor.getCodigoProveedor());
        dto.setFecha(facturaProveedor.getFecha().toString());
        dto.setTotal(facturaProveedor.getTotal());
        dto.setProveedor(facturaProveedorService.getProveedorFromFacturaProveedor(facturaProveedor).getNombre());
        return dto;
    }
}
