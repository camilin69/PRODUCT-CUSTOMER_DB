package co.edu.uptc;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import co.edu.uptc.entities.Consumer;
import co.edu.uptc.entities.Provider;
import co.edu.uptc.entities.Category;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class MySqlConnection {

    public static void main(String[] args) {
        String DATABASE = "consultoriopc";
        String url = "jdbc:mysql://localhost:3306/" + DATABASE;
        String username = "camilinpinguin";
        String password = "cami";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            int op = 5;
            if(op == 1)
                addProducts(connection);
            else if(op == 2)
                addConsumers(connection);
            else if(op == 3)
                addHistoricProducts(connection);
            else if(op == 4)
                addCategories(connection);
            else if(op == 5){
                addProviders(connection);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void addProducts(Connection connection) throws FileNotFoundException, IOException, SQLException{
        try (FileInputStream file = new FileInputStream("src/main/resources/data.xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            int c = 143;
            while (rowIterator.hasNext() && c <= 1000) {
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
                        "INSERT INTO productos (id, nombreDane, codigoBarras, nombre, unidad, marca, empresa, divipola, municipio, precioImplicito, precioExplicito) " +
                                "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)",
                        data.toArray());

                Statement statement = connection.createStatement();
                statement.executeUpdate(query); // Usar executeUpdate para operaciones de inserción
                statement.close();
                c++;

            }
            workbook.close();
        }
    }
    
    private static void addConsumers(Connection connection) throws IOException {
        Gson gson = new Gson();
        List<Consumer> consumers = new ArrayList<>();
        try(FileReader reader = new FileReader("src/main/resources/consumers.json")){
            consumers = gson.fromJson(reader, new TypeToken<List<Consumer>>(){}.getType());
            reader.close();
        }
        
        consumers.forEach(c -> {
            String sql = "INSERT INTO consumidores (id, nombre, edad, municipio, estrato, email) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setInt(1, c.getId());
                ps.setString(2, c.getName());
                ps.setInt(3, c.getAge());
                ps.setString(4, c.getMunicipio());
                ps.setInt(5, c.getStatus());
                ps.setString(6, c.getEmail());

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        });
    }

    private static void addHistoricProducts(Connection connection){

    }

    private static void addCategories(Connection connection) throws IOException{
        Gson gson = new Gson();
        List<Category> categories = new ArrayList<>();
        try(FileReader reader = new FileReader("src/main/resources/categories.json")){
            categories = gson.fromJson(reader, new TypeToken<List<Category>>(){}.getType());
            reader.close();
        }
        
        categories.forEach(c -> {
            String sql = "INSERT INTO categorias (id, nombre, descripcion) " +
                         "VALUES (?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setInt(1, c.getId());
                ps.setString(2, c.getName());
                ps.setString(3, c.getDescription());

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        });
    }

    private static void addProviders(Connection connection) throws IOException{
        Gson gson = new Gson();
        List<Provider> providers = new ArrayList<>();
        try(FileReader reader = new FileReader("src/main/resources/providers.json")){
            providers = gson.fromJson(reader, new TypeToken<List<Provider>>(){}.getType());
            reader.close();
        }
        
        providers.forEach(p -> {
            String sql = "INSERT INTO proveedores (id, nombre, direccion, telefono, email) " +
                         "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setInt(1, p.getId());
                ps.setString(2, p.getName());
                ps.setString(3, p.getAddress());
                ps.setString(4, p.getPhone());
                ps.setString(5, p.getEmail());

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        });
    }

}

