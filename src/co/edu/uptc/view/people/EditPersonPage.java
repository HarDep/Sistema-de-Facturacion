package co.edu.uptc.view.people;

import co.edu.uptc.pojo.Person;
import co.edu.uptc.util.Util;
import co.edu.uptc.view.ActionPage;
import co.edu.uptc.view.SectionPage;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;
import java.awt.*;

public class EditPersonPage extends ActionPage<Person> {
    private Person personSelected;
    private JPanel infoNoEdit;
    private JLabel typeLa;
    private JComboBox<String> type;
    private JLabel docNumLa;
    private JTextField docNum;
    private JLabel textNoEd;
    private JTextField name;
    private JTextField lastName;
    private JTextField resDir;
    private JTextField city;
    private boolean isEditable;
    public EditPersonPage(MainBoard mainBoard, SectionPage<Person> sectionPage) {
        super(mainBoard,sectionPage,"action_3","section_3_singular");
    }

    @Override
    protected void backButtonAction() {
        sectionPage.beginEditAction();
    }

    @Override
    protected void actionButton() {
        if (isSomeEmpty()||(isEditable&&mainBoard.getPresenter().isThisPerson((String) type.getSelectedItem(),docNum.getText(),personSelected))){
            JOptionPane.showMessageDialog(mainBoard,mainBoard.getValue("message_already_per"),mainBoard.getValue("action_3","section_3_singular"), JOptionPane.INFORMATION_MESSAGE);
        }else {
            if(mainBoard.getConfirmDialog("action_3","section_3_singular","confirm_action_3")){
                if (isEditable){
                    mainBoard.getPresenter().updatePerson(personSelected,(String) type.getSelectedItem(),
                            docNum.getText(),name.getText(),lastName.getText(),resDir.getText(),city.getText());
                }else {
                    mainBoard.getPresenter().updatePerson(personSelected,personSelected.getDocumentTye(),
                            personSelected.getDocumentNumber(),name.getText(),lastName.getText(),resDir.getText(),city.getText());
                }
                JOptionPane.showMessageDialog(mainBoard, mainBoard.getValue("section_3_singular","message_edited"), mainBoard.getValue("action_3","section_3_singular"), JOptionPane.INFORMATION_MESSAGE);
                sectionPage.beginEditAction();
            }
        }
    }
    private boolean isSomeEmpty(){
        if (isEditable){
            return docNum.getText().equals("")||name.getText().equals("")||lastName.getText().equals("")||resDir.getText().equals("")||city.getText().equals("");
        }
        return name.getText().equals("")||lastName.getText().equals("")||resDir.getText().equals("")||city.getText().equals("");
    }

    @Override
    protected void createFieldsSection() {
        panelContent.setLayout(new BorderLayout());
        infoNoEdit = new JPanel();
        textNoEd = new JLabel();
        infoNoEdit.add(textNoEd);
        panelContent.add(infoNoEdit,BorderLayout.NORTH);
        JPanel infoEdit = new JPanel(new GridLayout(6, 2, 0, 10));
        typeLa = new JLabel(mainBoard.getValue("person_field_1","generic_word_1"));
        infoEdit.add(typeLa);
        type = new JComboBox<>(mainBoard.getValue("id_type_fields").split(";"));
        infoEdit.add(type);
        docNumLa = new JLabel(mainBoard.getValue("person_field_1","generic_word_2"));
        infoEdit.add(docNumLa);
        docNum = new JTextField();
        docNum.addKeyListener(Util.getListenerChecked());
        infoEdit.add(docNum);
        infoEdit.add(new JLabel(mainBoard.getValue("person_field_2")));
        name = new JTextField();
        infoEdit.add(name);
        infoEdit.add(new JLabel(mainBoard.getValue("person_field_3")));
        lastName = new JTextField();
        infoEdit.add(lastName);
        infoEdit.add(new JLabel(mainBoard.getValue("person_field_4")));
        resDir = new JTextField();
        infoEdit.add(resDir);
        infoEdit.add(new JLabel(mainBoard.getValue("person_field_5")));
        city = new JTextField();
        infoEdit.add(city);
        panelContent.add(infoEdit,BorderLayout.CENTER);
        addKeyNavigable();
    }
    private void addKeyNavigable(){
        name.addKeyListener(Util.getKeyListenerNavigable(lastName));
        lastName.addKeyListener(Util.getKeyListenerNavigable(resDir));
        resDir.addKeyListener(Util.getKeyListenerNavigable(city));
        city.addKeyListener(Util.getKeyListenerNavigable(actionButton));
        actionButton.addKeyListener(Util.getKeyListenerNavigable(backButton));
    }

    @Override
    public void begin(Person value) {
        personSelected = value;
        prepareInfo();
        mainBoard.setToShow(this);
        if (isEditable){
            type.addKeyListener(Util.getKeyListenerNavigable(docNum));
            docNum.addKeyListener(Util.getKeyListenerNavigable(name));
            backButton.addKeyListener(Util.getKeyListenerNavigable(type));
            type.requestFocusInWindow();
        }else {
            backButton.addKeyListener(Util.getKeyListenerNavigable(name));
            name.requestFocusInWindow();
        }
    }

    @Override
    public void begin() {
    }

    private void prepareInfo() {
        if (mainBoard.getPresenter().isThePersonReferencedInABill(personSelected)){
            isEditable = false;
            infoNoEdit.setVisible(true);
            textNoEd.setText("\r\n"
                    + "          "+mainBoard.getValue("person_field_1","generic_word_1")+"                " + personSelected.getDocumentTye()+"\r\n"
                    + "          "+mainBoard.getValue("person_field_1","generic_word_2")+"              " + personSelected.getDocumentNumber()+"\r\n");
            typeLa.setVisible(false);
            type.setVisible(false);
            docNumLa.setVisible(false);
            docNum.setVisible(false);
        }else {
            isEditable=true;
            infoNoEdit.setVisible(false);
            typeLa.setVisible(true);
            type.setVisible(true);
            docNumLa.setVisible(true);
            docNum.setVisible(true);
            type.setSelectedItem(personSelected.getDocumentTye());
            docNum.setText(personSelected.getDocumentNumber());
        }
        name.setText(personSelected.getName());
        lastName.setText(personSelected.getLastName());
        resDir.setText(personSelected.getResDirection());
        city.setText(personSelected.getCity());
    }
}
