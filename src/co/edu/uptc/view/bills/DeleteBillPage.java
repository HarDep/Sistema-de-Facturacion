package co.edu.uptc.view.bills;

import co.edu.uptc.pojo.Bill;
import co.edu.uptc.pojo.Item;
import co.edu.uptc.util.Util;
import co.edu.uptc.view.ActionPage;
import co.edu.uptc.view.SectionPage;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DeleteBillPage extends ActionPage<Bill> {
    private final ResultsBillsPage resultsBillsPage;
    private Bill bill;
    private JLabel billNumAndDate;
    private JLabel documentType;
    private JLabel documentNumber;
    private JLabel namePerson;
    private JLabel prices;
    private JPanel panelTable;
    private JScrollPane details;
    public DeleteBillPage(MainBoard mainBoard, SectionPage<Bill> sectionPage,ResultsBillsPage resultsBillsPage) {
        super(mainBoard,sectionPage,"action_2","section_2_singular");
        this.resultsBillsPage = resultsBillsPage;
    }

    @Override
    protected void backButtonAction() {
        resultsBillsPage.prepareShow();
    }

    @Override
    protected void actionButton() {
        if(mainBoard.getConfirmDialog("action_2","section_2_singular","confirm_action_2")){
            mainBoard.getPresenter().deleteBill(bill);
            JOptionPane.showMessageDialog(mainBoard, mainBoard.getValue("section_2_singular","message_deleted"), mainBoard.getValue("action_2","section_2_singular"), JOptionPane.INFORMATION_MESSAGE);
            sectionPage.beginDeleteAction();
        }
    }

    @Override
    protected void createFieldsSection() {
        panelContent.setLayout(new BorderLayout());
        JPanel head = new JPanel();
        head.setLayout(new BoxLayout(head,BoxLayout.PAGE_AXIS));
        billNumAndDate = new JLabel();
        head.add(billNumAndDate);
        documentType = new JLabel();
        head.add(documentType);
        documentNumber = new JLabel();
        head.add(documentNumber);
        namePerson = new JLabel();
        head.add(namePerson);
        panelContent.add(head,BorderLayout.NORTH);
        panelTable = new JPanel();
        panelTable.setLayout(new BorderLayout());
        panelTable.add(new JLabel(mainBoard.getValue("bill_field_3")),BorderLayout.NORTH);
        prices = new JLabel();
        panelContent.add(prices,BorderLayout.SOUTH);
        panelContent.add(panelTable,BorderLayout.CENTER);
        addKeyNavigable();
    }
    private void addKeyNavigable(){
        actionButton.addKeyListener(Util.getKeyListenerNavigable(backButton));
        backButton.addKeyListener(Util.getKeyListenerNavigable(actionButton));
    }

    @Override
    public void begin(Bill value) {
        bill = value;prepareContent();
        mainBoard.setToShow(this);
        actionButton.requestFocusInWindow();
    }
    private void prepareContent(){
        billNumAndDate.setText(mainBoard.getValue("bill_field_4")+" " + bill.getBillHead().getBillNumber()
                + ", " +mainBoard.getValue("bill_field_5") + bill.getBillHead().getBillDate());
        documentType.setText(mainBoard.getValue("bill_field_1","generic_word_1")+" "+ bill.getBillHead().getCostumer().getDocumentTye());
        documentNumber.setText(mainBoard.getValue("bill_field_1","generic_word_2")+" "+ bill.getBillHead().getCostumer().getDocumentNumber());
        namePerson.setText(mainBoard.getValue("bill_field_2")+" "+ bill.getBillHead().getCostumer().getName() + ", "+ bill.getBillHead().getCostumer().getLastName());
        if (details!=null){
            panelTable.remove(details);
        }
        List<Item> items = bill.getDetails();
        DefaultTableModel tableModel = new DefaultTableModel(mainBoard.getValue("items_table_fields").split(";"),0);
        for (Item item:items) {
            tableModel.addRow(new Object[]{item.getItemNumber(),item.getProduct().getCIU(),item.getProduct().getDescription()
                    ,item.getProduct().getPrice(),item.getAmount()});
        }
        JTable t = new JTable(tableModel);
        t.setEnabled(false);
        t.getTableHeader().setReorderingAllowed(false);
        details = new JScrollPane(t);
        panelTable.add(details,BorderLayout.CENTER);
        prices.setText(mainBoard.getValue("bill_field_8")+" " + bill.getBillFoot().getTotalProducts()
                + mainBoard.getValue("bill_field_9")+" " + bill.getBillFoot().getValueForIVA() +mainBoard.getValue("bill_field_10")+" " + bill.getBillFoot().getTotal());
    }

    @Override
    public void begin() {
    }
}
