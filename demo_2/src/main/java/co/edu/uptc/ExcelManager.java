package co.edu.uptc;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelManager {

    private static final String FILE_PATH = "src/main/resources/data.xlsx";

    public static void main(String[] args) throws IOException {
        readExcelFile();
    }


    public static void readExcelFile() throws IOException {

    }

    // Método para actualizar datos en un archivo Excel
    public static void updateExcelFile(int rowNum, String newName) throws IOException {
        try (FileInputStream file = new FileInputStream(FILE_PATH)) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            // Obtener la fila y actualizar la celda
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                Cell cell = row.getCell(1);
                if (cell != null) {
                    cell.setCellValue(newName);
                }
            }

            // Guardar los cambios en el archivo
            try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH)) {
                workbook.write(fileOut);
            }
            workbook.close();
        }
        System.out.println("Datos actualizados correctamente.");
    }

    // Método para eliminar una fila en un archivo Excel
    public static void deleteExcelRow(int rowNum) throws IOException {
        try (FileInputStream file = new FileInputStream(FILE_PATH)) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            // Eliminar la fila
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                sheet.removeRow(row);
            }

            // Guardar los cambios en el archivo
            try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH)) {
                workbook.write(fileOut);
            }
            workbook.close();
        }
        System.out.println("Fila eliminada correctamente.");
    }
}


