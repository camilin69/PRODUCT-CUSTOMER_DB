package co.edu.uptc.demo_1.servlets;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "servletConsumer", value = "/servlet-consumer")
public class ServletConsumer extends HttpServlet {
    private String message;

    public void setup(){

    }
    public void init() {
        setup();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}