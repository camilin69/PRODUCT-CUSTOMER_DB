package co.edu.uptc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("login")
public class Login {

    private static final String SECRET = "123456789"; // Reemplaza con tu clave secreta

    @GET
    @Path("find_user")
    @Produces(MediaType.APPLICATION_JSON)
    public Integer find_user(@QueryParam("email") String email, 
                            @QueryParam("password") String password, 
                            @QueryParam("role") String role) {

        // Verificar que los parámetros no sean nulos o vacíos
        if (email == null || email.isEmpty() || password == null || password.isEmpty() || role == null || role.isEmpty()) {
            throw new RuntimeException("Email, Password or Role Missed");
        }

        Integer userId = null;


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }

        try (Connection c = MySqlConnection.getConnection()) {

            String table = role.equals("consumer")?"consumidores":"proveedores";
            
            String sql = "SELECT * FROM " + table + " WHERE email = ? AND contra = ?";
            
            try (PreparedStatement statement = c.prepareStatement(sql)) {

                statement.setString(1, email);
                statement.setString(2, password);

                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("id");  
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en la consulta SQL: " + e.getMessage(), e);
        }

        return userId;
    }

    
}
