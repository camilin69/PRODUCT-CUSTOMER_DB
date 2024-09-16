package co.edu.uptc.demo_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductHandler {
    private List<Product> products;

    public ProductHandler(){
        products = new ArrayList<>();
    }

    public Product findProduct(String id_product){
        Optional<Product> product = products.stream().filter(p -> p.getId_product().equals(id_product)).findFirst();
        return product.orElse(null);
    }

    public boolean addProduct(String id_product, String nameDane, String barcode, String name, String unity, String brand, String company, String divipola, String municipio, double priceImplicit, double priceExplicit){
        if(findProduct(id_product) == null){
            Product product = new Product(id_product, nameDane, barcode, name, unity, brand, company, divipola, municipio, priceImplicit, priceExplicit);
            products.add(product);
            return true;
        }
        return false;    
    }

    public boolean deleteProduct(String id_product){
        Product product = findProduct(id_product);
        if(product != null){
            products.remove(product);
            return true;
        }
        return false;
    }

    public List<Product> getProducts() {
        return products;
    }

    
}
