package co.edu.uptc.contract;

import co.edu.uptc.pojo.*;

import java.util.List;

public interface MainContract {
    interface Model{
        void setPresenter(Presenter presenter);
        void start();
        boolean isSomeProduct(String barcode, String ciu);
        void addProduct(Product product);
        Product getProduct(String ciu);
        void deleteProduct(Product product);
        boolean isTheProductReferencedInABill(Product product);
        boolean isSomeProduct(Product product, String barcode);
        boolean isThisPerson(String docType,String docNum);
        void addPerson(Person person);
        Person getPerson(String docType,String docNum);
        void deletePerson(Person person);
        boolean isThePersonReferencedInABill(Person person);
        boolean isThisPerson(String docType,String docNum,Person per);
        String getDate();
        int calculateIVA(int price);
        void addBill(Bill bill);
        List<Bill> getBills(String billNum);
        void deleteBill(Bill bill);
        boolean isTheSameListItems(List<Item> list1, List<Item> list2);
        boolean isTheSamePerson(Person person1,Person person2);
        void updateBill(Bill billToUpdate,String dateToChange,Person customerToChange,
                        List<Item> itemsToChange, BillFoot billFootToChange);
        void updatePerson(Person personToUpdate,String typeIdToChange,String idNumToChange,String nameToChange,
                          String lastNameToChange,String resDirToChange,String cityResToChange);
        void updateProduct(Product productToUpdate,String barcodeToChange,String descriptionToChange,int priceToChange);
        List<Bill> getList();
        List<Person> getListPeople();
        List<Product> getListProducts();
        String getBillsForAPerson(Person person);
    }
    interface Presenter{
        void setView(View view);
        void setModel(Model model);
        void start();
        boolean isSomeProduct(String barcode, String ciu);
        void addProduct(Product product);
        Product getProduct(String ciu);
        void deleteProduct(Product product);
        boolean isTheProductReferencedInABill(Product product);
        boolean isSomeProduct(Product product, String barcode);
        boolean isThisPerson(String docType,String docNum);
        void addPerson(Person person);
        Person getPerson(String docType,String docNum);
        void deletePerson(Person person);
        boolean isThePersonReferencedInABill(Person person);
        boolean isThisPerson(String docType,String docNum,Person per);
        String getDate();
        int calculateIVA(int price);
        void addBill(Bill bill);
        List<Bill> getBills(String billNum);
        void deleteBill(Bill bill);
        boolean isTheSameListItems(List<Item> list1, List<Item> list2);
        boolean isTheSamePerson(Person person1,Person person2);
        void updateBill(Bill billToUpdate,String dateToChange,Person customerToChange,
                        List<Item> itemsToChange, BillFoot billFootToChange);
        void updatePerson(Person personToUpdate,String typeIdToChange,String idNumToChange,String nameToChange,
                          String lastNameToChange,String resDirToChange,String cityResToChange);
        void updateProduct(Product productToUpdate,String barcodeToChange,String descriptionToChange,int priceToChange);
        List<Bill> getList();
        List<Person> getListPeople();
        List<Product> getListProducts();
        String getBillsForAPerson(Person person);
    }
    interface View{
        void setPresenter(Presenter presenter);
        void start();
    }
}
