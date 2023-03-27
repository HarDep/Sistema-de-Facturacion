package co.edu.uptc.view.mainBoard;

import co.edu.uptc.config.GlobalConfig;
import co.edu.uptc.config.ManagerProperties;
import co.edu.uptc.contract.MainContract;
import co.edu.uptc.util.Util;
import co.edu.uptc.view.bills.BillsPage;
import co.edu.uptc.view.mainMenu.MainMenuBar;
import co.edu.uptc.view.people.PeoplePage;
import co.edu.uptc.view.products.ProductsPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainBoard extends JFrame implements MainContract.View {
    protected final ManagerProperties properties;
    private JPanel firstPage;
    private BillsPage billsPage;
    private PeoplePage peoplePage;
    private ProductsPage productsPage;
    private MainMenuBar menuBar;
    private MainContract.Presenter presenter;

    private boolean isDark;
    private JButton section1;
    private JButton section2;
    private JButton section3;

    public MainBoard() throws Exception {
        super(ManagerProperties.getInstance().get("title"));
        setSize(700, 530);
        setMinimumSize(new Dimension(700, 530));
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.properties = ManagerProperties.getInstance();
        this.setLayout(new OverlayLayout(this.getContentPane()));
        this.createComponents();
        lightAppearance();
        prepareShowFirstPage();
    }
    public boolean isDark() {
        return isDark;
    }
    public MainContract.Presenter getPresenter() {
        return presenter;
    }

    private void createComponents() {
        firstPage=new JPanel();
        createFirstPageComponents();
        this.getContentPane().add(firstPage);
        billsPage = new BillsPage(this);
        this.getContentPane().add(billsPage);
        peoplePage = new PeoplePage(this);
        this.getContentPane().add(peoplePage);
        productsPage = new ProductsPage(this);
        this.getContentPane().add(productsPage);
        this.menuBar = new MainMenuBar(this);
        this.setJMenuBar(menuBar);
    }
    public ActionListener getActionShowPeoplePage(){
        return e -> peoplePage.prepareShow();
    }
    public ActionListener getActionShowProductsPage(){
        return e -> productsPage.prepareShow();
    }
    public ActionListener getActionShowBillsPage(){
        return e -> billsPage.prepareShow();
    }
    public ActionListener getActionShowFirstPage(){
        return e -> prepareShowFirstPage();
    }

    private void createFirstPageComponents() {
        JPanel southPanel = new JPanel();
        section1=getButton("section_1",e -> productsPage.prepareShow());
        southPanel.add(section1);
        section2=getButton("section_2",e -> billsPage.prepareShow());
        southPanel.add(section2);
        section3=getButton("section_3",e -> peoplePage.prepareShow());
        southPanel.add(section3);
        addComponentsInMainPanels(firstPage,"title","first_page_icon_dir",southPanel);
        addKeyNavigable();
    }
    private void addKeyNavigable(){
        section1.addKeyListener(Util.getKeyListenerNavigable(section2));
        section2.addKeyListener(Util.getKeyListenerNavigable(section3));
        section3.addKeyListener(Util.getKeyListenerNavigable(section1));
    }
    private void prepareShowFirstPage(){
        setToShow(firstPage);
        section1.requestFocusInWindow();
    }

    public void addComponentsInMainPanels(JPanel mainPanel,String title,String imagePath,JPanel buttonsPanel){
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(getTitleSection(title),BorderLayout.NORTH);
        mainPanel.add(getImage(imagePath),BorderLayout.CENTER);
        mainPanel.add(buttonsPanel,BorderLayout.SOUTH);
    }

    public void setToShow(JPanel panel){
        setVisibleAll();
        panel.setVisible(true);
        if (menuBar!=null){
            menuBar.setEnableShortcuts(panel != firstPage);
        }
    }

    private void setVisibleAll(){
        for (Component component:this.getContentPane().getComponents()) {
            component.setVisible(false);
        }
    }

    private JLabel getImage(String pathImage){
        ImageIcon icon = new ImageIcon(properties.get(pathImage));
        Image image = icon.getImage();
        Image scaledInstance = image.getScaledInstance(210, 170, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledInstance);
        return new JLabel(icon);
    }

    public JPanel getTitleSection(String keySection) {
        JPanel panel = new JPanel();
        JLabel title = new JLabel(properties.get(keySection));
        title.setFont(GlobalConfig.TITLES_FONT);
        panel.add(title);
        return panel;
    }
    public JPanel getTitleSectionWithoutProperties(String title) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(title);
        label.setFont(GlobalConfig.TITLES_FONT);
        panel.add(label);
        return panel;
    }
    public int getNumber(String keyNumber){
        return Integer.parseInt(properties.get(keyNumber));
    }
    public String getValue(String key1, String key2){
        return properties.get(key1) + properties.get(key2);
    }
    public String getValue(String key){
        return properties.get(key);
    }
    public JButton getButtonBackTo(ActionListener actionBack){
        JButton button = new JButton(properties.get("generic_word_3"));
        button.addActionListener(actionBack);
        return button;
    }
    public JButton getButton(String keyAction,ActionListener action){
        JButton button = new JButton(properties.get(keyAction));
        button.addActionListener(action);
        return button;
    }
    public boolean getConfirmDialog(String keyTitle,String keyMessage){
        return JOptionPane.showOptionDialog(this,properties.get(keyMessage),properties.get(keyTitle),JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,properties.get("confirm_options").split(";"),properties.get("confirm_word_1"))==JOptionPane.YES_OPTION;
    }
    public boolean getConfirmDialog(String keyTitle1,String keyTitle2,String keyMessage){
        return JOptionPane.showOptionDialog(this,properties.get(keyMessage),properties.get(keyTitle1)+properties.get(keyTitle2),JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,properties.get("confirm_options").split(";"),properties.get("confirm_word_1"))==JOptionPane.YES_OPTION;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void start() {
        this.setVisible(true);
    }

    public void darkAppearance() {
        isDark = true;
        firstPage.setBackground(GlobalConfig.PANEL_COLOR_DARK);
        setAppearance(GlobalConfig.FONT_COLOR_DARK,GlobalConfig.PANEL_COLOR_DARK);
        menuBar.setBackground(GlobalConfig.MENU_COLOR_DARK);
        menuBar.setMenuColor(GlobalConfig.FONT_COLOR_DARK, GlobalConfig.MENU_ITEM_COLOR_DARK);
    }

    public void lightAppearance() {
        isDark = false;
        setAppearance(GlobalConfig.FONT_COLOR_LIGHT,GlobalConfig.PANEL_COLOR_LIGHT);
        menuBar.setBackground(GlobalConfig.MENU_COLOR_LIGHT);
        menuBar.setMenuColor(GlobalConfig.FONT_COLOR_LIGHT, GlobalConfig.MENU_ITEM_COLOR_LIGHT);
    }
    private void setAppearance(Color fontColor,Color panelColor){
        for (Component c1:this.getContentPane().getComponents()) {
            c1.setBackground(panelColor);
            for (Component c2:((JPanel) c1).getComponents()) {
                if (c2.getClass()==JLabel.class){
                    c2.setForeground(fontColor);
                }else if (c2.getClass()==JPanel.class){
                    c2.setBackground(panelColor);
                    for (Component c3:((JPanel) c2).getComponents()) {
                        if (c3.getClass()==JLabel.class){
                            c3.setForeground(fontColor);
                        }else if (c3.getClass()==JPanel.class){
                            c3.setBackground(panelColor);
                            for (Component c4:((JPanel) c3).getComponents()) {
                                if (c4.getClass()==JLabel.class){
                                    c4.setForeground(fontColor);
                                }else if (c4.getClass()==JPanel.class){
                                    c4.setBackground(panelColor);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
