package co.edu.uptc.entities;

public class ProductMunicipio {
    private String producto;
    private String municipio;
    private int precioImplicito;
    private int precioExplicito;
    private int divipola;

    public ProductMunicipio(String producto, String municipio, int precioImplicito, int precioExplicito, int divipola) {
        this.producto = producto;
        this.municipio = municipio;
        this.precioImplicito = precioImplicito;
        this.precioExplicito = precioExplicito;
        this.divipola = divipola;
    }

    // Getters y setters
    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public int getPrecioImplicito() {
        return precioImplicito;
    }

    public void setPrecioImplicito(int precioImplicito) {
        this.precioImplicito = precioImplicito;
    }

    public int getPrecioExplicito() {
        return precioExplicito;
    }

    public void setPrecioExplicito(int precioExplicito) {
        this.precioExplicito = precioExplicito;
    }

    public int getDivipola() {
        return divipola;
    }

    public void setDivipola(int divipola) {
        this.divipola = divipola;
    }
}

