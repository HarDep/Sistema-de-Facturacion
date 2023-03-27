package co.edu.uptc.view.products;

import co.edu.uptc.pojo.Product;
import co.edu.uptc.util.Util;
import co.edu.uptc.view.ActionPage;
import co.edu.uptc.view.SectionPage;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;
import java.awt.*;

public class AddProductPage extends ActionPage<Product> {
    private JTextField barcode;
    private JTextField ciu;
    private JTextArea description;
    private JTextField price;
    public AddProductPage(MainBoard mainBoard, SectionPage<Product> sectionPage) {
        super(mainBoard, sectionPage, "action_1","section_1_singular");
    }

    @Override
    protected void backButtonAction() {
        sectionPage.prepareShow();
    }

    @Override
    protected void actionButton() {
        if (mainBoard.getPresenter().isSomeProduct(barcode.getText(),ciu.getText())||isSomeEmpty()){
            JOptionPane.showMessageDialog(mainBoard, mainBoard.getValue("message_already_prod"),mainBoard.getValue("action_1","section_1_singular"), JOptionPane.INFORMATION_MESSAGE);
        }else {
            if(mainBoard.getConfirmDialog("action_1","section_1_singular","confirm_action_1")){
                mainBoard.getPresenter().addProduct(new Product(barcode.getText(),ciu.getText(),description.getText(), Integer.parseInt(price.getText())));
                JOptionPane.showMessageDialog(mainBoard, mainBoard.getValue("section_1_singular","message_added"),mainBoard.getValue("action_1","section_1_singular"), JOptionPane.INFORMATION_MESSAGE);
                mainBoard.setToShow(sectionPage);
            }
        }
    }

    @Override
    protected void createFieldsSection() {
        panelContent = new JPanel(new GridLayout(4, 2, 0, 10));
        panelContent.add(new JLabel(mainBoard.getValue("product_field_1")));
        barcode = new JTextField();
        barcode.addKeyListener(Util.getListenerChecked());
        panelContent.add(barcode);
        panelContent.add(new JLabel(mainBoard.getValue("product_field_2")));
        ciu = new JTextField();
        ciu.addKeyListener(Util.getListenerChecked());
        panelContent.add(ciu);
        panelContent.add(new JLabel(mainBoard.getValue("product_field_3")));
        description = new JTextArea();
        panelContent.add(description);
        panelContent.add(new JLabel(mainBoard.getValue("product_field_4")));
        price = new JTextField();
        price.addKeyListener(Util.getListenerCheckedNumbers());
        panelContent.add(price);
        addKeyNavigable();
    }
    private void addKeyNavigable(){
        barcode.addKeyListener(Util.getKeyListenerNavigable(ciu));
        ciu.addKeyListener(Util.getKeyListenerNavigable(description));
        description.addKeyListener(Util.getKeyListenerNavigable(price));
        price.addKeyListener(Util.getKeyListenerNavigable(actionButton));
        actionButton.addKeyListener(Util.getKeyListenerNavigable(backButton));
        backButton.addKeyListener(Util.getKeyListenerNavigable(barcode));
    }

    @Override
    public void begin(Product value) {
    }

    @Override
    public void begin() {
        barcode.setText("");
        ciu.setText("");
        description.setText("");
        price.setText("");
        mainBoard.setToShow(this);
        barcode.requestFocusInWindow();
    }

    private boolean isSomeEmpty(){
        return barcode.getText().equals("")||description.getText().equals("")||ciu.getText().equals("")||price.getText().equals("");
    }
}
