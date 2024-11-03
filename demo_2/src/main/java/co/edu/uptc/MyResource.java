package co.edu.uptc;

import java.util.List;

import co.edu.uptc.entities.Product;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import co.edu.uptc.handler.ProductHandler;


@Path("myresource")
public class MyResource {

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getIt() {
        ProductHandler ph = new ProductHandler();
        ph.addProduct("1","Aceite de girasol", "7701018007151", "CAJ.ACEITE GIRASOL PREMIER  X1000CC", "Centímetros cúbicos", "PREMIER", "ABASTECEMOS DE OCCIDENTE S.A.", "76520", "Palmira", 12000, 10168);
        ph.addProduct("2","Aceite de girasol", "7701018007212", "CAJ.ACEITE GIRASOL PREMIER X500CC", "Centímetros cúbicos", "PREMIER", "ABASTECEMOS DE OCCIDENTE S.A.", "76520", "Palmira", 6300, 5651);
        ph.addProduct("3","Aceite de girasol", "7701018065991", "CAJ.ACEITE GIRASOL OLEOCALI X900CC.", "Centímetros cúbicos", "PREMIER", "ABASTECEMOS DE OCCIDENTE S.A.", "76520", "Palmira", 8250, 7269);
        ph.addProduct("4","Aceite de girasol", "7773103000057", "ACEITE FINO SOYA GIRASOL X1LTR", "Litros", "OLEOCALI", "ABASTECEMOS DE OCCIDENTE S.A.", "76520", "Palmira", 7250, 6302);
        ph.addProduct("5","Aceite de girasol", "7701018007151", "CAJ.ACEITE GIRASOL PREMIER  X1000CC", "Centímetros cúbicos", "FINO", "ABASTECEMOS DE OCCIDENTE S.A.", "76520", "Palmira", 12019, 10168);
        ph.addProduct("6","Aceite de girasol", "7701018007212", "CAJ.ACEITE GIRASOL PREMIER X500CC", "Centímetros cúbicos", "PREMIER", "ABASTECEMOS DE OCCIDENTE S.A.", "76892", "Yumbo", 6391, 5798);

        return ph.getProducts();
    }
}
