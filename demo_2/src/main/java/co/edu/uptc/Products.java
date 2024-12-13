package co.edu.uptc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.entities.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("products")
public class Products {

    @GET
    @Path("get_sell_point_consumer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_sell_point_consumer(@QueryParam("id_municipio") int id_municipio,
                                            @QueryParam("id_producto") int id_producto) {
        ArrayList<SellPoint> sellPoints = new ArrayList<>();  // Cambio a SellPoint en lugar de Product

        String sql = """
                    SELECT pv.id AS punto_venta_id,
                            pv.nombre AS punto_venta_nombre,
                            pv.direccion AS punto_venta_direccion,
                            pm.precioImplicito,
                            pm.precioExplicito,
                            pm.divipola
                    FROM producto_municipio pm
                    JOIN producto_punto_venta ppv ON pm.id = ppv.idProductoMunicipio
                    JOIN puntos_venta pv ON ppv.idPuntoVenta = pv.id
                    WHERE pm.idMunicipio = ? AND pm.idProducto = ?;
                    """;

        try (Connection c = MySqlConnection.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setInt(1, id_municipio);
            stmt.setInt(2, id_producto);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int puntoVentaId = rs.getInt("punto_venta_id");
                    String puntoVentaNombre = rs.getString("punto_venta_nombre");
                    String puntoVentaDireccion = rs.getString("punto_venta_direccion");
                    int precioImplicito = rs.getInt("precioImplicito");
                    int precioExplicito = rs.getInt("precioExplicito");
                    int divipola = rs.getInt("divipola");

                    SellPoint sellPoint = new SellPoint(puntoVentaId, puntoVentaNombre, puntoVentaDireccion, 
                                                        precioImplicito, precioExplicito, divipola);
                    sellPoints.add(sellPoint);  
                }
            }

            return Response.ok(sellPoints).build(); 

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en la consulta SQL: " + e.getMessage() + "\"}")
                    .build();
        }
    }


    @GET
    @Path("get_ten")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_ten(@QueryParam("offset") int offset) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
        ArrayList<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM productos LIMIT 10 OFFSET ?";

        try (Connection c = MySqlConnection.getConnection()) {

            try(PreparedStatement stmt = c.prepareStatement(sql)){
                stmt.setInt(1, offset);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String nameDane =  rs.getString("nombreDane");
                        String barCode = rs.getString("codigoBarras");
                        String name = rs.getString("nombre");
                        String unity = rs.getString("unidad");
                        String brand =  rs.getString("marca");
                        String company = rs.getString("empresa");
                        int idCategory = rs.getInt("idCategoria");
                        Product product = new Product(String.valueOf(id), nameDane, barCode, name, unity, brand, company, idCategory);
                        products.add(product);
                    }
                }
    
                return Response.ok(products).build();
            
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en la consulta SQL: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("get_municipios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_municipios() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }

        List<Municipio> municipios = new ArrayList<>();
        try (Connection c = MySqlConnection.getConnection()) {
            
            String sql = "SELECT * FROM municipios;";
            
            try (PreparedStatement statement = c.prepareStatement(sql)) {
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        municipios.add(new Municipio(rs.getInt("id"), rs.getString("nombre")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error on query SQL: " + e.getMessage(), e);
        }

        return Response.ok(municipios).build();
    }
    @GET
    @Path("get_proveedores")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_proveedores() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
        List<Provider> proveedores = new ArrayList<>();
        try (Connection c = MySqlConnection.getConnection()) {

            String sql = "SELECT * FROM proveedores;";

            try (PreparedStatement statement = c.prepareStatement(sql)) {
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        proveedores.add(new Provider(rs.getInt("id"), rs.getString("nombre"),rs.getString("telefono"),rs.getString("email"),rs.getString("contra"),null));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error on query SQL: " + e.getMessage(), e);
        }

        return Response.ok(proveedores).build();
    }

    @POST
    @Path("add_favorite")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFavorite(Favorite favorite) {
        String sql = "INSERT INTO consumidor_producto_punto_venta (idConsumidor, idProductoPuntoVenta) VALUES (?, ?)";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, favorite.getIdConsumidor());
            stmt.setInt(2, favorite.getIdProductoPuntoVenta());
            stmt.executeUpdate();
            return Response.ok("{\"message\":\"Favorite added successfully\"}").build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error adding favorite: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("remove_favorite")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeFavorite(Favorite favorite) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
        String sql = "DELETE FROM consumidor_producto_punto_venta WHERE idConsumidor = ? AND idProductoPuntoVenta = ?";
        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, favorite.getIdConsumidor());
            stmt.setInt(2, favorite.getIdProductoPuntoVenta());
            stmt.executeUpdate();
            return Response.ok("{\"message\":\"Favorite removed successfully\"}").build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error removing favorite: " + e.getMessage() + "\"}")
                    .build();
        }
    }
    @GET
    @Path("get_favorites")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFavorites(@QueryParam("idConsumidor") int idConsumidor) {
        String sql = """
    SELECT cp.idConsumidor, ppv.id AS idProductoPuntoVenta, p.nombre AS productName, 
           p.marca AS brand, pv.nombre AS sellPointName
    FROM consumidor_producto_punto_venta cp
    JOIN producto_punto_venta ppv ON cp.idProductoPuntoVenta = ppv.id
    JOIN producto_municipio pm ON ppv.idProductoMunicipio = pm.id
    JOIN productos p ON pm.idProducto = p.id
    JOIN puntos_venta pv ON ppv.idPuntoVenta = pv.id
    WHERE cp.idConsumidor = ?;
    """;

        try (Connection conn = MySqlConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idConsumidor);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Favorite> favorites = new ArrayList<>();
                while (rs.next()) {
                    Favorite favorite = new Favorite();
                    favorite.setIdConsumidor(rs.getInt("idConsumidor"));
                    favorite.setIdProductoPuntoVenta(rs.getInt("idProductoPuntoVenta"));
                    favorite.setProductName(rs.getString("productName"));
                    favorite.setBrand(rs.getString("brand"));
                    favorite.setSellPointName(rs.getString("sellPointName"));
                    favorites.add(favorite);
                }
                return Response.ok(favorites).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error fetching favorites: " + e.getMessage() + "\"}")
                    .build();
        }
    }





}
