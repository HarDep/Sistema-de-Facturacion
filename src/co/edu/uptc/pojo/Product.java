package co.edu.uptc.pojo;

public class Product implements Cloneable{
    private String barcode;
    private String CIU;
    private String description;
    private int price;

    public Product(String barcode, String ciu, String description, int price) {
        this.barcode = barcode;
        this.CIU = ciu;
        this.description = description;
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCIU() {
        return CIU;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public Product clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (Product) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
