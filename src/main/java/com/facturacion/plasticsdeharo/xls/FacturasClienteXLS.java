package com.facturacion.plasticsdeharo.xls;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.facturacion.plasticsdeharo.dto.DetalleFacturaDTO;
import com.facturacion.plasticsdeharo.dto.FacturaDTO;
import com.facturacion.plasticsdeharo.dto.HeaderFacturaDTO;
import com.facturacion.plasticsdeharo.entity.Cliente;
import com.facturacion.plasticsdeharo.service.ClienteService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FacturasClienteXLS {

    private final ClienteService clienteService;

    public void llenarHojaFactura(Workbook workbook, FacturaDTO facturaDTO,Cliente cliente) {
        Sheet sheet = workbook.getSheet("FACTURA");
        if (sheet == null) {
            throw new RuntimeException("La hoja FACTURA no existe en el archivo.");
        }
        HeaderFacturaDTO header = facturaDTO.getHeader();
        fillHeader(cliente, sheet, header);
        fillDetail(sheet,true,facturaDTO.getDetalle());

        // C42 Forma de pago
        Row row42 = sheet.getRow(41); 
        if (row42 == null) row42 = sheet.createRow(41);
        Cell cellC42 = row42.getCell(2); 
        if (cellC42 == null) cellC42 = row42.createCell(2);
        cellC42.setCellValue(cliente.getFormaDePago());

        // todo: F42 Base Imp.
        Cell cellF42 = row42.getCell(5); 
        if (cellF42 == null) cellF42 = row42.createCell(5);
        cellF42.setCellValue(header.getTotal().doubleValue());

        // C43 Vencimiento
        Row row43 = sheet.getRow(42); 
        if (row43 == null) row43 = sheet.createRow(42);
        Cell cellC43 = row43.getCell(2); 
        if (cellC43 == null) cellC43 = row43.createCell(2);
        cellC43.setCellValue(formattedStringDate(header.getFechaVencimiento()));

        //F43 iva
        Cell cellF43 = row43.getCell(5); 
        if (cellF43 == null) cellF43 = row43.createCell(5);
        cellF43.setCellValue(header.getImporteIva().doubleValue());

        // C44 IBAN
        Row row44 = sheet.getRow(43); 
        if (row44 == null) row44 = sheet.createRow(43);
        Cell cellC44 = row44.getCell(2); 
        if (cellC44 == null) cellC44 = row44.createCell(2);
        cellC44.setCellValue(cliente.getNumeroDeCuenta());

        // F44 total
        Cell cellF44 = row44.getCell(5); 
        if (cellF44 == null) cellF44 = row44.createCell(5);
        cellF44.setCellValue(header.getTotalConIva().doubleValue());
        
    }

    public void llenarHojaAlbaran(Workbook workbook, FacturaDTO facturaDTO, Cliente cliente) {
        Sheet sheet = workbook.getSheet("ALBARAN");
        if (sheet == null) {
            throw new RuntimeException("La hoja ALBARAN no existe en el archivo.");
        }
        HeaderFacturaDTO header = facturaDTO.getHeader();

        fillHeader(cliente, sheet, header);
        fillDetail(sheet,false,facturaDTO.getDetalle());

       
    }

    private void fillHeader(Cliente cliente, Sheet sheet, HeaderFacturaDTO header) {
        // C8 Fecha 
        Row row8 = sheet.getRow(7);
        if (row8 == null) row8 = sheet.createRow(7);
        Cell cellC8 = row8.getCell(2); 
        if (cellC8 == null) cellC8 = row8.createCell(2);
        cellC8.setCellValue(formattedStringDate(header.getFecha()));

        // C9 Numero Factura/Albaran
        Row row9 = sheet.getRow(8); 
        if (row9 == null) row9 = sheet.createRow(8);
        Cell cellC9 = row9.getCell(2); 
        if (cellC9 == null) cellC9 = row9.createCell(2);
        cellC9.setCellValue(header.getIdFactura().toString()); 

        // C10 Cliente
        Row row10 = sheet.getRow(9); 
        if (row10 == null) row10 = sheet.createRow(9);
        Cell cellC10 = row10.getCell(2); 
        if (cellC10 == null) cellC10 = row10.createCell(2);
        cellC10.setCellValue(cliente.getNombre()); 

        // C11 NIF
        Row row11 = sheet.getRow(10); 
        if (row11 == null) row11 = sheet.createRow(10);
        Cell cellC11 = row11.getCell(2); 
        if (cellC11 == null) cellC11 = row11.createCell(2);
        cellC11.setCellValue(cliente.getNif());
        
        // C12 Domicilio
        Row row12 = sheet.getRow(11); 
        if (row12 == null) row12 = sheet.createRow(11);
        Cell cellC12 = row12.getCell(2); 
        if (cellC12 == null) cellC12 = row12.createCell(2);
        cellC12.setCellValue(cliente.getDomicilio()+ " - " + cliente.getLocalidad());
    }
    
    private String formattedDate(LocalDate date){
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formateador);
    }

    private String formattedStringDate(String dateString){
        LocalDate date = LocalDate.parse(dateString);
        return formattedDate(date);
    }

    private void fillDetail(Sheet sheet,boolean isFactura, List<DetalleFacturaDTO> list ){
        int rowNum = 13;

        for (DetalleFacturaDTO detalle : list) {
            // Crear o obtener la fila actual
            Row row = sheet.getRow(rowNum);
            if (row == null) row = sheet.createRow(rowNum);

            // Columna B (segunda columna, índice 1)
            Cell cellB = row.getCell(1);
            if (cellB == null) cellB = row.createCell(1);
            cellB.setCellValue(detalle.getCodigoArticulo());

            // Columna C (tercera columna, índice 2)
            Cell cellC = row.getCell(2);
            if (cellC == null) cellC = row.createCell(2);
            cellC.setCellValue(detalle.getConceptoArticulo());

            // Columna D (cuarta columna, índice 3)
            Cell cellD = row.getCell(3);
            if (cellD == null) cellD = row.createCell(3);
            cellD.setCellValue(detalle.getUnidad());

            // Si isFactura es true, llenar también columnas E y F
            if (isFactura) {
                // Columna E (quinta columna, índice 4)
                Cell cellE = row.getCell(4);
                if (cellE == null) cellE = row.createCell(4);
                cellE.setCellValue(detalle.getPrecio().doubleValue());

                // Columna F (sexta columna, índice 5)
                Cell cellF = row.getCell(5);
                if (cellF == null) cellF = row.createCell(5);
                cellF.setCellValue(detalle.getImporte().doubleValue());
            }
            rowNum++;
        }
    }
}
