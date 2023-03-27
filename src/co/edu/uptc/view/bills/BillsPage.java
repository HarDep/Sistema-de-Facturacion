package co.edu.uptc.view.bills;

import co.edu.uptc.pojo.Bill;
import co.edu.uptc.view.SearchPage;
import co.edu.uptc.view.SectionPage;
import co.edu.uptc.view.ShowDialog;
import co.edu.uptc.view.mainBoard.MainBoard;

public class BillsPage extends SectionPage<Bill> {
    private ResultsBillsPage resultsDeletePage;
    private ResultsBillsPage resultsEditPage;
    public BillsPage(MainBoard mainBoard) {
        super(mainBoard,"section_2","section_2_icon_dir");
    }

    @Override
    protected void createActionPages() {
        this.addPage = new AddBillPage(this.mainBoard, this);
        keySingular =mainBoard.getValue("section_2_singular");
        this.deleteSearchPage = new SearchPage<>(mainBoard,this,getTitleWithAction("action_2"),
                mainBoard.getValue("section_2_singular","generic_word_2"),true);
        deleteSearchPage.addButtonBack(mainBoard.getButtonBackTo(e -> this.prepareShow()));
        mainBoard.add(deleteSearchPage);
        resultsDeletePage = new ResultsBillsPage(getTitleWithAction("action_2"),mainBoard,this,true);
        resultsDeletePage.addBackButton(mainBoard.getButtonBackTo(e -> beginDeleteAction()));
        mainBoard.add(resultsDeletePage);
        this.deletePage = new DeleteBillPage(this.mainBoard,this, resultsDeletePage);
        this.editSearchPage = new SearchPage<>(mainBoard,this,getTitleWithAction("action_3"),
                mainBoard.getValue("section_2_singular","generic_word_2"),false);
        editSearchPage.addButtonBack(mainBoard.getButtonBackTo(e -> this.prepareShow()));
        mainBoard.add(editSearchPage);
        resultsEditPage = new ResultsBillsPage(getTitleWithAction("action_3"),mainBoard,this,false);
        resultsEditPage.addBackButton(mainBoard.getButtonBackTo(e -> beginEditAction()));
        mainBoard.add(resultsEditPage);
        this.editPage = new EditBillPage(mainBoard,this,resultsEditPage);
    }

    @Override
    protected void showList() {
        ShowDialog<Bill> show=new ShowDialog<>(mainBoard,keyTitle,mainBoard.getPresenter().getList(),3);
        show.setVisible(true);
    }

    protected void beginResult(Bill billSelected, boolean isDeletePage){
        if (isDeletePage){
            deletePage.begin(billSelected);
        }else {
            editPage.begin(billSelected);
        }
    }

    @Override
    protected void searchDeleteAction(String type, String search) {
        resultsDeletePage.beginResults(search);
    }

    @Override
    protected void searchEditAction(String type, String search) {
        resultsEditPage.beginResults(search);
    }
}
