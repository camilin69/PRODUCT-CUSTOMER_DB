package co.edu.uptc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.entities.Municipio;
import co.edu.uptc.entities.Product;
import co.edu.uptc.entities.ProductMunicipio;
import co.edu.uptc.entities.ProductSellPoint;
import co.edu.uptc.entities.Provider;
import co.edu.uptc.entities.SellPoint;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("products")
public class Products {

    @GET
    @Path("get_sell_point_consumer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_sell_point_consumer(@QueryParam("id_municipio") int id_municipio,
                                            @QueryParam("id_producto") int id_producto) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
        ArrayList<SellPoint> sellPoints = new ArrayList<>(); 

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
    @Path("get_sell_point_provider")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_sell_point_provider(@QueryParam("id_provider") int id_provider) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
        ArrayList<SellPoint> sellPoints = new ArrayList<>(); 

        String sql = """
                    SELECT pv.id AS punto_venta_id,
                            pv.nombre AS punto_venta_nombre,
                            pv.direccion AS punto_venta_direccion
                    FROM puntos_venta pv
                    JOIN proveedores p ON pv.idProveedor = p.id
                    WHERE pv.idProveedor = ?;
                    """;

        try (Connection c = MySqlConnection.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setInt(1, id_provider);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int puntoVentaId = rs.getInt("punto_venta_id");
                    String puntoVentaNombre = rs.getString("punto_venta_nombre");
                    String puntoVentaDireccion = rs.getString("punto_venta_direccion");

                    SellPoint sellPoint = new SellPoint(puntoVentaId, puntoVentaNombre, puntoVentaDireccion);
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
    @Path("get_product_municipio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_product_municipio(@QueryParam("id_municipio") int id_municipio,
                                          @QueryParam("id_producto") int id_producto) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
        ArrayList<ProductMunicipio> responses = new ArrayList<>();


        String sql = """
                     SELECT 
                         p.nombre AS Producto,
                         m.nombre AS Municipio,
                         pm.precioImplicito,
                         pm.precioExplicito,
                         pm.divipola
                     FROM 
                         producto_municipio pm
                     JOIN 
                         productos p ON pm.idProducto = p.id
                     JOIN 
                         municipios m ON pm.idMunicipio = m.id
                     WHERE 
                         m.id = ? 
                         AND p.id = ?; 
                     """;

            try (Connection c = MySqlConnection.getConnection()) {
                try(PreparedStatement stmt = c.prepareStatement(sql)){
                    stmt.setInt(1, id_municipio);
                    stmt.setInt(2, id_producto);  
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            ProductMunicipio response = new ProductMunicipio(
                                rs.getString("Producto"),
                                rs.getString("Municipio"),
                                rs.getInt("precioImplicito"),
                                rs.getInt("precioExplicito"),
                                rs.getInt("divipola")
                            );
                            responses.add(response);
                            break;
                        }
                    }
        
                    return Response.ok(responses).build();
        
                }
            } catch (SQLException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"message\":\"Error en la consulta SQL: " + e.getMessage() + "\"}")
                        .build();
            }
    }

    @GET
    @Path("get_searched_products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_searched_products(@QueryParam("offset") int offset,
                                        @QueryParam("attribute") String attribute,
                                        @QueryParam("category") int id_category,
                                        @QueryParam("search_input") String search_input) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
        ArrayList<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM productos WHERE 1=1";

        if (search_input != null && !search_input.isEmpty()) {
            sql += " AND nombre LIKE ?";
        }
        if (id_category > 0) {
            sql += " AND idCategoria = ?";
        }
        if (attribute != null && !attribute.isEmpty()) {
            sql += " AND " + attribute + " LIKE ?";
        }

        sql += " LIMIT 10 OFFSET ?";

        try (Connection c = MySqlConnection.getConnection()) {

            try(PreparedStatement stmt = c.prepareStatement(sql)) {
                int paramIndex = 1;
                if (search_input != null && !search_input.isEmpty()) {
                    stmt.setString(paramIndex++, "%" + search_input + "%");
                }
                if (id_category > 0) {
                    stmt.setInt(paramIndex++, id_category);
                }
                if (attribute != null && !attribute.isEmpty()) {
                    stmt.setString(paramIndex++, "%" + search_input + "%");
                }
                stmt.setInt(paramIndex, offset);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String nameDane = rs.getString("nombreDane");
                        String barCode = rs.getString("codigoBarras");
                        String name = rs.getString("nombre");
                        String unity = rs.getString("unidad");
                        String brand = rs.getString("marca");
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
@Path("get_product_sell_point")
@Produces(MediaType.APPLICATION_JSON)
public Response get_product_sell_point(@QueryParam("id_sell_point") int id_sell_point) {

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new RuntimeException("MySQL Driver not found", e);
    }
    
    ArrayList<ProductSellPoint> responses = new ArrayList<>();

    String sql = """
                 SELECT 
                    p.id as Producto,
                    p.nombre AS nombreProducto,
                    p.nombreDANE,
                    p.codigoBarras,
                    p.unidad,
                    p.marca,
                    p.empresa,
                    p.idCategoria,
                    m.id AS Municipio,
                    pm.precioImplicito,
                    pm.precioExplicito,
                    pm.divipola
                FROM 
                    producto_punto_venta ppv
                JOIN 
                    producto_municipio pm ON ppv.idProductoMunicipio = pm.id
                JOIN 
                    productos p ON pm.idProducto = p.id
                JOIN 
                    municipios m ON pm.idMunicipio = m.id
                WHERE 
                    ppv.idPuntoVenta = ?;
                 """;

    try (Connection c = MySqlConnection.getConnection()) {
        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, id_sell_point);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProductSellPoint response = new ProductSellPoint(
                        String.valueOf(rs.getInt("Producto")),
                        rs.getString("nombreProducto"),
                        rs.getString("nombreDANE"),
                        rs.getString("codigoBarras"),
                        rs.getString("unidad"),
                        rs.getString("marca"),
                        rs.getString("empresa"),
                        rs.getInt("idCategoria"),
                        rs.getInt("Municipio"),
                        rs.getInt("precioImplicito"),
                        rs.getInt("precioExplicito"),
                        rs.getInt("divipola")
                    );
                    responses.add(response);  
                }
            }

            return Response.ok(responses).build();  
        }
    } catch (SQLException e) {
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
    @Path("add_products_sell_point")
    @Produces(MediaType.APPLICATION_JSON)
    public Response add_products_sell_point(@QueryParam("id_municipio") int id_municipio,
                                            @QueryParam("id_product") int id_product,
                                            @QueryParam("id_sell_point") int id_sell_point) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }

        String sqlGetIdProductoMunicipio = """
                SELECT id
                FROM producto_municipio
                WHERE idProducto = ? AND idMunicipio = ?;
                """;

        String sqlInsertProductoPuntoVenta = """
                INSERT INTO producto_punto_venta (idProductoMunicipio, idPuntoVenta)
                VALUES (?, ?);
                """;

        try (Connection c = MySqlConnection.getConnection()) {
            int idProductoMunicipio = -1;
            try (PreparedStatement stmt = c.prepareStatement(sqlGetIdProductoMunicipio)) {
                stmt.setInt(1, id_product);
                stmt.setInt(2, id_municipio);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        idProductoMunicipio = rs.getInt("id");
                    }
                }
            }

            if (idProductoMunicipio != -1) {
                try (PreparedStatement stmt = c.prepareStatement(sqlInsertProductoPuntoVenta)) {
                    stmt.setInt(1, idProductoMunicipio);
                    stmt.setInt(2, id_sell_point);
                    stmt.executeUpdate();
                }

                return Response.ok("{\"message\":\"Producto agregado al punto de venta correctamente\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\":\"No se encontró el producto en el municipio especificado.\"}")
                        .build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en la consulta SQL: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("remove_product_sell_point")
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove_product_sell_point(@QueryParam("id_municipio") int id_municipio,
                                            @QueryParam("id_product") int id_product,
                                            @QueryParam("id_sell_point") int id_sell_point) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }

        String sqlDeleteProductoPuntoVenta = """
            DELETE FROM producto_punto_venta
            WHERE idPuntoVenta = ? 
            AND idProductoMunicipio IN (
                SELECT id FROM producto_municipio 
                WHERE idProducto = ? 
                AND idMunicipio = ?
            );
        """;

        try (Connection c = MySqlConnection.getConnection()) {
            try (PreparedStatement stmt = c.prepareStatement(sqlDeleteProductoPuntoVenta)) {
                // Establecer los parámetros de la consulta
                stmt.setInt(1, id_sell_point);  
                stmt.setInt(2, id_product);      
                stmt.setInt(3, id_municipio);    

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    return Response.ok("{\"message\": \"Producto eliminado correctamente del punto de venta.\"}").build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("{\"message\": \"No se encontró el producto en el punto de venta especificado.\"}")
                            .build();
                }
            }
        } catch (SQLException e) {
            // En caso de error con la base de datos, devolver un mensaje de error
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Error en la consulta SQL: " + e.getMessage() + "\"}")
                    .build();
        }
    }


    @GET
    @Path("add_sell_point")
    @Produces(MediaType.APPLICATION_JSON)
    public Response add_sell_point(@QueryParam("id_provider") int id_provider,
                                    @QueryParam("name") String name,
                                    @QueryParam("address") String address) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }

        String sqlInsertPuntosVenta = """
                INSERT INTO puntos_venta (idProveedor, nombre, direccion)
                VALUES (?, ?, ?);
                """;

        try (Connection c = MySqlConnection.getConnection()) {
            try (PreparedStatement stmt = c.prepareStatement(sqlInsertPuntosVenta, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, id_provider);
                stmt.setString(2, name);
                stmt.setString(3, address);
                
                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            return Response.ok("{\"message\":\"Punto de venta agregado con éxito.\", \"id\": " + generatedId + "}").build();
                        } else {
                            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                    .entity("{\"message\":\"Error al obtener el ID generado\"}")
                                    .build();
                        }
                    }
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("{\"message\":\"Error al insertar el punto de venta\"}")
                            .build();
                }
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en la consulta SQL: " + e.getMessage() + "\"}")
                    .build();
        }
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
