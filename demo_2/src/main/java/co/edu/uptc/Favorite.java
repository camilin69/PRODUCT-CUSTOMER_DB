package co.edu.uptc;



public class FavoriteDTO {

    private int idConsumidor;
    private int idProductoPuntoVenta;
    private String productName;
    private String brand;
    private String sellPointName;

    public FavoriteDTO() {
    }

    public FavoriteDTO(int idConsumidor, int idProductoPuntoVenta, String productName, String brand, String sellPointName) {
        this.idConsumidor = idConsumidor;
        this.idProductoPuntoVenta = idProductoPuntoVenta;
        this.productName = productName;
        this.brand = brand;
        this.sellPointName = sellPointName;
    }

    public int getIdConsumidor() {
        return idConsumidor;
    }

    public void setIdConsumidor(int idConsumidor) {
        this.idConsumidor = idConsumidor;
    }

    public int getIdProductoPuntoVenta() {
        return idProductoPuntoVenta;
    }

    public void setIdProductoPuntoVenta(int idProductoPuntoVenta) {
        this.idProductoPuntoVenta = idProductoPuntoVenta;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSellPointName() {
        return sellPointName;
    }

    public void setSellPointName(String sellPointName) {
        this.sellPointName = sellPointName;
    }

    @Override
    public String toString() {
        return "FavoriteDTO{" +
                "idConsumidor=" + idConsumidor +
                ", idProductoPuntoVenta=" + idProductoPuntoVenta +
                ", productName='" + productName + '\'' +
                ", brand='" + brand + '\'' +
                ", sellPointName='" + sellPointName + '\'' +
                '}';
    }
}
