package co.edu.uptc.presenter;

import co.edu.uptc.contract.MainContract;
import co.edu.uptc.model.Model;
import co.edu.uptc.view.mainBoard.MainBoard;

public class BillingSystem {
    private final MainContract.Model model;
    private final MainContract.Presenter presenter;
    private final MainContract.View mainBoard;
    public BillingSystem() throws Exception {
        model = new Model();
        presenter = new Presenter();
        mainBoard = new MainBoard();
    }
    public void start(){
        model.setPresenter(presenter);
        presenter.setModel(model);
        presenter.setView(mainBoard);
        mainBoard.setPresenter(presenter);
        model.start();
    }
}
