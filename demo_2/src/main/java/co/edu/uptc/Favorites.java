package co.edu.uptc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import co.edu.uptc.entities.Product;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("favorites")
public class Favorites {

    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addToFavorites(@QueryParam("productId") String productId, @HeaderParam("Authorization") String token) {
        int userId = getUserIdFromToken(token);

        if(userId == -1){
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\":\"Usuario no autenticado\"}")
                    .build();
        }

        String sql = "INSERT INTO favoritos (usuario_id, producto_id) VALUES (?, ?)";

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, productId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return Response.ok("{\"message\":\"Producto agregado a favoritos\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\":\"No se pudo agregar el producto a favoritos\"}")
                        .build();
            }

        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en la consulta SQL: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @POST
    @Path("remove")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeFromFavorites(@QueryParam("productId") String productId, @HeaderParam("Authorization") String token) {
        int userId = getUserIdFromToken(token);

        if(userId == -1){
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\":\"Usuario no autenticado\"}")
                    .build();
        }

        String sql = "DELETE FROM favoritos WHERE usuario_id = ? AND producto_id = ?";

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, productId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return Response.ok("{\"message\":\"Producto removido de favoritos\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"Producto no encontrado en favoritos\"}")
                        .build();
            }

        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en la consulta SQL: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("get_favorites")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFavorites(@HeaderParam("Authorization") String token) {
        int userId = getUserIdFromToken(token);

        if(userId == -1){
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\":\"Usuario no autenticado\"}")
                    .build();
        }

        ArrayList<Product> favorites = new ArrayList<>();
        String sql = "SELECT p.* FROM productos p JOIN favoritos f ON p.id = f.producto_id WHERE f.usuario_id = ?";

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                            rs.getString("id"),
                            rs.getString("nombreDane"),
                            rs.getString("codigoBarras"),
                            rs.getString("nombre"),
                            rs.getString("unidad"),
                            rs.getString("marca"),
                            rs.getString("empresa"),
                            rs.getInt("id_category")
                    );
                    favorites.add(product);
                }
            }

            return Response.ok(favorites).build();

        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en la consulta SQL: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    private int getUserIdFromToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return -1;
        }

        token = token.substring("Bearer ".length());

        try {
            com.auth0.jwt.interfaces.DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("your_secret_key"))
                    .withIssuer("consultorio")
                    .build()
                    .verify(token);
            return decodedJWT.getClaim("sub").asInt();
        } catch (Exception e) {
            return -1;
        }
    }
}
