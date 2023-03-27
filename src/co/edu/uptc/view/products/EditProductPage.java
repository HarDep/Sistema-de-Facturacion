package co.edu.uptc.view.products;

import co.edu.uptc.pojo.Product;
import co.edu.uptc.util.Util;
import co.edu.uptc.view.ActionPage;
import co.edu.uptc.view.SectionPage;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;
import java.awt.*;

public class EditProductPage extends ActionPage<Product> {
    private Product productSelected;
    private JLabel ciu;
    private JTextField barcode;
    private JTextArea description;
    private JTextField price;
    public EditProductPage(MainBoard mainBoard, SectionPage<Product> sectionPage) {
        super(mainBoard,sectionPage,"action_3","section_1_singular");
    }

    @Override
    protected void backButtonAction() {
        sectionPage.beginEditAction();
    }

    private boolean isSomeEmpty(){
        return barcode.getText().equals("")||description.getText().equals("")||price.getText().equals("");
    }
    @Override
    protected void actionButton() {
        if (mainBoard.getPresenter().isSomeProduct(productSelected, barcode.getText())||isSomeEmpty()){
            JOptionPane.showMessageDialog(mainBoard, mainBoard.getValue("message_already_prod"),mainBoard.getValue("action_3","section_1_singular"), JOptionPane.INFORMATION_MESSAGE);
        }else {
            if(mainBoard.getConfirmDialog("action_3","section_1_singular","confirm_action_3")){
                mainBoard.getPresenter().updateProduct(productSelected,barcode.getText(),description.getText(),Integer.parseInt(price.getText()));
                JOptionPane.showMessageDialog(mainBoard, mainBoard.getValue("section_1_singular","message_edited"),mainBoard.getValue("action_3","section_1_singular"), JOptionPane.INFORMATION_MESSAGE);
                sectionPage.beginEditAction();
            }
        }
    }

    @Override
    protected void createFieldsSection() {
        panelContent.setLayout(new BorderLayout());
        JPanel infoNoEdit = new JPanel();
        ciu = new JLabel();
        infoNoEdit.add(ciu);
        panelContent.add(infoNoEdit,BorderLayout.NORTH);
        JPanel infoEdit = new JPanel(new GridLayout(3, 2, 0, 10));
        infoEdit.add(new JLabel(mainBoard.getValue("product_field_1")));
        barcode = new JTextField();
        barcode.addKeyListener(Util.getListenerChecked());
        infoEdit.add(barcode);
        infoEdit.add(new JLabel(mainBoard.getValue("product_field_3")));
        description = new JTextArea();
        infoEdit.add(description);
        infoEdit.add(new JLabel(mainBoard.getValue("product_field_4")));
        price = new JTextField();
        price.addKeyListener(Util.getListenerCheckedNumbers());
        infoEdit.add(price);
        panelContent.add(infoEdit,BorderLayout.CENTER);
        addKeyNavigable();
    }
    private void addKeyNavigable(){
        barcode.addKeyListener(Util.getKeyListenerNavigable(description));
        description.addKeyListener(Util.getKeyListenerNavigable(price));
        price.addKeyListener(Util.getKeyListenerNavigable(actionButton));
        actionButton.addKeyListener(Util.getKeyListenerNavigable(backButton));
        backButton.addKeyListener(Util.getKeyListenerNavigable(barcode));
    }

    @Override
    public void begin(Product value) {
        productSelected=value;
        prepareContent();
        mainBoard.setToShow(this);
        barcode.requestFocusInWindow();
    }

    @Override
    public void begin() {
    }

    private void prepareContent() {
        ciu.setText(mainBoard.getValue("product_field_2")+" "+ productSelected.getCIU());
        barcode.setText(productSelected.getBarcode());
        description.setText(productSelected.getDescription());
        price.setText(productSelected.getPrice()+ "");
    }
}
