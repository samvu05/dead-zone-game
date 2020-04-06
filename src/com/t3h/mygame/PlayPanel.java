package com.t3h.mygame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayPanel extends JPanel implements Constants, KeyListener {
    private ManagerPlayer manager;
    private long currentTime =0;
    private Image imgBg;

    public PlayPanel(int mapSize, int level){
        manager = new ManagerPlayer(mapSize,level);
        imgBg = new ImageIcon(getClass().getResource("/imgs/imgPlayBG.png")).getImage();
        setSize(W_F,H_PLAY);
        setLocation(0,H_HEADER);
        setBackground(Color.BLACK);
        setFocusable(true);
        setRequestFocusEnabled(true);
        addKeyListener(this);
        initThread();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.drawImage(imgBg,0,0,W_F,H_PLAY,null);
        manager.DrawAll(g2d);
    }
    public void initThread(){
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    manager.stepTheard(currentTime);
                    currentTime += 1;
                    repaint();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        th.start();
    }
    public void setAction(IPlay action){
        manager.setAction(action);
    }
    @Override
    public void keyTyped(KeyEvent e) { }
    @Override
    public void keyPressed(KeyEvent e) {
        manager.keyPressed(e.getKeyCode());
    }
    @Override
    public void keyReleased(KeyEvent e) {
        manager.keyRelease(e.getKeyCode());
    }

}
