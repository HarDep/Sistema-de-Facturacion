package co.edu.uptc.view.products;

import co.edu.uptc.pojo.Product;
import co.edu.uptc.view.SearchPage;
import co.edu.uptc.view.SectionPage;
import co.edu.uptc.view.ShowDialog;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;

public class ProductsPage extends SectionPage<Product> {
    public ProductsPage(MainBoard mainBoard) {
        super(mainBoard, "section_1","section_1_icon_dir");
    }

    @Override
    protected void createActionPages() {
        this.addPage = new AddProductPage(mainBoard,this);
        keySingular =mainBoard.getValue("section_1_singular");
        this.deleteSearchPage = new SearchPage<>(mainBoard,this,getTitleWithAction("action_2"),mainBoard.getValue("product_field_2"),true);
        deleteSearchPage.addButtonBack(mainBoard.getButtonBackTo(e -> this.prepareShow()));
        mainBoard.add(deleteSearchPage);
        this.deletePage = new DeleteProductPage(mainBoard,this);
        this.editSearchPage = new SearchPage<>(mainBoard,this,getTitleWithAction("action_3"),mainBoard.getValue("product_field_2"),false);
        editSearchPage.addButtonBack(mainBoard.getButtonBackTo(e -> this.prepareShow()));
        mainBoard.add(editSearchPage);
        this.editPage = new EditProductPage(mainBoard,this);
    }

    @Override
    protected void showList() {
        ShowDialog<Product> show = new ShowDialog<>(mainBoard,keyTitle,mainBoard.getPresenter().getListProducts(),1);
        show.setVisible(true);
    }

    @Override
    protected void searchDeleteAction(String type, String search) {
        Product product = mainBoard.getPresenter().getProduct(search);
        if (product!=null){
            deletePage.begin(product);
        }else {
            JOptionPane.showMessageDialog(mainBoard,mainBoard.getValue("message_not_found"),mainBoard.getValue("action_2","section_1_singular"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    protected void searchEditAction(String type, String search) {
        Product product = mainBoard.getPresenter().getProduct(search);
        if (product!=null){
            editPage.begin(product);
        }else {
            JOptionPane.showMessageDialog(mainBoard,mainBoard.getValue("message_not_found"),mainBoard.getValue("action_3","section_1_singular"), JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
