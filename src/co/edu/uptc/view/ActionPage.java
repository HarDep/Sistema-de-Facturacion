package co.edu.uptc.view;

import co.edu.uptc.config.GlobalConfig;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;
import java.awt.*;

public abstract class ActionPage<T> extends JPanel {
    protected final MainBoard mainBoard;
    protected final SectionPage<T> sectionPage;
    protected final String titleAction;
    protected JPanel panelContent;
    private final String keyAction;
    protected JButton actionButton;
    protected JButton backButton;

    public ActionPage(MainBoard mainBoard, SectionPage<T> sectionPage,String keyAction, String keySection) {
        this.mainBoard = mainBoard;
        this.sectionPage = sectionPage;
        this.keyAction = keyAction;
        this.titleAction = mainBoard.getValue(keyAction,keySection);
        createComponents();
    }
    private void createComponents() {
        this.setLayout(new BorderLayout());
        JPanel buttonsPanel = new JPanel();
        addButtonAction(buttonsPanel);
        backButton =mainBoard.getButtonBackTo(e -> backButtonAction());
        buttonsPanel.add(backButton);
        this.add(buttonsPanel,BorderLayout.SOUTH);
        this.add(mainBoard.getTitleSectionWithoutProperties(titleAction),BorderLayout.NORTH);
        panelContent=new JPanel();
        createFieldsSection();
        panelContent.setBorder(BorderFactory.createLineBorder(GlobalConfig.LINE_PANEL_COLOR));
        this.add(panelContent,BorderLayout.CENTER);
        this.add(new JPanel(),BorderLayout.WEST);
        this.add(new JPanel(),BorderLayout.EAST);
    }

    protected abstract void backButtonAction();

    private void addButtonAction(JPanel buttonsPanel) {
        actionButton = new JButton(mainBoard.getValue(keyAction));
        actionButton.addActionListener(e -> actionButton());
        buttonsPanel.add(actionButton);
    }

    protected abstract void actionButton();

    protected abstract void createFieldsSection();

    public abstract void begin(T value);
    public abstract void begin();
}
