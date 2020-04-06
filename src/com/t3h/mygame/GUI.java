package com.t3h.mygame;

import javax.swing.*;

public class GUI extends JFrame implements Constants {
    private PlayPanel playPanel;
    private MenuPanel menuPanel;
    private EndPanel endPanel;
    private IMenu iMenu;
    private IEnd showMenu;
    private IPlay showEnd;

    public GUI(){
        setTitle("MY GAME");
        setSize(W_F,H_F);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
        menuPanel = new MenuPanel();
        iMenu = new IMenu() {
            @Override
            public void showPlay(int mapSize, int level) {
                addPlayRemoveMenu(mapSize,level);
            }
        };
        menuPanel.setAction(iMenu);
        add(menuPanel);
    }
    public void addPlayRemoveMenu(int mapSize, int level) {
        getContentPane().removeAll();
        playPanel = new PlayPanel(mapSize,level);
        showEnd = new IPlay() {
            @Override
            public void showEnd() {
                addEndRemovePlay();
            }
        };
        playPanel.setAction(showEnd);
        add(playPanel);
        repaint();
        playPanel.requestFocus();
        playPanel.setFocusable(true);
    }
    public void addEndRemovePlay(){
        getContentPane().removeAll();
        endPanel = new EndPanel();
        showMenu = new IEnd() {
            @Override
            public void showMenu() {
                addMenuRemoveEnd();
            }
        };
        endPanel.setAction(showMenu);
        add(endPanel);
        repaint();
        endPanel.requestFocus();
        endPanel.setFocusable(true);
    }
    public void addMenuRemoveEnd(){
        getContentPane().removeAll();
        menuPanel = new MenuPanel();
        iMenu = new IMenu() {
            @Override
            public void showPlay(int mapSize, int level) {
                addPlayRemoveMenu(mapSize,level);
            }
        };
        menuPanel.setAction(iMenu);
        add(menuPanel);
        repaint();
        menuPanel.requestFocus();
        menuPanel.setFocusable(true);

    }
}
