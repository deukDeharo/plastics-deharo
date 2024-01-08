package com.facturacion.plasticsdeharo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.facturacion.plasticsdeharo.entity.Articulo;
import com.facturacion.plasticsdeharo.service.ArticuloService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
@Controller
public class FacturasClientesController {

    // @Autowired
    // ArticuloService articuloService;
    
    @GetMapping("/facturasCliente")
    public String getArticulos(Model model) {
        //model.addAttribute("articulos", articuloService.getAllArticulos());
        return "facturasCliente";
    }

    @GetMapping("/facturaCliente")
    public String getArticulo(Model model) {
        // model.addAttribute("articulo", new Articulo());
        return "CreateFacturaCliente";
    }

    // @PostMapping("/articulo")
    // public String postArticulo(@ModelAttribute Articulo articulo) {
    //     articuloService.createArticulo(articulo);
    //     return "redirect:/articulos";
    // }

    // @GetMapping("/articulo/{codigo}")
    // public String getArticulo(@PathVariable Long codigo, Model model) {
    //     model.addAttribute("articulo", articuloService.getArticuloById(codigo));
    //     return "editArticulo";
    // }

    // @PostMapping("/articulo/{codigo}")
    // public String updateArticulo(@PathVariable Long codigo, @ModelAttribute Articulo articulo) {
    //     articuloService.updateArticulo(articulo);
    //     return "redirect:/articulos";
    // }

    // @DeleteMapping("/articulo/{codigo}")
    // public ResponseEntity<?> deleteArticulo(@PathVariable Long codigo) {
    //     articuloService.deleteArticulo(codigo);
    //     return ResponseEntity.ok().build();
    // }
}