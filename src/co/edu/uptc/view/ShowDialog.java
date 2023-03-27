package co.edu.uptc.view;

import co.edu.uptc.config.GlobalConfig;
import co.edu.uptc.pojo.Bill;
import co.edu.uptc.pojo.Person;
import co.edu.uptc.pojo.Product;
import co.edu.uptc.util.Util;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShowDialog<T> extends JDialog {
    private final MainBoard mainBoard;
    private JPanel panelContent;
    private JPanel panelButtons;
    private JPanel title;
    private final List<T> list;
    private final int type;
    public ShowDialog(MainBoard mainBoard, String keyTitle, java.util.List<T> list, int type) {
        super(mainBoard,true);
        this.mainBoard=mainBoard;
        this.list=list;
        this.type=type;
        setTitle(mainBoard.getValue(keyTitle));
        setSize(1000,300);
        setLocationRelativeTo(mainBoard);
        setResizable(false);
        createComponents(keyTitle);
        putMode();
    }

    private void putMode() {
        if (mainBoard.isDark()){
            title.setBackground(GlobalConfig.DIALOG_COLOR_DARK);
            title.getComponents()[0].setForeground(GlobalConfig.FONT_COLOR_DARK);
            panelButtons.setBackground(GlobalConfig.DIALOG_COLOR_DARK);
            panelContent.setBackground(GlobalConfig.DIALOG_COLOR_DARK);
        }else {
            title.setBackground(GlobalConfig.DIALOG_COLOR_LIGHT);
            title.getComponents()[0].setForeground(GlobalConfig.FONT_COLOR_LIGHT);
            panelButtons.setBackground(GlobalConfig.DIALOG_COLOR_LIGHT);
            panelContent.setBackground(GlobalConfig.DIALOG_COLOR_LIGHT);
        }
    }

    private void createComponents(String key) {
        this.setLayout(new BorderLayout());
        title=mainBoard.getTitleSection(key);
        panelContent = new JPanel(new BorderLayout());
        panelButtons=new JPanel();
        putList();
        JButton ok = new JButton(mainBoard.getValue("generic_word_4"));
        ok.addActionListener(e -> this.dispose());
        ok.addKeyListener(Util.getKeyListenerNavigable(ok));
        panelButtons.add(ok);
        this.add(title,BorderLayout.NORTH);
        this.add(panelContent,BorderLayout.CENTER);
        this.add(panelButtons,BorderLayout.SOUTH);
        ok.requestFocusInWindow();
    }

    private void putList() {
        switch (type) {
            case 1 -> putProducts();
            case 2 -> putPeople();
            case 3 -> putBills();
            case 4 -> putPeopleWithBills();
        }
    }

    private void putBills() {
        DefaultTableModel tableModel = new DefaultTableModel(mainBoard.getValue("items_bill").split(";"),0);
        for (T bi :list) {
            Bill bill =(Bill) bi;
            tableModel.addRow(new Object[]{bill.getBillHead().getBillNumber(),bill.getBillHead().getBillDate(),
                    bill.getBillHead().getCostumer().getDocumentTye()+ ", "+bill.getBillHead().getCostumer().getDocumentNumber(),
                    bill.getBillHead().getCostumer().getName()+ ", "+bill.getBillHead().getCostumer().getLastName(),
                    bill.getBillFoot().getTotalProducts(), bill.getBillFoot().getTotal()});
        }
        JTable t = new JTable(tableModel);
        t.setEnabled(false);
        t.getTableHeader().setReorderingAllowed(false);
        JScrollPane details = new JScrollPane(t);
        panelContent.add(details,BorderLayout.CENTER);
    }

    private void putPeople() {
        DefaultTableModel tableModel = new DefaultTableModel(mainBoard.getValue("items_person").split(";"),0);
        for (T person :list) {
            Person prod =(Person) person;
            tableModel.addRow(new Object[]{prod.getDocumentTye(),prod.getDocumentNumber(),prod.getName(),prod.getLastName()
                    ,prod.getResDirection(),prod.getCity()});
        }
        JTable t = new JTable(tableModel);
        t.setEnabled(false);
        t.getTableHeader().setReorderingAllowed(false);
        JScrollPane details = new JScrollPane(t);
        panelContent.add(details,BorderLayout.CENTER);
    }

    private void putProducts() {
        DefaultTableModel tableModel = new DefaultTableModel(mainBoard.getValue("items_product").split(";"),0);
        for (T product :list) {
            Product prod =(Product)product;
            tableModel.addRow(new Object[]{prod.getCIU(),prod.getBarcode(),prod.getDescription(),prod.getPrice()});
        }
        JTable t = new JTable(tableModel);
        t.setEnabled(false);
        t.getTableHeader().setReorderingAllowed(false);
        JScrollPane details = new JScrollPane(t);
        panelContent.add(details,BorderLayout.CENTER);
    }
    private void putPeopleWithBills(){
        DefaultTableModel tableModel = new DefaultTableModel(mainBoard.getValue("evaluation_fields_table").split(";"),0);
        for (T person :list) {
            Person prod =(Person) person;
            tableModel.addRow(new Object[]{prod.getDocumentTye(),prod.getDocumentNumber(),prod.getName(),prod.getLastName()
                    , mainBoard.getPresenter().getBillsForAPerson(prod)});
        }
        JTable t = new JTable(tableModel);
        t.setEnabled(false);
        t.getTableHeader().setReorderingAllowed(false);
        JScrollPane details = new JScrollPane(t);
        panelContent.add(details,BorderLayout.CENTER);
    }
}
