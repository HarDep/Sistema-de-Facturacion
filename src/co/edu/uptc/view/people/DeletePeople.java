package co.edu.uptc.view.people;

import co.edu.uptc.config.GlobalConfig;
import co.edu.uptc.model.simpleList.UPTCList;
import co.edu.uptc.pojo.Person;
import co.edu.uptc.util.Util;
import co.edu.uptc.view.ShowDialog;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DeletePeople extends JDialog {
    private final MainBoard mainBoard;
    private List<Person> list;
    private final List<Person> toRemove;
    private DefaultTableModel details;
    private JButton confirmAction;
    private JButton delete;
    private JPanel title;
    private JPanel buttonsPanel;
    private JLabel titleLab;
    private JButton cancel;

    public DeletePeople(MainBoard mainBoard) {
        super(mainBoard,true);
        this.mainBoard=mainBoard;
        setTitle(mainBoard.getValue("evaluation_title"));
        setSize(400,300);
        setLocationRelativeTo(mainBoard);
        setResizable(false);
        list=new UPTCList<>();
        toRemove = new UPTCList<>();
        createComponents();
        putMode();
    }

    private void putMode() {
        if (mainBoard.isDark()){
            title.setBackground(GlobalConfig.DIALOG_COLOR_DARK);
            titleLab.setForeground(GlobalConfig.FONT_COLOR_DARK);
            buttonsPanel.setBackground(GlobalConfig.DIALOG_COLOR_DARK);
        }else {
            title.setBackground(GlobalConfig.DIALOG_COLOR_LIGHT);
            titleLab.setForeground(GlobalConfig.FONT_COLOR_LIGHT);
            buttonsPanel.setBackground(GlobalConfig.DIALOG_COLOR_LIGHT);
        }
    }

    private void createComponents() {
        this.setLayout(new BorderLayout());
        title = new JPanel();
        titleLab = new JLabel(mainBoard.getValue("evaluation_title"));
        title.add(titleLab);
        this.add(title,BorderLayout.NORTH);
        addTable();
        buttonsPanel = new JPanel();
        delete = new JButton(mainBoard.getValue("action_2"));
        delete.addActionListener(e -> {
            list=mainBoard.getPresenter().getListPeople();
            removePeopleWithoutBill();
            showToRemove();
        });
        cancel = new JButton(mainBoard.getValue("generic_word_5"));
        cancel.addActionListener(e -> this.dispose());
        confirmAction = new JButton(mainBoard.getValue("evaluation_button"));
        confirmAction.addActionListener(e -> doConfirmAction());
        buttonsPanel.add(confirmAction);
        buttonsPanel.add(delete);
        buttonsPanel.add(cancel);
        this.add(buttonsPanel,BorderLayout.SOUTH);
        confirmAction.setEnabled(false);
        addKeyNavigable();
    }

    private void addKeyNavigable() {
        delete.addKeyListener(Util.getKeyListenerNavigable(cancel));
        cancel.addKeyListener(Util.getKeyListenerNavigable(delete));
    }

    private void addTable() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        details= new DefaultTableModel(mainBoard.getValue("items_person").split(";"), 0);
        JTable t = new JTable(details);
        t.setEnabled(false);
        t.getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll = new JScrollPane(t);
        tablePanel.add(scroll,BorderLayout.CENTER);
        this.add(tablePanel,BorderLayout.CENTER);
    }

    private void doConfirmAction() {
        for (Person person:toRemove) {
            mainBoard.getPresenter().deletePerson(person);
        }
        this.dispose();
        ShowDialog<Person> show = new ShowDialog<>(mainBoard,mainBoard.getValue("section_3"),mainBoard.getPresenter().getListPeople(),4);
        show.setVisible(true);
    }

    private void showToRemove() {
        for (Person person:toRemove) {
            details.addRow(new Object[]{person.getDocumentTye(),person.getDocumentNumber(),person.getName(),person.getLastName()
                    ,person.getResDirection(),person.getCity()});
        }
        confirmAction.setEnabled(true);
        delete.setEnabled(false);
        cancel.removeKeyListener(Util.getKeyListenerNavigable(delete));
        cancel.addKeyListener(Util.getKeyListenerNavigable(confirmAction));
        confirmAction.addKeyListener(Util.getKeyListenerNavigable(cancel));
    }

    private void removePeopleWithoutBill() {
        for (Person person:list) {
            if (!(mainBoard.getPresenter().isThePersonReferencedInABill(person))){
                toRemove.add(person);
            }
        }
    }
}
