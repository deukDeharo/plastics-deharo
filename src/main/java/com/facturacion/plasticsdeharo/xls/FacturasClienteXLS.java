package com.facturacion.plasticsdeharo.xls;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

@Component
public class FacturasClienteXLS {
    public void llenarHojaFactura(Workbook workbook) {
        Sheet sheet = workbook.getSheet("FACTURA");
        if (sheet == null) {
            throw new RuntimeException("La hoja FACTURA no existe en el archivo.");
        }

        // Formateador de fecha y hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Llenar la celda C8 con la fecha y hora actual
        Row row8 = sheet.getRow(7); // Fila 8 (índice 7)
        if (row8 == null) row8 = sheet.createRow(7);
        Cell cellC8 = row8.getCell(2); // Celda C8 (índice 2)
        if (cellC8 == null) cellC8 = row8.createCell(2);
        cellC8.setCellValue(LocalDateTime.now().format(formatter)); // Fecha y hora actual

        // Llenar la celda C9 con un valor double de prueba
        Row row9 = sheet.getRow(8); // Fila 9 (índice 8)
        if (row9 == null) row9 = sheet.createRow(8);
        Cell cellC9 = row9.getCell(2); // Celda C9 (índice 2)
        if (cellC9 == null) cellC9 = row9.createCell(2);
        cellC9.setCellValue(12345.67); // Valor double de prueba

        // Llenar la celda C10 con un string
        Row row10 = sheet.getRow(9); // Fila 10 (índice 9)
        if (row10 == null) row10 = sheet.createRow(9);
        Cell cellC10 = row10.getCell(2); // Celda C10 (índice 2)
        if (cellC10 == null) cellC10 = row10.createCell(2);
        cellC10.setCellValue("Este es un texto de prueba"); // String de prueba
    }
    

    public void llenarHojaAlbaran(Workbook workbook) {
        Sheet sheet = workbook.getSheet("ALBARAN");
        if (sheet == null) {
        throw new RuntimeException("La hoja ALBARAN no existe en el archivo.");
        }

        // Formateador de fecha y hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Llenar la celda C8 con la fecha y hora actual
        Row row8 = sheet.getRow(7); // Fila 8 (índice 7)
        if (row8 == null) row8 = sheet.createRow(7);
        Cell cellC8 = row8.getCell(2); // Celda C8 (índice 2)
        if (cellC8 == null) cellC8 = row8.createCell(2);
        cellC8.setCellValue(LocalDateTime.now().format(formatter)); // Fecha y hora actual

        // Llenar la celda C9 con un valor double de prueba
        Row row9 = sheet.getRow(8); // Fila 9 (índice 8)
        if (row9 == null) row9 = sheet.createRow(8);
        Cell cellC9 = row9.getCell(2); // Celda C9 (índice 2)
        if (cellC9 == null) cellC9 = row9.createCell(2);
        cellC9.setCellValue(12345.67); // Valor double de prueba

        // Llenar la celda C10 con un string
        Row row10 = sheet.getRow(9); // Fila 10 (índice 9)
        if (row10 == null) row10 = sheet.createRow(9);
        Cell cellC10 = row10.getCell(2); // Celda C10 (índice 2)
        if (cellC10 == null) cellC10 = row10.createCell(2);
        cellC10.setCellValue("Este es un texto de prueba"); // String de prueba
    }
}
