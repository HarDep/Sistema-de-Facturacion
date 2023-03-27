package co.edu.uptc.view.bills;

import co.edu.uptc.model.simpleList.UPTCList;
import co.edu.uptc.pojo.*;
import co.edu.uptc.util.Util;
import co.edu.uptc.view.ActionPage;
import co.edu.uptc.view.EditorTable;
import co.edu.uptc.view.SectionPage;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class AddBillPage extends ActionPage<Bill> {
    private int billNum;
    private String billDate;
    private JLabel billNumCont;
    private JLabel billDateCont;
    private JComboBox<String> documentType;
    private JTextField documentNumber;
    private JLabel namePerson;
    private Person personSelected;
    private DefaultTableModel details;
    private JScrollPane scroll;
    private JPanel panelTable;
    private JLabel prices;
    private int price;
    private int iva;
    private int total;
    private JButton addItem;
    private JButton removeItem;

    public AddBillPage(MainBoard mainBoard, SectionPage<Bill> sectionPage) {
        super(mainBoard,sectionPage,"action_1","section_2_singular");
        billNum = mainBoard.getNumber("innit_bill_number");
    }

    private boolean isSomeEmpty(){
        return documentNumber.getText().equals("")||price==0;
    }

    @Override
    protected void backButtonAction() {
        sectionPage.prepareShow();
    }

    @Override
    protected void actionButton() {
        if (isSomeEmpty()||personSelected==null){
            JOptionPane.showMessageDialog(mainBoard, mainBoard.getValue("message_bills"), titleAction, JOptionPane.INFORMATION_MESSAGE);
        }else {
            List<Item> items = new UPTCList<>();
            for (int i = 0; i < details.getRowCount(); i++) {
                Product product = mainBoard.getPresenter().getProduct((String) details.getValueAt(i,1));
                items.add(new Item((int) details.getValueAt(i,0),new Product(product.getBarcode(),product.getCIU(),
                        product.getDescription(),product.getPrice()), (int) details.getValueAt(i,4)));
            }
            if(mainBoard.getConfirmDialog("action_1","section_2_singular","confirm_action_1")){
                mainBoard.getPresenter().addBill(new Bill(new BillHead(billNum+"",billDate,personSelected),items,new BillFoot(price,iva,total)));
                billNum++;
                JOptionPane.showMessageDialog(mainBoard, mainBoard.getValue("section_2_singular","message_added"), titleAction, JOptionPane.INFORMATION_MESSAGE);
                mainBoard.setToShow(sectionPage);
            }
        }
    }

    @Override
    protected void createFieldsSection() {
        panelContent.setLayout(new BorderLayout());
        addHead();
        addDetails();
        prices = new JLabel();
        panelContent.add(prices,BorderLayout.SOUTH);
        addKeyListenerNavigable();
    }

    @Override
    public void begin(Bill value) {
    }
    private void addKeyListenerNavigable(){
        documentType.addKeyListener(Util.getKeyListenerNavigable(documentNumber));
        addItem.addKeyListener(Util.getKeyListenerNavigable(removeItem));
        removeItem.addKeyListener(Util.getKeyListenerNavigable(actionButton));
        actionButton.addKeyListener(Util.getKeyListenerNavigable(backButton));
        backButton.addKeyListener(Util.getKeyListenerNavigable(documentType));
    }

    private void addHead(){
        JPanel head = new JPanel(new GridLayout(4, 2, 0, 10));
        billNumCont = new JLabel();
        head.add(billNumCont);
        billDateCont = new JLabel();
        head.add(billDateCont);
        head.add(new JLabel(mainBoard.getValue("bill_field_1","generic_word_1")));
        documentType = new JComboBox<>(mainBoard.getValue("id_type_fields").split(";"));
        documentType.addItemListener(e -> actionChange());
        head.add(documentType);
        head.add(new JLabel(mainBoard.getValue("bill_field_1","generic_word_2")));
        documentNumber = new JTextField();
        documentNumber.addKeyListener(getKeyListenerCostumersInfo());
        head.add(documentNumber);
        head.add(new JLabel(mainBoard.getValue("bill_field_2")));
        namePerson = new JLabel();
        head.add(namePerson);
        panelContent.add(head,BorderLayout.NORTH);
    }
    private KeyListener getKeyListenerCostumersInfo(){
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c  = e.getKeyChar();
                if (!(Character.isDigit(c)||Character.isAlphabetic(c))){
                    e.consume();
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {

            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_BACK_SPACE||documentNumber.getText().length()>0){
                    actionChange();
                }
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    addItem.requestFocusInWindow();
                }
            }
        };
    }
    private void addDetails(){
        panelTable = new JPanel(new BorderLayout());
        panelTable.add(new JLabel(mainBoard.getValue("bill_field_3")),BorderLayout.NORTH);
        JPanel buttons = new JPanel();
        addItem = new JButton(mainBoard.getValue("bill_button_1"));
        addItem.addActionListener(e -> addItem());
        buttons.add(addItem);
        removeItem = new JButton(mainBoard.getValue("bill_button_2"));
        removeItem.addActionListener(e -> removeItem());
        buttons.add(removeItem);
        panelTable.add(buttons,BorderLayout.SOUTH);
        panelContent.add(panelTable,BorderLayout.CENTER);
    }

    @Override
    public void begin() {
        price=0;
        iva=0;
        total=0;
        clearContent();
        mainBoard.setToShow(this);
        documentType.requestFocusInWindow();
    }

    private void clearContent(){
        billDate=mainBoard.getPresenter().getDate();
        billNumCont.setText(mainBoard.getValue("bill_field_4")+ " " + billNum);
        billDateCont.setText(mainBoard.getValue("bill_field_5")+ " " + billDate);
        documentNumber.setText("");
        namePerson.setText(mainBoard.getValue("bill_field_6"));
        if (scroll!=null){
            panelTable.remove(scroll);
        }
        details= new DefaultTableModel(mainBoard.getValue("items_table_fields").split(";"), 0);
        JTable t = new JTable(details);
        t.setEnabled(false);
        t.getTableHeader().setReorderingAllowed(false);
        scroll = new JScrollPane(t);
        panelTable.add(scroll, BorderLayout.CENTER);
        prices.setText(mainBoard.getValue("bill_field_7"));
    }
    private void actionChange(){
        personSelected = mainBoard.getPresenter().getPerson((String) documentType.getSelectedItem(),documentNumber.getText());
        if (personSelected!=null){
            namePerson.setText(personSelected.getName() + ", " + personSelected.getLastName());
        }else {
            namePerson.setText(mainBoard.getValue("bill_field_6"));
        }
    }
    private void addItem(){
        EditorTable editorTable = new EditorTable(mainBoard,details,true);
        editorTable.setVisible(true);
        updatePrices();
    }

    private void updatePrices() {
        if (details.getRowCount()!=0){
            int sum =0;
            for (int i = 0; i < details.getRowCount(); i++) {
                sum+= (int) details.getValueAt(i,3) * (int) details.getValueAt(i,4);
            }
            price=sum;
            iva = mainBoard.getPresenter().calculateIVA(price);
            total= price+iva;
            if (price!=0){
                prices.setText(mainBoard.getValue("bill_field_8")+" " + price +mainBoard.getValue("bill_field_9")
                        +" "+ iva + mainBoard.getValue("bill_field_10")+" "+ total);
            }else {
                prices.setText(mainBoard.getValue("bill_field_7"));
            }
        }
    }

    private void removeItem(){
        EditorTable editorTable = new EditorTable(mainBoard,details,false);
        editorTable.setVisible(true);
        updatePrices();
    }
}
