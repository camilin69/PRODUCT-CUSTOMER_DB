package co.edu.uptc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import co.edu.uptc.entities.Consumer;
import co.edu.uptc.entities.Provider;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

@Path("register")
public class Register {

    @POST
    @Path("reg_consumer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reg_consumer(Consumer consumer) {
        String sql = "INSERT INTO consumidores (id, nombre, edad, municipio, estrato, email, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {

            // Hash de la contraseña
            String hashedPassword = BCrypt.hashpw(consumer.getPassword(), BCrypt.gensalt());

            stmt.setInt(1, consumer.getId());
            stmt.setString(2, consumer.getName());
            stmt.setInt(3, consumer.getAge());
            stmt.setString(4, consumer.getMunicipio());
            stmt.setInt(5, consumer.getEstrato());
            stmt.setString(6, consumer.getEmail());
            stmt.setString(7, hashedPassword);

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

    @POST
    @Path("reg_provider")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reg_provider(Provider provider) {
        String sql = "INSERT INTO proveedores (id, nombre, telefono, email, password, direccion) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {

            // Hash de la contraseña
            String hashedPassword = BCrypt.hashpw(provider.getPassword(), BCrypt.gensalt());

            stmt.setInt(1, provider.getId());
            stmt.setString(2, provider.getName());
            stmt.setString(3, provider.getPhone());
            stmt.setString(4, provider.getEmail());
            stmt.setString(5, hashedPassword);
            stmt.setString(6, provider.getAddress());

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
