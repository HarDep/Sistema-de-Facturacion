package co.edu.uptc.view.people;

import co.edu.uptc.pojo.Person;
import co.edu.uptc.util.Util;
import co.edu.uptc.view.ActionPage;
import co.edu.uptc.view.SectionPage;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;

public class DeletePersonPage extends ActionPage<Person> {
    private JTextArea labelInfo;
    private Person personSelected;

    public DeletePersonPage(MainBoard mainBoard, SectionPage<Person> sectionPage) {
        super(mainBoard,sectionPage,"action_2","section_3_singular");
    }

    @Override
    protected void backButtonAction() {
        sectionPage.beginDeleteAction();
    }

    @Override
    protected void actionButton() {
        if (mainBoard.getPresenter().isThePersonReferencedInABill(personSelected)){
            JOptionPane.showMessageDialog(mainBoard, mainBoard.getValue("message_referenced_bill"), mainBoard.getValue("action_2","section_3_singular"), JOptionPane.INFORMATION_MESSAGE);
        }else {
            if(mainBoard.getConfirmDialog("action_2","section_3_singular","confirm_action_2")){
                mainBoard.getPresenter().deletePerson(personSelected);
                JOptionPane.showMessageDialog(mainBoard, mainBoard.getValue("section_3_singular","message_deleted"), mainBoard.getValue("action_2","section_3_singular"), JOptionPane.INFORMATION_MESSAGE);
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
    public void begin(Person value) {
        personSelected= value;
        labelInfo.setText("\r\n"+"\r\n"
                + "          "+ mainBoard.getValue("person_field_1","generic_word_1")+"                " + personSelected.getDocumentTye()+"\r\n"+"\r\n"
                + "          "+mainBoard.getValue("person_field_1","generic_word_2")+"              " + personSelected.getDocumentNumber()+"\r\n"+"\r\n"
                + "          "+mainBoard.getValue("person_field_2")+"                         " + personSelected.getName()+"\r\n"+"\r\n"
                + "          "+mainBoard.getValue("person_field_3")+"                    " + personSelected.getLastName() +"\r\n"+"\r\n"
                + "          "+mainBoard.getValue("person_field_4")+"          " + personSelected.getResDirection()+"\r\n"+"\r\n"
                + "          "+mainBoard.getValue("person_field_5")+"               " + personSelected.getCity() +"\r\n"+"\r\n");
        labelInfo.setEditable(false);
        mainBoard.setToShow(this);
        actionButton.requestFocusInWindow();
    }

    @Override
    public void begin() {
    }
}
