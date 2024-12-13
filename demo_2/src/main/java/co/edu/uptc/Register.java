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


    @GET
    @Path("reg_consumer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reg_consumer(@QueryParam("id") int id,
                                @QueryParam("name") String name,
                                @QueryParam("age") int age,
                                @QueryParam("estrato") int estrato,
                                @QueryParam("email") String email,
                                @QueryParam("password") String password) {
        String sql = "INSERT INTO consumidores (id, nombre, edad, estrato, email, contra) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection c = MySqlConnection.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setInt(3, age);
            stmt.setInt(4, estrato);
            stmt.setString(5, email);
            stmt.setString(6, password); 

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\":\"Consumidor registrado exitosamente\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\":\"No se pudo registrar el consumidor\"}")
                        .build();
            }

        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en la consulta SQL: " + e.getMessage() + "\"}")
                    .build();
        }
    }


    @GET
    @Path("reg_provider")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reg_provider(@QueryParam("id") int id,
                                @QueryParam("name") String name,
                                @QueryParam("phone") String phone,
                                @QueryParam("email") String email,
                                @QueryParam("password") String password) {

        String sql = "INSERT INTO proveedores (id, nombre, telefono, email, contra ) VALUES (?, ?, ?, ?, ?)";

        try (Connection c = MySqlConnection.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.setString(5, password); 

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\":\"Proveedor registrado exitosamente\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\":\"No se pudo registrar el proveedor\"}")
                        .build();
            }

        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en la consulta SQL: " + e.getMessage() + "\"}")
                    .build();
        }
    }


    
}
