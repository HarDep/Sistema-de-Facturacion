package co.edu.uptc.pojo;

public class BillFoot implements Cloneable{
    private int totalProducts;
    private int valueForIVA;
    private int total;

    public BillFoot(int totalProducts, int valueForIVA, int total) {
        this.totalProducts = totalProducts;
        this.valueForIVA = valueForIVA;
        this.total = total;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public int getValueForIVA() {
        return valueForIVA;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public BillFoot clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (BillFoot) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
