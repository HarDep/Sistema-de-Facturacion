package co.edu.uptc.view;

import co.edu.uptc.util.Util;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;
import java.awt.*;

public abstract class SectionPage<T> extends JPanel {
    protected final MainBoard mainBoard;
    protected final String keyTitle;
    private final String keyPathImage;
    protected ActionPage<T> addPage;
    protected ActionPage<T> deletePage;
    protected ActionPage<T> editPage;
    protected SearchPage<T> deleteSearchPage;
    protected SearchPage<T> editSearchPage;
    protected String keySingular;
    protected JButton backButton;
    private JButton action1;
    private JButton action2;
    private JButton action3;
    protected JButton showButton;
    protected JPanel buttonsPanel;

    public SectionPage(MainBoard mainBoard, String keyTitle, String keyPathImage) {
        this.mainBoard = mainBoard;
        this.keyTitle = keyTitle;
        this.keyPathImage = keyPathImage;
        createComponents();
    }

    private void createComponents() {
        this.setLayout(new BorderLayout());
        JPanel contentPaneSection = new JPanel();
        addButtonsPanel();
        mainBoard.addComponentsInMainPanels(contentPaneSection, keyTitle, keyPathImage, buttonsPanel);
        this.add(contentPaneSection,BorderLayout.CENTER);
        JPanel panel = new JPanel();
        backButton =mainBoard.getButtonBackTo(mainBoard.getActionShowFirstPage());
        addKeyNavigable();
        panel.add(backButton);
        this.add(panel, BorderLayout.SOUTH);
        createActionPages();
        mainBoard.add(addPage);
        mainBoard.add(deletePage);
        mainBoard.add(editPage);
    }
    private void addKeyNavigable(){
        action1.addKeyListener(Util.getKeyListenerNavigable(action2));
        action2.addKeyListener(Util.getKeyListenerNavigable(action3));
        action3.addKeyListener(Util.getKeyListenerNavigable(showButton));
        showButton.addKeyListener(Util.getKeyListenerNavigable(backButton));
        backButton.addKeyListener(Util.getKeyListenerNavigable(action1));
    }

    protected abstract void createActionPages();

    private void addButtonsPanel() {
        buttonsPanel = new JPanel();
        action1=mainBoard.getButton("action_1",e -> addPage.begin());
        buttonsPanel.add(action1);
        action2=mainBoard.getButton("action_2",e -> beginDeleteAction());
        buttonsPanel.add(action2);
        action3=mainBoard.getButton("action_3",e -> beginEditAction());
        buttonsPanel.add(action3);
        showButton = new JButton(mainBoard.getValue("action_4",keyTitle));
        showButton.addActionListener(e -> showList());
        buttonsPanel.add(showButton);
    }
    protected abstract void showList();

    public void beginEditAction(){
        mainBoard.setToShow(editSearchPage);
        editSearchPage.prepareContent();
    }

    public void beginDeleteAction(){
        mainBoard.setToShow(deleteSearchPage);
        deleteSearchPage.prepareContent();
    }
    protected abstract void searchDeleteAction(String type, String search);
    protected abstract void searchEditAction(String type, String search);
    protected JPanel getTitleWithAction(String keyAction){
        return mainBoard.getTitleSectionWithoutProperties(mainBoard.getValue(keyAction)+ keySingular);
    }
    public void prepareShow(){
        mainBoard.setToShow(this);
        action1.requestFocusInWindow();
    }
}
