package co.edu.uptc.view;

import co.edu.uptc.config.GlobalConfig;
import co.edu.uptc.pojo.Product;
import co.edu.uptc.util.Util;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EditorTable extends JDialog {
    private final MainBoard mainBoard;
    private final DefaultTableModel table;
    private JTextField ciu;
    private JTextField amount;
    private JTextField itemRemove;
    private JLabel labelCiu;
    private JLabel labelAmount;
    private JLabel labelRemove;
    private JButton cancel;
    private JButton ok;

    public EditorTable(MainBoard mainBoard, DefaultTableModel table, boolean isAdd) {
        super(mainBoard, true);
        this.mainBoard = mainBoard;
        this.table = table;
        setTitle(mainBoard.getValue("bill_editor"));
        setSize(400,180);
        setLocationRelativeTo(mainBoard);
        setResizable(false);
        if (isAdd){
            addComponents();
        }else {
            removeComponents();
        }
        putMode(isAdd);
    }
    private void putMode(boolean isAdd){
        if (mainBoard.isDark()){
            this.getContentPane().setBackground(GlobalConfig.DIALOG_COLOR_DARK);
            setFontColor(GlobalConfig.FONT_COLOR_DARK,isAdd);
        }else {
            this.getContentPane().setBackground(GlobalConfig.DIALOG_COLOR_LIGHT);
            setFontColor(GlobalConfig.FONT_COLOR_LIGHT,isAdd);
        }
    }
    private void setFontColor(Color c, boolean isAdd){
        if (isAdd){
            labelAmount.setForeground(c);
            labelCiu.setForeground(c);
        }else {
            labelRemove.setForeground(c);
        }
    }

    private void removeComponents() {
        this.setLayout(new GridLayout(2,2));
        labelRemove = new JLabel(mainBoard.getValue("bill_remove"));
        this.add(labelRemove);
        itemRemove = new JTextField();
        itemRemove.addKeyListener(Util.getListenerCheckedNumbers());
        this.add(itemRemove);
        ok = new JButton(mainBoard.getValue("generic_word_4"));
        ok.addActionListener(e -> removeItem());
        this.add(ok);
        addButtonRemove();
        addKeyNavigableRemoveItems();
    }
    private void addKeyNavigableRemoveItems(){
        itemRemove.addKeyListener(Util.getKeyListenerNavigable(ok));
        ok.addKeyListener(Util.getKeyListenerNavigable(cancel));
        cancel.addKeyListener(Util.getKeyListenerNavigable(itemRemove));
        itemRemove.requestFocusInWindow();
    }

    private void removeItem() {
        if (itemRemove.getText().equals("")||itemRemove.getText().equals("0")||(table.getRowCount()+1)<Integer.parseInt(itemRemove.getText())){
            JOptionPane.showMessageDialog(mainBoard, mainBoard.getValue("bill_editor_message"),
                    mainBoard.getValue("bill_editor"), JOptionPane.INFORMATION_MESSAGE);
        }else {
            table.removeRow(Integer.parseInt(itemRemove.getText()) - 1);
            updateIndexes();
            this.dispose();
        }
    }

    private void addComponents() {
        this.setLayout(new GridLayout(3,2));
        labelCiu = new JLabel(mainBoard.getValue("section_1_singular","product_field_2"));
        this.add(labelCiu);
        ciu = new JTextField();
        ciu.addKeyListener(Util.getListenerCheckedNumbers());
        this.add(ciu);
        labelAmount = new JLabel(mainBoard.getValue("section_1_singular","bill_field_12"));
        this.add(labelAmount);
        amount = new JTextField();
        amount.addKeyListener(Util.getListenerCheckedNumbers());
        this.add(amount);
        ok = new JButton(mainBoard.getValue("generic_word_4"));
        ok.addActionListener(e -> addItem());
        this.add(ok);
        addButtonRemove();
        addKeyNavigableAddItems();
    }
    private void addKeyNavigableAddItems(){
        ciu.addKeyListener(Util.getKeyListenerNavigable(amount));
        amount.addKeyListener(Util.getKeyListenerNavigable(ok));
        ok.addKeyListener(Util.getKeyListenerNavigable(cancel));
        cancel.addKeyListener(Util.getKeyListenerNavigable(ciu));
        ciu.requestFocusInWindow();
    }

    private void addItem() {
        Product pr =mainBoard.getPresenter().getProduct(ciu.getText());
        if (ciu.getText().equals("")||amount.getText().equals("")||pr==null){
            JOptionPane.showMessageDialog(mainBoard, mainBoard.getValue("bill_editor_message"),
                    mainBoard.getValue("bill_editor"), JOptionPane.INFORMATION_MESSAGE);
        }else {
            table.addRow(new Object[]{table.getRowCount() + 1, ciu.getText(), pr.getDescription(), pr.getPrice(), Integer.parseInt(amount.getText()) });
            this.dispose();
        }
    }

    private void addButtonRemove(){
        cancel = new JButton(mainBoard.getValue("generic_word_5"));
        cancel.addActionListener(e -> this.dispose());
        this.add(cancel);
    }
    private void updateIndexes() {
        for (int i = 0; i < table.getRowCount(); i++) {
            table.setValueAt(i + 1, i, 0);
        }
    }
}
