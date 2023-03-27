package co.edu.uptc.view;

import co.edu.uptc.util.Util;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SearchPage<T> extends JPanel {
    private final MainBoard mainBoard;
    private JTextField fieldSearch;
    private final SectionPage<T> sectionPage;
    private JComboBox<String> type;
    private JPanel typeContent;
    private JButton backButton;

    public SearchPage(MainBoard mainBoard,SectionPage<T> sectionPage, JPanel titleLabel, String searchType, boolean isDeleteAction) {
        this.mainBoard = mainBoard;
        this.sectionPage = sectionPage;
        createComponents(titleLabel,searchType,isDeleteAction);
    }

    private void createComponents(JPanel titleLabel, String searchType,boolean isDeleteAction) {
        this.setLayout(new BorderLayout());
        this.add(titleLabel,BorderLayout.NORTH);
        JPanel panel = new JPanel(new GridLayout(4,1,0,10));
        typeContent=new JPanel();
        panel.add(typeContent);
        panel.add(Util.getLabel(mainBoard.getValue("search_message_1")+" " + searchType));
        panel.add(Util.getLabel(mainBoard.getValue("search_message_2")));
        fieldSearch = Util.getSearchField();
        fieldSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c)||Character.isAlphabetic(c))){
                    e.consume();
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    backButton.requestFocusInWindow();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (isDeleteAction){
                        sectionPage.searchDeleteAction(type!=null?(String) type.getSelectedItem():null,fieldSearch.getText());
                    }else {
                        sectionPage.searchEditAction(type!=null?(String) type.getSelectedItem():null,fieldSearch.getText());
                    }
                }
            }
        });
        panel.add(fieldSearch);
        this.add(panel,BorderLayout.CENTER);
        this.add(new JPanel(),BorderLayout.EAST);
        this.add(new JPanel(),BorderLayout.WEST);
    }
    public void addTypeComboBox(JComboBox<String> type, String typeDescription){
        this.typeContent.add(Util.getLabel(typeDescription));
        this.type = type;
        this.typeContent.add(type);
    }
    public void addButtonBack(JButton back){
        JPanel button = new JPanel();
        backButton = back;
        button.add(backButton);
        this.add(button,BorderLayout.SOUTH);
    }
    private void addKeyNavigable(){
        if (type!=null){
            type.addKeyListener(Util.getKeyListenerNavigable(fieldSearch));
            backButton.addKeyListener(Util.getKeyListenerNavigable(type));
        }else {
            backButton.addKeyListener(Util.getKeyListenerNavigable(fieldSearch));
        }
    }
    public void prepareContent(){
        fieldSearch.setText("");
        addKeyNavigable();
        fieldSearch.requestFocusInWindow();
    }
}
