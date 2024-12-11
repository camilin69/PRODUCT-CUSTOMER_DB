package co.edu.uptc.entities;

public class Product {
    private String id;
    private String nameDane;
    private String barcode;
    private String name;
    private String unity;
    private String brand;
    private String company;
    private int id_category;
    public Product(String id, String nameDane, String barcode, String name, String unity, String brand, String company,
            int id_category) {
        this.id = id;
        this.nameDane = nameDane;
        this.barcode = barcode;
        this.name = name;
        this.unity = unity;
        this.brand = brand;
        this.company = company;
        this.id_category = id_category;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public int getId_category() {
        return id_category;
    }
    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    

    

    
}
