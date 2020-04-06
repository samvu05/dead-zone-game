package com.t3h.mygame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndPanel extends JPanel implements Constants, ActionListener {
    private IEnd myAction;
    private Image imgBg;
    private Image imgDie;
    private int h=0;
    public EndPanel(){
        imgBg = new ImageIcon(getClass().getResource("/imgs/imgEndBG.png")).getImage();
        imgDie = new ImageIcon(getClass().getResource("/imgs/youDied.png")).getImage();
        setLayout(null);
        setSize(W_F,H_PLAY);
        initButton();
        initThread();
    }
    public void initButton(){
        JButton btnContinue = new JButton();
        btnContinue.setSize(150, 40);
        btnContinue.setLocation((W_F-150)/2,H_PLAY-250);
        btnContinue.setVerticalTextPosition(SwingConstants.CENTER);
        btnContinue.setHorizontalTextPosition(SwingConstants.CENTER);
        btnContinue.setForeground(Color.RED);
        btnContinue.setText("CONTINUE");
        btnContinue.addActionListener(this);
        add(btnContinue);

        JButton btnExit = new JButton();
        btnExit.setSize(100,30);
        btnExit.setLocation((W_F-100)/2,H_PLAY-200);
        btnExit.setVerticalTextPosition(SwingConstants.CENTER);
        btnExit.setHorizontalTextPosition(SwingConstants.CENTER);
        btnExit.setForeground(Color.RED);
        btnExit.setText("EXIT");
        btnExit.addActionListener(this);
        add(btnExit);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.drawImage(imgBg,0,0,W_F,H_PLAY,null);
        g2d.drawImage(imgDie,0,0,W_F,h,null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if ("CONTINUE".equals(action)) {
            myAction.showMenu();
        }
        if("EXIT".equals(action)) {
            System.out.println("exit");
            if (JOptionPane.showConfirmDialog(
                    this, "Do you want to exit ?", "Confirm Exit ",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
    public void setAction(IEnd action) {
        myAction = action;
    }
    public void autoResize() {
        if ( h > H_PLAY) {
            return;
        }
        h += 8;
    }
    public void initThread () {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        autoResize();
                        Thread.sleep(5);
                        repaint();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

}
