package co.edu.uptc.view.mainMenu;

import co.edu.uptc.config.GlobalConfig;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;
import java.awt.*;

public class MainMenuBar extends JMenuBar {
    private final MainBoard mainBoard;
    private JMenu menuAppearance;
    private JMenu menuShortcuts;
    private JMenuItem mainPageItem;
    private JMenuItem productsPageItem;
    private JMenuItem billsPageItem;
    private JMenuItem peoplePageItem;

    public MainMenuBar(MainBoard mainBoard) {
        this.mainBoard=mainBoard;
        this.setBorderPainted(false);
        this.createComponents();
    }
    private void createComponents() {
        addMenuAppearance();
        addMenuShortcuts();
        addMenuAboutNavigation();
    }
    private void addPaddingToTheJMenu(JMenu menu){
        Dimension dim = menu.getPreferredSize();
        dim.width=dim.width + Integer.parseInt(mainBoard.getValue("padding_menu"));
        menu.setPreferredSize(dim);
    }
    private void addMenuAboutNavigation(){
        JMenu navigation = new JMenu(mainBoard.getValue("menu_3"));
        navigation.setMnemonic('N');
        navigation.setFont(GlobalConfig.FONT_MENU);
        addPaddingToTheJMenu(navigation);
        JMenuItem info = new JMenuItem(mainBoard.getValue("generic_word_7"));
        info.setMnemonic('I');
        info.addActionListener(e -> JOptionPane.showMessageDialog(mainBoard,mainBoard.getValue(
                "navigation_message"),mainBoard.getValue("menu_3"),JOptionPane.INFORMATION_MESSAGE));
        navigation.add(info);
        this.add(navigation);
    }

    private void addMenuShortcuts() {
        menuShortcuts = new JMenu(mainBoard.getValue("menu_2"));
        menuShortcuts.setMnemonic('S');
        menuShortcuts.setFont(GlobalConfig.FONT_MENU);
        addPaddingToTheJMenu(menuShortcuts);
        this.add(menuShortcuts);
        addMainPage();
        addProductsPage();
        addBillsPage();
        addPeoplePage();
        addExit();
        setEnableShortcuts(false);
    }
    public void setEnableShortcuts(boolean changing){
        mainPageItem.setEnabled(changing);
        peoplePageItem.setEnabled(changing);
        billsPageItem.setEnabled(changing);
        productsPageItem.setEnabled(changing);
    }

    private void addExit() {
        JMenuItem menuItem = new JMenuItem(mainBoard.getValue("exit"),createIcon(mainBoard.getValue("exit_icon_dir")));
        menuItem.setMnemonic('x');
        menuItem.addActionListener(e ->{
            if (mainBoard.getConfirmDialog("exit","exit_message")){
                mainBoard.dispose();
            }
        });
        menuShortcuts.add(menuItem);
    }

    private void addPeoplePage() {
        peoplePageItem = new JMenuItem(mainBoard.getValue("section_3"), createIcon(mainBoard.getValue("section_3_icon_dir")));
        peoplePageItem.setMnemonic('e');
        peoplePageItem.addActionListener(mainBoard.getActionShowPeoplePage());
        menuShortcuts.add(peoplePageItem);
    }

    private void addBillsPage() {
        billsPageItem = new JMenuItem(mainBoard.getValue("section_2"), createIcon(mainBoard.getValue("section_2_icon_dir")));
        billsPageItem.setMnemonic('B');
        billsPageItem.addActionListener(mainBoard.getActionShowBillsPage());
        menuShortcuts.add(billsPageItem);
    }

    private void addProductsPage() {
        productsPageItem = new JMenuItem(mainBoard.getValue("section_1"), createIcon(mainBoard.getValue("section_1_icon_dir")));
        productsPageItem.setMnemonic('P');
        productsPageItem.addActionListener(mainBoard.getActionShowProductsPage());
        menuShortcuts.add(productsPageItem);
    }

    private void addMainPage() {
        mainPageItem = new JMenuItem(mainBoard.getValue("main_title"), createIcon(mainBoard.getValue("first_page_icon_dir")));
        mainPageItem.setMnemonic('M');
        mainPageItem.addActionListener(mainBoard.getActionShowFirstPage());
        menuShortcuts.add(mainPageItem);
    }

    private void addMenuAppearance() {
        menuAppearance = new JMenu(mainBoard.getValue("menu_1"));
        menuAppearance.setMnemonic('A');
        menuAppearance.setFont(GlobalConfig.FONT_MENU);
        addPaddingToTheJMenu(menuAppearance);
        this.add(menuAppearance);
        addDarkMode();
        addLightMode();
    }
    private void addLightMode() {
        JMenuItem lightMenu = new JMenuItem(mainBoard.getValue("mode_1"), createIconMode(mainBoard.getValue("light_mode_icon_dir")));
        lightMenu.setMnemonic('L');
        lightMenu.addActionListener(e -> mainBoard.lightAppearance());
        menuAppearance.add(lightMenu);
    }
    private void addDarkMode() {
        JMenuItem darkMenu = new JMenuItem(mainBoard.getValue("mode_2"), createIconMode(mainBoard.getValue("dark_mode_icon_dir")));
        darkMenu.setMnemonic('D');
        darkMenu.addActionListener(e -> mainBoard.darkAppearance());
        menuAppearance.add(darkMenu);
    }
    public void setMenuColor(Color fontColor,Color itemColor){
        for (Component component:this.getComponents()) {
            component.setForeground(fontColor);
            for (Component component2:((JMenu)component).getMenuComponents()) {
                component2.setForeground(fontColor);
                component2.setBackground(itemColor);
            }
        }
    }
    private static ImageIcon createIconMode(String fileName){
        ImageIcon icon = new ImageIcon(fileName);
        Image image = icon.getImage();
        Image scaledInstance = image.getScaledInstance(40, 20, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledInstance);
        return  icon;
    }
    private static ImageIcon createIcon(String fileName){
        ImageIcon icon = new ImageIcon(fileName);
        Image image = icon.getImage();
        Image scaledInstance = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledInstance);
        return  icon;
    }
}
