package co.edu.uptc.view.bills;

import co.edu.uptc.config.GlobalConfig;
import co.edu.uptc.pojo.Bill;
import co.edu.uptc.util.Util;
import co.edu.uptc.view.mainBoard.MainBoard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResultsBillsPage extends JPanel {
    private final MainBoard mainBoard;
    private final BillsPage billsPage;
    private final boolean isDeletePage;
    private final JPanel title;
    private JPanel panelBills;
    private JScrollPane results;
    private JButton backButton;
    private JPanel panelResults;

    public ResultsBillsPage(JPanel title, MainBoard mainBoard,BillsPage billsPage, boolean isDeletePage) {
        this.mainBoard = mainBoard;
        this.billsPage = billsPage;
        this.title=title;
        this.isDeletePage = isDeletePage;
        createComponents();
    }

    private void createComponents() {
        this.setLayout(new BorderLayout());
        this.add(title,BorderLayout.NORTH);
        panelBills = new JPanel();
        panelBills.setBorder(BorderFactory.createLineBorder(GlobalConfig.LINE_PANEL_COLOR));
        panelBills.setLayout(new BorderLayout());
        this.add(panelBills,BorderLayout.CENTER);
        this.add(new JPanel(),BorderLayout.WEST);
        this.add(new JPanel(),BorderLayout.EAST);
    }
    protected void addBackButton(JButton back){
        JPanel button = new JPanel();
        backButton = back;
        button.add(backButton);
        this.add(button,BorderLayout.SOUTH);
    }
    protected void beginResults(String search){
        if (results!=null){
            panelBills.remove(results);
        }
        results=getResults(search);
        panelBills.add(results,BorderLayout.CENTER);
        mainBoard.setToShow(this);
        addKeyNavigable();
    }
    public void prepareShow(){
        mainBoard.setToShow(this);
        ((JPanel)panelResults.getComponent(0)).getComponent(1).requestFocusInWindow();
    }
    private JScrollPane getResults(String search) {
        panelResults = new JPanel();
        if (search.equals("")){
            panelResults.add(new JLabel(mainBoard.getValue("bill_search_message")));
        }else {
            String field =mainBoard.getValue("bill_field_4");
            String textButton = mainBoard.getValue("generic_word_6");
            List<Bill> bills = mainBoard.getPresenter().getBills(search);
            if (mainBoard.isDark()){
                panelResults.setBackground(GlobalConfig.PANEL_COLOR_DARK);
            }else {
                panelResults.setBackground(GlobalConfig.PANEL_COLOR_LIGHT);
            }
            panelResults.setPreferredSize(new Dimension(560,400));
            int count =0;
            for (Bill bill:bills) {
                JPanel panel1 = new JPanel();
                panel1.setBorder(BorderFactory.createLineBorder(GlobalConfig.LINE_PANEL_COLOR));
                panel1.setPreferredSize(new Dimension(550,40));
                panel1.add(new JLabel(field+" " + bill.getBillHead().getBillNumber()));
                JButton button = new JButton(textButton);
                button.addActionListener(e ->billsPage.beginResult(bill,isDeletePage));
                panel1.add(button);
                panelResults.add(panel1);
                count++;
            }
            if (count==0){
                panelResults.add(new JLabel(mainBoard.getValue("bill_search_message")));
            }
        }
        return new JScrollPane(panelResults);
    }

    private void addKeyNavigable() {
        if (panelResults.getComponent(0).getClass()==JLabel.class){
            backButton.addKeyListener(Util.getKeyListenerNavigable(backButton));
            backButton.requestFocusInWindow();
        }else {
            for (int i = 0; i < panelResults.getComponents().length; i++) {
                JPanel com = (JPanel) panelResults.getComponent(i);
                if (i!=panelResults.getComponents().length-1){
                    JPanel next = (JPanel) panelResults.getComponent(i+1);
                    com.getComponent(1).addKeyListener(Util.getKeyListenerNavigable(next.getComponent(1)));
                }else {
                    com.getComponent(1).addKeyListener(Util.getKeyListenerNavigable(backButton));
                }
                if (i==0){
                    backButton.addKeyListener(Util.getKeyListenerNavigable(com.getComponent(1)));
                    com.getComponent(1).requestFocusInWindow();
                }
            }
        }
    }
}
