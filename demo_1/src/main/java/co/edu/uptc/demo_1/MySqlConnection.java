package co.edu.uptc.demo_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySqlConnection {
    public static void main(String[] args) {
        // Datos de conexión
        String url = "jdbc:mysql://localhost:3306/nombre_de_la_base_de_datos";
        String username = "tu_usuario";
        String password = "tu_contraseña";

        try {
            // Establecer conexión
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión exitosa!");

            // Crear una consulta SQL
            String query = "SELECT * FROM tu_tabla";

            // Crear un Statement para ejecutar la consulta
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Procesar los resultados
            while (resultSet.next()) {
                // Supongamos que tu tabla tiene columnas id y nombre
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                System.out.println("ID: " + id + ", Nombre: " + nombre);
            }

            // Cerrar conexión
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

