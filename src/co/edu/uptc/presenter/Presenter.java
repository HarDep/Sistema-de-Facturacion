package co.edu.uptc.presenter;

import co.edu.uptc.contract.MainContract;
import co.edu.uptc.pojo.*;

import java.util.List;

public class Presenter implements MainContract.Presenter {
    private MainContract.View view;
    private MainContract.Model model;
    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void setModel(MainContract.Model model) {
        this.model = model;
    }

    @Override
    public void start() {
        this.view.start();
    }

    @Override
    public boolean isSomeProduct(String barcode, String ciu) {
        return model.isSomeProduct(barcode, ciu);
    }

    @Override
    public void addProduct(Product product) {
        model.addProduct(product);
    }

    @Override
    public Product getProduct(String ciu) {
        return model.getProduct(ciu);
    }

    @Override
    public void deleteProduct(Product product) {
        model.deleteProduct(product);
    }

    @Override
    public boolean isTheProductReferencedInABill(Product product) {
        return model.isTheProductReferencedInABill(product);
    }

    @Override
    public boolean isSomeProduct(Product product, String barcode) {
        return model.isSomeProduct(product,barcode);
    }

    @Override
    public boolean isThisPerson(String docType, String docNum) {
        return model.isThisPerson(docType, docNum);
    }

    @Override
    public void addPerson(Person person) {
        model.addPerson(person);
    }

    @Override
    public Person getPerson(String docType, String docNum) {
        return model.getPerson(docType, docNum);
    }

    @Override
    public void deletePerson(Person person) {
        model.deletePerson(person);
    }

    @Override
    public boolean isThePersonReferencedInABill(Person person) {
        return model.isThePersonReferencedInABill(person);
    }

    @Override
    public boolean isThisPerson(String docType, String docNum, Person per) {
        return model.isThisPerson(docType, docNum, per);
    }

    @Override
    public String getDate() {
        return model.getDate();
    }

    @Override
    public int calculateIVA(int price) {
        return model.calculateIVA(price);
    }

    @Override
    public void addBill(Bill bill) {
        model.addBill(bill);
    }

    @Override
    public List<Bill> getBills(String billNum) {
        return model.getBills(billNum);
    }

    @Override
    public void deleteBill(Bill bill) {
        model.deleteBill(bill);
    }

    @Override
    public boolean isTheSameListItems(List<Item> list1, List<Item> list2) {
        return model.isTheSameListItems(list1,list2);
    }

    @Override
    public boolean isTheSamePerson(Person person1, Person person2) {
        return model.isTheSamePerson(person1,person2);
    }

    @Override
    public void updateBill(Bill billToUpdate, String dateToChange, Person customerToChange, List<Item> itemsToChange, BillFoot billFootToChange) {
        model.updateBill(billToUpdate, dateToChange, customerToChange, itemsToChange, billFootToChange);
    }

    @Override
    public void updatePerson(Person personToUpdate, String typeIdToChange, String idNumToChange, String nameToChange, String lastNameToChange, String resDirToChange, String cityResToChange) {
        model.updatePerson(personToUpdate, typeIdToChange, idNumToChange, nameToChange, lastNameToChange, resDirToChange, cityResToChange);
    }

    @Override
    public void updateProduct(Product productToUpdate, String barcodeToChange, String descriptionToChange, int priceToChange) {
        model.updateProduct(productToUpdate, barcodeToChange, descriptionToChange, priceToChange);
    }

    @Override
    public List<Bill> getList() {
        return model.getList();
    }

    @Override
    public List<Person> getListPeople() {
        return model.getListPeople();
    }

    @Override
    public List<Product> getListProducts() {
        return model.getListProducts();
    }

    @Override
    public String getBillsForAPerson(Person person) {
        return model.getBillsForAPerson(person);
    }
}
