package co.edu.uptc.model;
import co.edu.uptc.model.simpleList.UPTCList;
import co.edu.uptc.pojo.*;

import java.util.List;

public class ManagerBills {
    private final List<Bill> bills;

    public ManagerBills() {
        this.bills = new UPTCList<>();
        //añadir valores predeterminados
        //si no se usa estas facturas predeterminadas, se debe poner el generador del numero de factura inicial en 1
        //se realiza en el archivo config.properties en la clave innit_bill_number en la que se cambia el valor a 1
        List<Item> a = new UPTCList<>();
        a.add(new Item(1,new Product("1","1","aa",10),4));
        a.add(new Item(2,new Product("2","2","bb",15),7));
        for (int i = 1; i < 15; i++) {
            bills.add(new Bill(new BillHead(i+"","01/01/2023",
                    new Person("C.C.","1","juan","perez","xx","ii")),
                    a,new BillFoot(145,27,172)));
            //se pone los valores manualmente porque al añadirlos en la interfaz grafica se calculan los valores
        }
    }
    public boolean isTheProductReferencedInABill(Product product){
        for (Bill bill:bills) {
            for (Item item:bill.getDetails()) {
                if (item.getProduct().getCIU().equals(product.getCIU())){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isThePersonReferencedInABill(Person person){
        for (Bill bill:bills) {
            if (bill.getBillHead().getCostumer().getDocumentTye().equals(person.getDocumentTye())&&
            bill.getBillHead().getCostumer().getDocumentNumber().equals(person.getDocumentNumber())){
                return true;
            }
        }
        return false;
    }
    public void addBill(Bill bill){
        bills.add(bill.clone());
    }
    public List<Bill> getBills(String billNum){
        List<Bill> res = new UPTCList<>();
        for (Bill bill:bills) {
            if (bill.getBillHead().getBillNumber().contains(billNum)){
                res.add(bill.clone());
            }
        }
        return res;
    }
    public void deleteBill(Bill bill){
        Bill remove = null;
        for (Bill bill1:bills) {
            if (bill1.getBillHead().getBillNumber().equals(bill.getBillHead().getBillNumber())){
                remove=bill1;
            }
        }
        bills.remove(remove);
    }
    public void updateBill(Bill billToUpdate,String dateToChange,Person customerToChange,
                           List<Item> itemsToChange, BillFoot billFootToChange){
        for (Bill bill:bills) {
            if (bill.getBillHead().getBillNumber().equals(billToUpdate.getBillHead().getBillNumber())){
                bill.getBillHead().setBillDate(new String(dateToChange));
                bill.getBillHead().setCostumer(customerToChange.clone());
                bill.setDetails(getListItemsClone(itemsToChange));
                bill.setBillFoot(billFootToChange.clone());
                break;
            }
        }
    }
    private List<Item> getListItemsClone(List<Item> list){
        List<Item> newList = new UPTCList<>();
        for (Item item:list) {
            newList.add(item.clone());
        }
        return newList;
    }
    public List<Bill> getList(){
        List<Bill> list = new UPTCList<>();
        for (Bill bill:bills) {
            list.add(bill.clone());
        }
        return list;
    }
    public String getBillsForAPerson(Person person){
        String billsNumbers = "";
        for (Bill bill:bills) {
            if (bill.getBillHead().getCostumer().getDocumentTye().equals(person.getDocumentTye())&&
                    bill.getBillHead().getCostumer().getDocumentNumber().equals(person.getDocumentNumber())){
                billsNumbers+=bill.getBillHead().getBillNumber() + " - ";
            }
        }
        return billsNumbers;
    }
}
