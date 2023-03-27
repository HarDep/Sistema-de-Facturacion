package co.edu.uptc.pojo;

import java.util.List;

public class Bill implements Cloneable{
    private BillHead billHead;
    private List<Item> details;
    private BillFoot billFoot;

    public Bill(BillHead billHead, List<Item> list, BillFoot billFoot) {
        this.billHead = billHead;
        this.details = list;
        this.billFoot = billFoot;
    }

    public BillHead getBillHead() {
        return billHead;
    }

    public List<Item> getDetails() {
        return details;
    }

    public void setDetails(List<Item> details) {
        this.details = details;
    }

    public BillFoot getBillFoot() {
        return billFoot;
    }

    public void setBillFoot(BillFoot billFoot) {
        this.billFoot = billFoot;
    }

    @Override
    public Bill clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (Bill) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
