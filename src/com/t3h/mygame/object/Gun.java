package com.t3h.mygame.object;

import com.t3h.mygame.Constants;
import com.t3h.mygame.SoundManager;
import com.t3h.mygame.Utils;
import java.awt.*;
import java.util.ArrayList;

public class Gun extends Object2D implements Constants {
    public static final int THE_LEFT_IN_SCR = 0;
    public static final int THE_TOP_IN_SCR = 1;
    public static final int THE_RIGHT_IN_SCR = 2;
    public static final int THE_BOT_IN_SCR = 3;
    private ArrayList<Boss> bosses = new ArrayList<>();
    private Image imgs[] = new Image[4];
    private int sizeItem;

    public Gun(int SIZE_ITEM) {
        this.sizeItem = SIZE_ITEM;
        initImags();
    }

    public void draw(Graphics2D g2d) {
        img = imgs[ori];
        super.draw(g2d);
        for (int i = 0; i < bosses.size(); i++){
            bosses.get(i).draw(g2d);
        }
    }
    private void initImags() {
        imgs[LEFT] = Utils.getImage("/imgs/arLEFT.png", sizeItem, sizeItem);
        imgs[UP] = Utils.getImage("/imgs/arUP.png", sizeItem, sizeItem);
        imgs[RIGHT] = Utils.getImage("/imgs/arRIGHT.png", sizeItem, sizeItem);
        imgs[DOWN] = Utils.getImage("/imgs/arDOWN.png", sizeItem, sizeItem);
    }
    public int getBossesSize(){
        return bosses.size();
    }
    public Rectangle getBossBound(int i){
        return bosses.get(i).getBound();
    }
    public void moveAllBoss(){
        for (int i = 0; i < bosses.size(); i++){
            if(bosses.get(i)== null){
                continue;
            }
            if (bosses.get(i).getX()<0 || bosses.get(i).getX()>W_F || bosses.get(i).getY()<0 || bosses.get(i).getY()>H_PLAY){
                bosses.remove(bosses.get(i));
                continue;
            }
            switch (bosses.get(i).ori) {
                case DOWN:
                    bosses.get(i).y += 1;
                    break;
                case UP:
                    bosses.get(i).y -= 1;
                    break;
                case RIGHT:
                    bosses.get(i).x += 1;
                    break;
                case LEFT:
                    bosses.get(i).x -= 1;
                    break;
                default:
                    return;
            }

        }
    }
    public void bornBoss() {
        Boss boss = new Boss();
        boss.w = sizeItem;
        boss.h = sizeItem;
        switch (ori){
            case LEFT:
                boss.x = this.x- sizeItem;
                boss.y = this.y;
                boss.setOri(LEFT);
                break;
            case DOWN:
                boss.x = x;
                boss.y = y+ sizeItem;
                boss.setOri(DOWN);
                break;
            case RIGHT:
                boss.x = x+ sizeItem;
                boss.y = y;
                boss.setOri(RIGHT);
                break;
            case UP:
                boss.x = x;
                boss.y = y- sizeItem;
                boss.setOri(UP);
                break;
        }
        bosses.add(boss);
    }

    public void changeImageAllBoss(long currentTime) {
        for(int i = 0;i<bosses.size();i++){
            bosses.get(i).changeImage(currentTime);
        }
    }
}
