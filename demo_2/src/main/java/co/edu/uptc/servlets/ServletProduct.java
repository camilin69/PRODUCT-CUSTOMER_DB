package co.edu.uptc.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import co.edu.uptc.handler.ProductHandler;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "servletProduct", value = "/servlet-product")
public class ServletProduct extends HttpServlet {
    private ProductHandler ph;

    public void setup(){
        ph = new ProductHandler();

        ph.addProduct("1","Aceite de girasol", "7701018007151", "CAJ.ACEITE GIRASOL PREMIER  X1000CC", "Centímetros cúbicos", "PREMIER", "ABASTECEMOS DE OCCIDENTE S.A.", "76520", "Palmira", 12000, 10168);
        ph.addProduct("2","Aceite de girasol", "7701018007212", "CAJ.ACEITE GIRASOL PREMIER X500CC", "Centímetros cúbicos", "PREMIER", "ABASTECEMOS DE OCCIDENTE S.A.", "76520", "Palmira", 6300, 5651);
        ph.addProduct("3","Aceite de girasol", "7701018065991", "CAJ.ACEITE GIRASOL OLEOCALI X900CC.", "Centímetros cúbicos", "PREMIER", "ABASTECEMOS DE OCCIDENTE S.A.", "76520", "Palmira", 8250, 7269);
        ph.addProduct("4","Aceite de girasol", "7773103000057", "ACEITE FINO SOYA GIRASOL X1LTR", "Litros", "OLEOCALI", "ABASTECEMOS DE OCCIDENTE S.A.", "76520", "Palmira", 7250, 6302);
        ph.addProduct("5","Aceite de girasol", "7701018007151", "CAJ.ACEITE GIRASOL PREMIER  X1000CC", "Centímetros cúbicos", "FINO", "ABASTECEMOS DE OCCIDENTE S.A.", "76520", "Palmira", 12019, 10168);
        ph.addProduct("6","Aceite de girasol", "7701018007212", "CAJ.ACEITE GIRASOL PREMIER X500CC", "Centímetros cúbicos", "PREMIER", "ABASTECEMOS DE OCCIDENTE S.A.", "76892", "Yumbo", 6391, 5798);

    }

    public void init() {
        setup();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String getType = request.getParameter("getType");

        response.setContentType("application/json");

        Gson gson = new Gson();

        try (PrintWriter out = response.getWriter()) {
            
            if(getType.equals("get_raw_product_list")){
                out.print(gson.toJson(ph.getProducts()));
            }
        }
    }
}
