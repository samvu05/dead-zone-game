package com.t3h.mygame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel implements Constants, ActionListener {
    private int btnW = 250;
    private int btnH = 50;
    private Image imgBg;
    private Image imgSpectre;
    private Image imgTutorial;
    private IMenu myAction;
    private int indexImage = 0;
    private String[] levels = {"Easy","Normal","Hard"};
    private String[] mapSizes = {"4*4","5*5","6*6"};
    private Image[] spectre;
    private JComboBox levelCombb;
    private JComboBox mapSizeCombb;
    private long currentTime = 0;
    private int toturialW =W_F-400, tutorialH =0;
    private int temp = 0;
    private SoundManager soundBg;

    public MenuPanel(){
        soundBg = new SoundManager("/Sound/soundPlay.wav");
        imgBg = new ImageIcon(getClass().getResource("/imgs/imgMenuBG.png")).getImage();
        imgTutorial = new ImageIcon(getClass().getResource("/imgs/imgTutorial.png")).getImage();
        spectre = new Image[8];
        for(int i=0;i<8;i++){
            spectre[i] = new ImageIcon(getClass().getResource("/imgs/spectre" + i + ".png")).getImage();
        }
        setLayout(null);
        setSize(W_F,H_F);
        initButton();
        initLevel();
        initMapSize();
        initThread();
        soundBg.play(true);
    }
    public void initButton(){
        JButton btnPlay = new JButton();
        btnPlay.setSize(btnW, btnH);
        btnPlay.setLocation((W_F- btnW)/2,H_F-250);
        btnPlay.setVerticalTextPosition(SwingConstants.CENTER);
        btnPlay.setHorizontalTextPosition(SwingConstants.CENTER);
        btnPlay.setForeground(Color.RED);
        btnPlay.setText("CLICK TO PLAY");
        btnPlay.addActionListener(this);
        btnPlay.setActionCommand("PLAY");
        add(btnPlay);

        JButton btnTutorial = new JButton();
        btnTutorial.setSize(btnW /2-10, btnH);
        btnTutorial.setLocation((W_F- btnW)/2,H_F-175);;
        btnTutorial.setVerticalTextPosition(SwingConstants.CENTER);
        btnTutorial.setHorizontalTextPosition(SwingConstants.CENTER);
        btnTutorial.setForeground(Color.RED);
        btnTutorial.setText("TUTORIAL");
        btnTutorial.addActionListener(this);
        btnTutorial.setActionCommand("TUTORIAL");
        add(btnTutorial);

        JButton btnExit = new JButton();
        btnExit.setSize(btnW /2-10, btnH);
        btnExit.setLocation(W_F/2+10,H_F-175);
        btnExit.setVerticalTextPosition(SwingConstants.CENTER);
        btnExit.setHorizontalTextPosition(SwingConstants.CENTER);
        btnExit.setForeground(Color.RED);
        btnExit.setText("EXIT");
        btnExit.addActionListener(this);
        btnExit.setActionCommand("EXIT");
        add(btnExit);


    }
    public void initLevel(){
        levelCombb = new JComboBox(levels);
        levelCombb.setSize(btnW /2, btnH /2);
        levelCombb.setLocation((W_F)/2,H_F-300);
        add(levelCombb);
        JLabel levetLabel = new JLabel();
        levetLabel.setForeground(Color.RED);
        levetLabel.setSize(btnW /2, btnH /2);
        levetLabel.setText("SELECT  LEVEL");
        levetLabel.setLocation((W_F- btnW)/2,H_F-300);
        add(levetLabel);
    }
    public void initMapSize(){
        mapSizeCombb = new JComboBox(mapSizes);
        mapSizeCombb.setSize(btnW /2, btnH /2);
        mapSizeCombb.setLocation((W_F)/2,H_F-350);

        add(mapSizeCombb);
        JLabel mapSizeLabel = new JLabel();
        mapSizeLabel.setForeground(Color.RED);
        mapSizeLabel.setSize(btnW /2, btnH /2);
        mapSizeLabel.setText("SELECT SIZE");
        mapSizeLabel.setLocation((W_F- btnW)/2,H_F-350);
        add(mapSizeLabel);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if ("PLAY".equals(action)) {
            soundBg.stop();
            myAction.showPlay(getMapSize(),getLevel());
        }
        if("TUTORIAL".equals(action)){
            temp += 1;
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
    public void changeImage(long currentTime) {

        if (currentTime % 100 != 0) {
            return;
        }
        indexImage = (indexImage + 1) % 8;
        imgSpectre = spectre[indexImage];
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.drawImage(imgBg,0,0,W_F,H_PLAY,null);
        g2d.drawImage(imgSpectre,300,230,150,150,null);
        if(temp%2 == 1){
            g2d.drawImage(imgTutorial,200,210, toturialW, tutorialH,null);
        }
    }
    public int getLevel(){
        return levelCombb.getSelectedIndex()+1;
    }
    public int getMapSize(){
        return mapSizeCombb.getSelectedIndex()+4;
    }
    public void setAction(IMenu action) {
        myAction = action;
    }
    public void changeTutorialSize(){
        if(temp%2 != 1){
            tutorialH =0;
            return;
        }
        if (tutorialH > 230){
            return;
        }
        tutorialH +=1;

    }

    public void initThread(){
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        changeTutorialSize();
                        currentTime+=1;
                        changeImage(currentTime);
                        repaint();
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        th.start();
    }
}
