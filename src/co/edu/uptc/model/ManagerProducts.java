package co.edu.uptc.model;
import co.edu.uptc.model.simpleList.UPTCList;
import co.edu.uptc.pojo.Product;

import java.util.List;

public class ManagerProducts {
    private final List<Product> products;

    public ManagerProducts() {
        this.products = new UPTCList<>();
        //añadir valores predeterminados
        products.add(new Product("1","1","aa",10));
        products.add(new Product("2","2","bb",15));
        products.add(new Product("3","3","cc",18));
    }

    public boolean isSomeProduct(String barcode, String ciu){
        for (Product product:products) {
            if (product.getBarcode().equals(barcode)||product.getCIU().equals(ciu)){
                return true;
            }
        }
        return false;
    }
    public boolean isSomeProduct(Product prod, String barcode){
        for (Product product:products) {
            if ((!product.getCIU().equals(prod.getCIU()))&&product.getBarcode().equals(barcode)){
                return true;
            }
        }
        return false;
    }
    public void addProduct(Product product){
        products.add(product.clone());
    }

    public Product getProduct(String ciu){
        for (Product product: products) {
            if (product.getCIU().equals(ciu)){
                return product.clone();
            }
        }
        return null;
    }
    public void deleteProduct(Product product){
        Product remove = null;
        for (Product prod:products) {
            if (prod.getCIU().equals(product.getCIU())){
                remove = prod;
                break;
            }
        }
        products.remove(remove);
    }
    public void updateProduct(Product productToUpdate,String barcodeToChange,String descriptionToChange,int priceToChange){
        for (Product product:products) {
            if (product.getCIU().equals(productToUpdate.getCIU())){
                product.setBarcode(new String(barcodeToChange));
                product.setDescription(new String(descriptionToChange));
                product.setPrice(priceToChange);
                break;
            }
        }
    }
    public List<Product> getListProducts(){
        List<Product> list = new UPTCList<>();
        for (Product product:products) {
            list.add(product.clone());
        }
        return list;
    }
}
