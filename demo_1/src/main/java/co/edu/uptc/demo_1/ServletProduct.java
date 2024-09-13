package co.edu.uptc.demo_1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "servletProduct", value = "/servlet-product")
public class ServletProduct extends HttpServlet {
    public void setup(){

    }
    public void init() {
        setup();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
