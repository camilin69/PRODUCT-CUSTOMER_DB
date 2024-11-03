package co.edu.uptc.entities;

public class Product {
    private String id_product;
    private String nameDane;
    private String barcode;
    private String name;
    private String unity;
    private String brand;
    private String company;
    private String divipola;
    private String municipio;
    private double priceImplicit;
    private double priceExplicit;

    public Product(String id_product, String nameDane, String barcode, String name, String unity, String brand, String company, String divipola, String municipio, double priceImplicit, double priceExplicit) {
        this.id_product = id_product;
        this.nameDane = nameDane;
        this.barcode = barcode;
        this.name = name;
        this.unity = unity;
        this.brand = brand;
        this.company = company;
        this.divipola = divipola;
        this.municipio = municipio;
        this.priceImplicit = priceImplicit;
        this.priceExplicit = priceExplicit;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getNameDane() {
        return nameDane;
    }

    public void setNameDane(String nameDane) {
        this.nameDane = nameDane;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDivipola() {
        return divipola;
    }

    public void setDivipola(String divipola) {
        this.divipola = divipola;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
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
