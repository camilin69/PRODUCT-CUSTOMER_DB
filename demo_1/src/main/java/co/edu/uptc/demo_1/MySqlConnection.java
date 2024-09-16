package co.edu.uptc.demo_1;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MySqlConnection {

    public static void main(String[] args) {
        // Datos de conexión
        String url = "jdbc:mysql://localhost:3306/consultorio_productos";
        String username = "cami";
        String password = "cami";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            try (FileInputStream file = new FileInputStream("src/main/resources/data.xlsx")) {
                XSSFWorkbook workbook = new XSSFWorkbook(file);
                Sheet sheet = workbook.getSheetAt(0);

                Iterator<Row> rowIterator = sheet.iterator();
                int c = 1;
                while (rowIterator.hasNext() && c <= 10) {
                    List<String> data = new ArrayList<>();
                    data.add(String.valueOf(c));
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        if(cell.getCellType() != CellType.BLANK ){
                            switch (cell.getCellType()){
                                case STRING -> data.add("'" + cell.getStringCellValue() + "'");
                                case NUMERIC -> data.add(String.valueOf(cell.getNumericCellValue()));
                            }
                        }else{
                            break;
                        }
                    }
                    String query = String.format(
                            "INSERT INTO productos (id, nombreDane, codigoBarras, nombreProducto, unidadMedida, marca, empresa, divipola, municipio, precioImplicito, precioReportado) " +
                                    "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)",
                            data.toArray());

                    Statement statement = connection.createStatement();
                    statement.executeUpdate(query); // Usar executeUpdate para operaciones de inserción
                    statement.close();
                    c++;

                }
                workbook.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}

