package co.edu.uptc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import jakarta.ws.rs.QueryParam;
import co.edu.uptc.entities.Product;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import co.edu.uptc.handler.ProductHandler;


@Path("products")
public class Products {
    
    @GET
    @Path("get_ten")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> get_ten(@QueryParam("offset") int offset){
        ProductHandler ph = new ProductHandler();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }

        try {
            Connection c = MySqlConnection.getConnection();
            Statement statement = c.createStatement();
            
            String sql = "SELECT * FROM productos LIMIT 10 OFFSET " + offset;

            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                ph.addProduct(String.valueOf(rs.getInt("id")), rs.getString("nombreDANE"), rs.getString("codigoBarras"), rs.getString("nombre"), "", rs.getString("marca"), rs.getString("empresa"), "", "", rs.getInt("precioExplicito"), rs.getInt("precioExplicito"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ph.getProducts();
    }
}
