package co.edu.uptc.entities;

public class SellPoint {
    private int id;
    private String name;
    private String address;
    private int priceImplicit;
    private int priceExplicit;
    private int divipola;
    public SellPoint(int id, String name, String address, int priceImplicit, int priceExplicit, int divipola) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.priceImplicit = priceImplicit;
        this.priceExplicit = priceExplicit;
        this.divipola = divipola;
    }
    public SellPoint(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getPriceImplicit() {
        return priceImplicit;
    }
    public void setPriceImplicit(int priceImplicit) {
        this.priceImplicit = priceImplicit;
    }
    public int getPriceExplicit() {
        return priceExplicit;
    }
    public void setPriceExplicit(int priceExplicit) {
        this.priceExplicit = priceExplicit;
    }
    public int getDivipola() {
        return divipola;
    }
    public void setDivipola(int divipola) {
        this.divipola = divipola;
    }

    


}
