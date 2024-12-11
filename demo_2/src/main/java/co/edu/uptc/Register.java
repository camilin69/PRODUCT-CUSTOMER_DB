package co.edu.uptc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("register")
public class Register {

    @GET
    @Path("find_user")
    @Produces(MediaType.TEXT_PLAIN)
    public boolean find_user(@QueryParam("id") String id, 
                            @QueryParam("role") String role) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }

        try (Connection c = MySqlConnection.getConnection()) {

            String table = role.equals("consumer")?"consumidores":"proveedores";
            
            String sql = "SELECT COUNT(*) FROM " + table + " WHERE id = ?";
            
            try (PreparedStatement statement = c.prepareStatement(sql)) {

                statement.setString(1, id);

                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return true;  
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en la consulta SQL: " + e.getMessage(), e);
        }

        return false;
    }

    @POST
    @Path("reg_consumer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response reg_consumer(
            @FormParam("id") String id,
            @FormParam("name") String name,
            @FormParam("email") String email,
            @FormParam("password") String password,
            @FormParam("municipio_id") String municipioId,
            @FormParam("age") int age,
            @FormParam("status") int stratum) {

        boolean isSuccess = false;
        try {
            String query = "INSERT INTO consumers (id, nombre, email, contra, idMunicipio, edad, estrato) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = MySqlConnection.getConnection().prepareStatement(query);
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.setString(5, municipioId);
            stmt.setInt(6, age);
            stmt.setInt(7, stratum);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Response.ok(isSuccess).build();
    }

    @POST
    @Path("reg_provider")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response reg_provider(
            @FormParam("id") String id,
            @FormParam("name") String name,
            @FormParam("email") String email,
            @FormParam("password") String password,
            @FormParam("phone") String phone) {

        // Handle registration for provider
        boolean isSuccess = false;
        try {
            String query = "INSERT INTO providers (id, name, email, password, phone) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = MySqlConnection.getConnection().prepareStatement(query);
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, password); // In real-life, hash the password
            stmt.setString(5, phone);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Response.ok(isSuccess).build();
    }
}
