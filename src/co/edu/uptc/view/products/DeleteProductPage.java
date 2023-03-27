package co.edu.uptc.view.products;

import co.edu.uptc.pojo.Product;
import co.edu.uptc.util.Util;
import co.edu.uptc.view.ActionPage;
import co.edu.uptc.view.SectionPage;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;

public class DeleteProductPage extends ActionPage<Product> {
    private Product productSelected;
    private JTextArea labelInfo;

    public DeleteProductPage(MainBoard mainBoard, SectionPage<Product> sectionPage) {
        super(mainBoard,sectionPage,"action_2","section_1_singular");
    }

    @Override
    protected void backButtonAction() {
        sectionPage.beginDeleteAction();
    }

    @Override
    protected void actionButton() {
        if (mainBoard.getPresenter().isTheProductReferencedInABill(productSelected)){
            JOptionPane.showMessageDialog(mainBoard, mainBoard.getValue("message_referenced_bill"), mainBoard.getValue("action_2","section_1_singular"), JOptionPane.INFORMATION_MESSAGE);
        }else {
            if(mainBoard.getConfirmDialog("action_2","section_1_singular","confirm_action_2")){
                mainBoard.getPresenter().deleteProduct(productSelected);
                JOptionPane.showMessageDialog(mainBoard, mainBoard.getValue("section_1_singular","message_deleted"), mainBoard.getValue("action_2","section_1_singular"), JOptionPane.INFORMATION_MESSAGE);
            }
        }
        sectionPage.beginDeleteAction();
    }

    @Override
    protected void createFieldsSection() {
        labelInfo = new JTextArea();
        panelContent.add(labelInfo);
        addKeyNavigable();
    }
    private void addKeyNavigable(){
        actionButton.addKeyListener(Util.getKeyListenerNavigable(backButton));
        backButton.addKeyListener(Util.getKeyListenerNavigable(actionButton));
    }

    @Override
    public void begin(Product value) {
        productSelected = value;
        labelInfo.setText("\r\n"+"\r\n"
                + "          "+mainBoard.getValue("product_field_1")+"          " + productSelected.getBarcode()+"\r\n"+"\r\n"
                + "          "+mainBoard.getValue("product_field_2")+"              " + productSelected.getCIU()+"\r\n"+"\r\n"
                + "          "+mainBoard.getValue("product_field_3")+"      " + productSelected.getDescription()+"\r\n"+"\r\n"
                + "          "+mainBoard.getValue("product_field_4")+"            " + productSelected.getPrice()+"\r\n"+"\r\n");
        labelInfo.setEditable(false);
        mainBoard.setToShow(this);
        actionButton.requestFocusInWindow();
    }

    @Override
    public void begin() {
    }
}
