package co.edu.uptc.pojo;

public class BillHead {
    private String billNumber;
    private String billDate;
    private Person costumer;

    public BillHead(String billNumber, String billDate, Person costumer) {
        this.billNumber = billNumber;
        this.billDate = billDate;
        this.costumer = costumer;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public Person getCostumer() {
        return costumer;
    }

    public void setCostumer(Person costumer) {
        this.costumer = costumer;
    }
}
