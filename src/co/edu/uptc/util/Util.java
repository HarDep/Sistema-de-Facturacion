package co.edu.uptc.util;

import co.edu.uptc.config.GlobalConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Util {
    public static KeyListener getKeyListenerNavigable(Component com1){
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    com1.requestFocusInWindow();
                } else if (e.getComponent().getClass() == JButton.class&&e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ((JButton) e.getComponent()).doClick();
                }
            }
        };
    }
    public static KeyListener getListenerCheckedNumbers(){
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)){
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
    }
    public static KeyListener getListenerChecked(){
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c  = e.getKeyChar();
                if (!(Character.isDigit(c)||Character.isAlphabetic(c))){
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
    }
    public static JLabel getLabel(String text){
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    public static JTextField getSearchField(){
        JTextField fieldSearch = new JTextField();
        fieldSearch.setFont(GlobalConfig.DEFAULT_FIELD_SEARCH_FONT);
        return fieldSearch;
    }
}
