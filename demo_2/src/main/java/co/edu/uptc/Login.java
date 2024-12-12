package co.edu.uptc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uptc.entities.Consumer;
import co.edu.uptc.entities.Provider;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Path("login")
public class Login {

    private static final String SECRET = "123456789"; // Reemplaza con tu clave secreta

    @GET
    @Path("find_user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find_user(@QueryParam("email") String email,
                              @QueryParam("password") String password,
                              @QueryParam("role") String role) {

        // Verificar que los parámetros no sean nulos o vacíos
        if (email == null || email.isEmpty() || password == null || password.isEmpty() || role == null || role.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Email, Password or Role Missing\"}")
                    .build();
        }

        String table = role.equals("consumer") ? "consumidores" : "proveedores";
        String sql = "SELECT id, password FROM " + table + " WHERE email = ?";

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHashedPassword = rs.getString("password");
                    if (BCrypt.checkpw(password, storedHashedPassword)) {
                        // Generar Token JWT
                        String token = JWT.create()
                                .withIssuer("consultorio")
                                .withSubject(String.valueOf(rs.getInt("id")))
                                .withClaim("role", role)
                                .sign(Algorithm.HMAC256(SECRET));

                        return Response.ok("{\"message\":\"User Found\", \"token\":\"" + token + "\", \"role\":\"" + role + "\"}").build();
                    }
                }
            }

        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en la consulta SQL: " + e.getMessage() + "\"}")
                    .build();
        }

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"message\":\"Credenciales incorrectas o usuario no encontrado\"}")
                .build();
    }
}
