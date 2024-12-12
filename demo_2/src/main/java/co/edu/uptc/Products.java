package co.edu.uptc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import co.edu.uptc.entities.Product;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("products")
public class Products {

    @GET
    @Path("get_all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM productos";

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("id"),
                        rs.getString("nombreDane"),
                        rs.getString("codigoBarras"),
                        rs.getString("nombre"),
                        rs.getString("unidad"),
                        rs.getString("marca"),
                        rs.getString("empresa"),
                        rs.getInt("id_category"),
                        rs.getDouble("precioImplicito"),
                        rs.getDouble("precioExplicito")
                );
                products.add(product);
            }

            return Response.ok(products).build();

        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en la consulta SQL: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("get_paginated")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaginatedProducts(@QueryParam("page") @DefaultValue("1") int page,
                                         @QueryParam("size") @DefaultValue("10") int size) {
        ArrayList<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM productos LIMIT ? OFFSET ?";

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setInt(1, size);
            stmt.setInt(2, (page - 1) * size);

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
                            rs.getInt("id_category"),
                            rs.getDouble("precioImplicito"),
                            rs.getDouble("precioExplicito")
                    );
                    products.add(product);
                }
            }

            return Response.ok(products).build();

        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en la consulta SQL: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(Product product) {
        String sql = "INSERT INTO productos (id, nombreDane, codigoBarras, nombre, unidad, marca, empresa, id_category, precioImplicito, precioExplicito) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setString(1, product.getId());
            stmt.setString(2, product.getNameDane());
            stmt.setString(3, product.getBarcode());
            stmt.setString(4, product.getName());
            stmt.setString(5, product.getUnity());
            stmt.setString(6, product.getBrand());
            stmt.setString(7, product.getCompany());
            stmt.setInt(8, product.getIdCategory());
            stmt.setDouble(9, product.getPriceImplicit());
            stmt.setDouble(10, product.getPriceExplicit());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\":\"Producto creado exitosamente\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\":\"No se pudo crear el producto\"}")
                        .build();
            }

        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en la consulta SQL: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("id") String id, Product product) {
        String sql = "UPDATE productos SET nombreDane = ?, codigoBarras = ?, nombre = ?, unidad = ?, marca = ?, empresa = ?, id_category = ?, precioImplicito = ?, precioExplicito = ? WHERE id = ?";

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setString(1, product.getNameDane());
            stmt.setString(2, product.getBarcode());
            stmt.setString(3, product.getName());
            stmt.setString(4, product.getUnity());
            stmt.setString(5, product.getBrand());
            stmt.setString(6, product.getCompany());
            stmt.setInt(7, product.getIdCategory());
            stmt.setDouble(8, product.getPriceImplicit());
            stmt.setDouble(9, product.getPriceExplicit());
            stmt.setString(10, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return Response.ok("{\"message\":\"Producto actualizado exitosamente\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"Producto no encontrado\"}")
                        .build();
            }

        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en la consulta SQL: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("id") String id) {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setString(1, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return Response.ok("{\"message\":\"Producto eliminado exitosamente\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"Producto no encontrado\"}")
                        .build();
            }

        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en la consulta SQL: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}
