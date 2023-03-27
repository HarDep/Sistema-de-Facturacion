package co.edu.uptc.model;

import co.edu.uptc.config.ManagerProperties;
import co.edu.uptc.contract.MainContract;
import co.edu.uptc.pojo.*;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

public class Model implements MainContract.Model {
    private final ManagerBills  managerBills;
    private final ManagerPeople managerPeople;
    private final ManagerProducts managerProducts;
    private MainContract.Presenter presenter;
    private final ManagerProperties properties;
    public Model() throws Exception {
        properties=ManagerProperties.getInstance();
        managerProducts=new ManagerProducts();
        managerBills = new ManagerBills();
        managerPeople = new ManagerPeople();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void start() {
        this.presenter.start();
    }

    @Override
    public boolean isSomeProduct(String barcode, String ciu) {
        return managerProducts.isSomeProduct(barcode,ciu);
    }

    @Override
    public void addProduct(Product product) {
        managerProducts.addProduct(product);
    }

    @Override
    public Product getProduct(String ciu) {
        return managerProducts.getProduct(ciu);
    }

    @Override
    public void deleteProduct(Product product) {
        managerProducts.deleteProduct(product);
    }

    @Override
    public boolean isTheProductReferencedInABill(Product product) {
        return managerBills.isTheProductReferencedInABill(product);
    }

    @Override
    public boolean isSomeProduct(Product product, String barcode) {
        return managerProducts.isSomeProduct(product,barcode);
    }

    @Override
    public boolean isThisPerson(String docType, String docNum) {
        return managerPeople.isThisPerson(docType,docNum);
    }

    @Override
    public void addPerson(Person person) {
        managerPeople.addPerson(person);
    }

    @Override
    public Person getPerson(String docType, String docNum) {
        return managerPeople.getPerson(docType, docNum);
    }

    @Override
    public void deletePerson(Person person) {
        managerPeople.deletePerson(person);
    }

    @Override
    public boolean isThePersonReferencedInABill(Person person) {
        return managerBills.isThePersonReferencedInABill(person);
    }

    @Override
    public boolean isThisPerson(String docType, String docNum, Person per) {
        return managerPeople.isThisPerson(docType, docNum, per);
    }

    @Override
    public String getDate() {
        String[] b = String.valueOf(LocalDate.now()).split("-");
        return b[2]+"/"+b[1]+"/"+b[0];
    }

    @Override
    public int calculateIVA(int price) {
        return (int) (price*(Integer.parseInt(properties.get("percent_IVA"))*0.01));
    }

    @Override
    public void addBill(Bill bill) {
        managerBills.addBill(bill);
    }

    @Override
    public List<Bill> getBills(String billNum) {
        return managerBills.getBills(billNum);
    }

    @Override
    public void deleteBill(Bill bill) {
        managerBills.deleteBill(bill);
    }

    @Override
    public boolean isTheSameListItems(List<Item> list1, List<Item> list2) {
        if (list1.size()!=list2.size()){
            return false;
        }
        Iterator<Item> iterator1 = list1.iterator();
        Iterator<Item> iterator2 = list2.iterator();
        while (iterator1.hasNext()||iterator2.hasNext()){
            Item item1 = iterator1.next();
            Item item2 = iterator2.next();
            boolean ciu = item1.getProduct().getCIU().equals(item2.getProduct().getCIU());
            boolean description = item1.getProduct().getDescription().equals(item2.getProduct().getDescription());
            boolean price = item1.getProduct().getPrice() ==  item2.getProduct().getPrice();
            boolean amount = item1.getAmount() == item2.getAmount();
            if ((!ciu)||(!description)||(!price)||(!amount)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isTheSamePerson(Person person1,Person person2){
        return person1.getDocumentTye().equals(person2.getDocumentTye())&&
                person1.getDocumentNumber().equals(person2.getDocumentNumber());
    }

    @Override
    public void updateBill(Bill billToUpdate, String dateToChange, Person customerToChange, List<Item> itemsToChange, BillFoot billFootToChange) {
        managerBills.updateBill(billToUpdate, dateToChange, customerToChange, itemsToChange, billFootToChange);
    }

    @Override
    public void updatePerson(Person personToUpdate, String typeIdToChange, String idNumToChange, String nameToChange, String lastNameToChange, String resDirToChange, String cityResToChange) {
        managerPeople.updatePerson(personToUpdate, typeIdToChange, idNumToChange, nameToChange, lastNameToChange, resDirToChange, cityResToChange);
    }

    @Override
    public void updateProduct(Product productToUpdate, String barcodeToChange, String descriptionToChange, int priceToChange) {
        managerProducts.updateProduct(productToUpdate, barcodeToChange, descriptionToChange, priceToChange);
    }

    @Override
    public List<Bill> getList() {
        return managerBills.getList();
    }

    @Override
    public List<Person> getListPeople() {
        return managerPeople.getListPeople();
    }

    @Override
    public List<Product> getListProducts() {
        return managerProducts.getListProducts();
    }

    @Override
    public String getBillsForAPerson(Person person) {
        return managerBills.getBillsForAPerson(person);
    }

}
