package co.edu.uptc.entities;

public class Product {
    private String id;
    private String nameDane;
    private String barcode;
    private String name;
    private String unity;
    private String brand;
    private String company;
    private int idCategory;

    public Product() {}

    
    
    public Product(String id, String nameDane, String barcode, String name, String unity, String brand, String company,
            int idCategory) {
        this.id = id;
        this.nameDane = nameDane;
        this.barcode = barcode;
        this.name = name;
        this.unity = unity;
        this.brand = brand;
        this.company = company;
        this.idCategory = idCategory;
    }



    // Getters y Setters
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
    public int getIdCategory() {
        return idCategory;
    }
    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
}
