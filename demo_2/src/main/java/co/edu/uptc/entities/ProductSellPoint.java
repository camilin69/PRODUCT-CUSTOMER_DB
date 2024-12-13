package co.edu.uptc.entities;

public class ProductSellPoint extends Product{
    private int idMunicipio;
    private int priceImplicit;
    private int priceExplicit;
    private int divipola;
    public ProductSellPoint(String id, String nameDane, String barcode, String name, String unity, String brand,
            String company, int idCategory, int idMunicipio, int priceImplicit, int priceExplicit, int divipola) {
        super(id, nameDane, barcode, name, unity, brand, company, idCategory);
        this.idMunicipio = idMunicipio;
        this.priceImplicit = priceImplicit;
        this.priceExplicit = priceExplicit;
        this.divipola = divipola;
    }
    public int getIdMunicipio() {
        return idMunicipio;
    }
    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
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
