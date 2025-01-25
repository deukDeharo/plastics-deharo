package com.facturacion.plasticsdeharo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.facturacion.plasticsdeharo.dto.FacturaDTO;
import com.facturacion.plasticsdeharo.entity.FacturaClientesHeader;
import com.facturacion.plasticsdeharo.service.FacturaClientesService;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
public class FacturasClientesController {

    @Autowired
    FacturaClientesService fClientesService;

    @GetMapping("/facturasCliente")
    public String getFacturas(@RequestParam(required = false) Long codigo,
            @RequestParam(required = false) Long cliente,
            Model model) {
        List<FacturaClientesHeader> facturas = fClientesService.filterFacturas(codigo, cliente);
        model.addAttribute("facturas", facturas);
        return "facturasCliente";
    }

    @GetMapping("/facturaCliente/{id}")
    public String getFactura(@PathVariable Long id, Model model) {
        FacturaDTO facturaDTO = fClientesService.getFacturaById(id);
        model.addAttribute("factura", facturaDTO);
        return "editFacturaCliente";
    }

    @GetMapping("/facturaCliente")
    public String getCreateFactura(Model model) {
        // model.addAttribute("articulo", new Articulo());
        return "createFacturaCliente";
    }

    @PostMapping("/facturaCliente")
    public ResponseEntity<String> crearFactura(@RequestBody FacturaDTO factura) {
        fClientesService.createFacturaByFacturaDTO(factura);
        return ResponseEntity.ok("/facturasCliente");
    }

    @PutMapping("/facturaCliente/{id}")
    public ResponseEntity<String> actualizarFactura(@PathVariable Long id, @RequestBody FacturaDTO factura) {
        try {
            factura.getHeader().setIdFactura(id); // Ensure the ID in the header matches the path variable
            fClientesService.updateFacturaByFacturaDTO(factura);
            return ResponseEntity.ok("/facturasCliente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar la factura: " + e.getMessage());
        }
    }

    @GetMapping("/facturaCliente/{id}/delete")
    public String deleteFacturaCliente(@PathVariable Long id, Model model) {
        boolean deleted = fClientesService.deleteFacturaCliente(id);
        if (deleted) {
            List<FacturaClientesHeader> facturas = fClientesService.filterFacturas(null, null);
            model.addAttribute("facturas", facturas);
            return "facturasCliente";
        } else {
            return "Factura cliente no encontrada.";
        }
    }

    @GetMapping("/facturaCliente/{id}/print")
    public ResponseEntity<byte[]> printFacturaXls(@PathVariable Long id) {
        try {
            // Generar el archivo Excel como flujo de bytes
            ByteArrayOutputStream excelOutputStream = fClientesService.printFacturaXls(id,true);

            // Preparar los encabezados de la respuesta HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            String fileName = "factura_"+ id +".xlsx";
            headers.setContentDispositionFormData("attachment", fileName);

            // Devolver el archivo como un arreglo de bytes
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelOutputStream.toByteArray());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/facturaCliente/{id}/printAlbaran")
    public ResponseEntity<byte[]> printAlbaranXls(@PathVariable Long id) {
        try {
            // Generar el archivo Excel como flujo de bytes
            ByteArrayOutputStream excelOutputStream = fClientesService.printFacturaXls(id,false);

            // Preparar los encabezados de la respuesta HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            String fileName = "albaran_"+ id +"_.xlsx";
            headers.setContentDispositionFormData("attachment", fileName);

            // Devolver el archivo como un arreglo de bytes
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelOutputStream.toByteArray());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/facturaCliente/{id}/generate")
    public ResponseEntity<byte[]> generateFacturaXls(@PathVariable Long id) {
        try {
            fClientesService.generateFactura(id);
            // Generar el archivo Excel como flujo de bytes
            ByteArrayOutputStream excelOutputStream = fClientesService.printFacturaXls(id,true);

            // Preparar los encabezados de la respuesta HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            String fileName = "factura_"+ id +".xlsx";
            headers.setContentDispositionFormData("attachment", fileName);

            // Devolver el archivo como un arreglo de bytes
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelOutputStream.toByteArray());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

}