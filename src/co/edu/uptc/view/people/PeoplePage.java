package co.edu.uptc.view.people;

import co.edu.uptc.pojo.Person;
import co.edu.uptc.util.Util;
import co.edu.uptc.view.SearchPage;
import co.edu.uptc.view.SectionPage;
import co.edu.uptc.view.ShowDialog;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;

public class PeoplePage extends SectionPage<Person> {
    public PeoplePage(MainBoard mainBoard) {
        super(mainBoard, "section_3","section_3_icon_dir");
    }

    @Override
    protected void createActionPages() {
        addButtonDeletePeopleWithoutBill();
        this.addPage = new AddPersonPage(mainBoard,this);
        keySingular =mainBoard.getValue("section_3_singular");
        this.deleteSearchPage = new SearchPage<>(mainBoard,this,getTitleWithAction("action_2"),mainBoard.getValue("person_field_1","generic_word_2"),true);
        deleteSearchPage.addButtonBack(mainBoard.getButtonBackTo(e -> this.prepareShow()));
        deleteSearchPage.addTypeComboBox(new JComboBox<>(mainBoard.getValue("id_type_fields").split(";")),mainBoard.getValue("person_field_6"));
        mainBoard.add(deleteSearchPage);
        this.deletePage = new DeletePersonPage(mainBoard,this);
        this.editSearchPage = new SearchPage<>(mainBoard,this,getTitleWithAction("action_3"),mainBoard.getValue("person_field_1","generic_word_2"),false);
        editSearchPage.addButtonBack(mainBoard.getButtonBackTo(e -> this.prepareShow()));
        editSearchPage.addTypeComboBox(new JComboBox<>(mainBoard.getValue("id_type_fields").split(";")),mainBoard.getValue("person_field_6"));
        mainBoard.add(editSearchPage);
        this.editPage = new EditPersonPage(mainBoard,this);
    }
    private void addButtonDeletePeopleWithoutBill(){
        JButton delete = new JButton(mainBoard.getValue("evaluation_button_principal"));
        delete.addActionListener(e -> {
            DeletePeople deletePeople = new DeletePeople(mainBoard);
            deletePeople.setVisible(true);
        });
        buttonsPanel.add(delete);
        showButton.removeKeyListener(Util.getKeyListenerNavigable(backButton));
        showButton.addKeyListener(Util.getKeyListenerNavigable(delete));
        delete.addKeyListener(Util.getKeyListenerNavigable(backButton));
    }
    @Override
    protected void showList() {
        ShowDialog<Person> show = new ShowDialog<>(mainBoard,keyTitle,mainBoard.getPresenter().getListPeople(),2);
        show.setVisible(true);
    }

    @Override
    protected void searchDeleteAction(String type, String search) {
        Person person = mainBoard.getPresenter().getPerson(type,search);
        if (person!=null){
            deletePage.begin(person);
        }else {
            JOptionPane.showMessageDialog(mainBoard,mainBoard.getValue("message_not_found"),mainBoard.getValue("action_2","section_3_singular"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    protected void searchEditAction(String type, String search) {
        Person person = mainBoard.getPresenter().getPerson(type,search);
        if (person!=null){
            editPage.begin(person);
        }else {
            JOptionPane.showMessageDialog(mainBoard,mainBoard.getValue("message_not_found"),mainBoard.getValue("action_3","section_3_singular"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
