package co.edu.uptc.entities;

public class Product_Historic {
    private int idProducto;
    private String date;
    private double priceImplicit;
    private double priceExplicit;
    
    public Product_Historic(int idProducto, String date, double priceImplicit, double priceExplicit) {
        this.idProducto = idProducto;
        this.date = date;
        this.priceImplicit = priceImplicit;
        this.priceExplicit = priceExplicit;
    }
    public int getIdProducto() {
        return idProducto;
    }
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public double getPriceImplicit() {
        return priceImplicit;
    }
    public void setPriceImplicit(double priceImplicit) {
        this.priceImplicit = priceImplicit;
    }
    public double getPriceExplicit() {
        return priceExplicit;
    }
    public void setPriceExplicit(double priceExplicit) {
        this.priceExplicit = priceExplicit;
    }

    
}
