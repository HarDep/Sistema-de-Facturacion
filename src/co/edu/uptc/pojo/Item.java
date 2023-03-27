package co.edu.uptc.pojo;

public class Item implements Cloneable{
    private int itemNumber;
    private Product product;
    private int amount;

    public Item(int itemNumber, Product product, int amount) {
        this.itemNumber = itemNumber;
        this.product = product;
        this.amount = amount;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public Item clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
