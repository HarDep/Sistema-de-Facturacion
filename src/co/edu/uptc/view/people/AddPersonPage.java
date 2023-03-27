package co.edu.uptc.view.people;

import co.edu.uptc.pojo.Person;
import co.edu.uptc.util.Util;
import co.edu.uptc.view.ActionPage;
import co.edu.uptc.view.SectionPage;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;
import java.awt.*;

public class AddPersonPage extends ActionPage<Person> {
    private JComboBox<String> documentType;
    private JTextField documentNumber;
    private JTextField name;
    private JTextField lastName;
    private JTextField residenceDir;
    private JTextField city;
    public AddPersonPage(MainBoard mainBoard, SectionPage<Person> sectionPage) {
        super(mainBoard, sectionPage, "action_1","section_3_singular");
    }

    @Override
    protected void backButtonAction() {
        sectionPage.prepareShow();
    }

    @Override
    protected void actionButton() {
        if (mainBoard.getPresenter().isThisPerson((String) documentType.getSelectedItem(),documentNumber.getText())||isSomeEmpty()){
            JOptionPane.showMessageDialog(mainBoard, mainBoard.getValue("message_already_per"),mainBoard.getValue("action_1","section_3_singular"), JOptionPane.INFORMATION_MESSAGE);
        }else {
            if(mainBoard.getConfirmDialog("action_1","section_3_singular","confirm_action_1")){
                mainBoard.getPresenter().addPerson(new Person((String) documentType.getSelectedItem(),documentNumber.getText(),name.getText(),lastName.getText(),residenceDir.getText(),city.getText()));
                JOptionPane.showMessageDialog(mainBoard, mainBoard.getValue("section_3_singular","message_added"),mainBoard.getValue("action_1","section_3_singular"), JOptionPane.INFORMATION_MESSAGE);
                mainBoard.setToShow(sectionPage);
            }
        }
    }

    @Override
    protected void createFieldsSection() {
        panelContent = new JPanel(new GridLayout(6, 2, 0, 10));
        panelContent.add(new JLabel(mainBoard.getValue("person_field_1","generic_word_1")));
        documentType = new JComboBox<>(mainBoard.getValue("id_type_fields").split(";"));
        panelContent.add(documentType);
        panelContent.add(new JLabel(mainBoard.getValue("person_field_1","generic_word_2")));
        documentNumber = new JTextField();
        documentNumber.addKeyListener(Util.getListenerChecked());
        panelContent.add(documentNumber);
        panelContent.add(new JLabel(mainBoard.getValue("person_field_2")));
        name = new JTextField();
        panelContent.add(name);
        panelContent.add(new JLabel(mainBoard.getValue("person_field_3")));
        lastName = new JTextField();
        panelContent.add(lastName);
        panelContent.add(new JLabel(mainBoard.getValue("person_field_4")));
        residenceDir = new JTextField();
        panelContent.add(residenceDir);
        panelContent.add(new JLabel(mainBoard.getValue("person_field_5")));
        city = new JTextField();
        panelContent.add(city);
        addKeyNavigable();
    }
    private void addKeyNavigable(){
        documentType.addKeyListener(Util.getKeyListenerNavigable(documentNumber));
        documentNumber.addKeyListener(Util.getKeyListenerNavigable(name));
        name.addKeyListener(Util.getKeyListenerNavigable(lastName));
        lastName.addKeyListener(Util.getKeyListenerNavigable(residenceDir));
        residenceDir.addKeyListener(Util.getKeyListenerNavigable(city));
        city.addKeyListener(Util.getKeyListenerNavigable(actionButton));
        actionButton.addKeyListener(Util.getKeyListenerNavigable(backButton));
        backButton.addKeyListener(Util.getKeyListenerNavigable(documentType));
    }

    @Override
    public void begin(Person value) {
    }

    @Override
    public void begin() {
        documentNumber.setText("");
        name.setText("");
        lastName.setText("");
        residenceDir.setText("");
        city.setText("");
        mainBoard.setToShow(this);
        documentType.requestFocusInWindow();
    }
    private boolean isSomeEmpty(){
        return documentNumber.getText().equals("")|| name.getText().equals("")||lastName.getText().equals("")|| residenceDir.getText().equals("")||city.getText().equals("");
    }
}
