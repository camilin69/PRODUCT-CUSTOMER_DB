package co.edu.uptc.entities;

public class ProductFavorite {
    private int id;
    private String name;
    private String brand;
    private String company;
    private SellPoint sellPoint;

    public ProductFavorite(int id, String name, String brand, String company, SellPoint sellPoint) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.company = company;
        this.sellPoint = sellPoint;
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

    public SellPoint getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(SellPoint sellPoint) {
        this.sellPoint = sellPoint;
    }
}
