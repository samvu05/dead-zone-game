package com.t3h.mygame.object;

import javax.swing.*;
import java.awt.*;

public class Boss extends ObjMov2D {
    private Image[] imgsRight;
    private Image[] imgsLeft;
    private int indexImage = 0;
    public Boss() {
        imgsRight = new Image[8];
        for (int i = 0; i < 8; i++) {
            imgsRight[i] = new ImageIcon(getClass().getResource("/imgs/bossRight" + i + ".png")).getImage();
        }
        imgsLeft = new Image[8];
        for (int i = 0; i < 8; i++) {
            imgsLeft[i] = new ImageIcon(getClass().getResource("/imgs/bossLeft" + i + ".png")).getImage();
        }
        ori = RIGHT;
    }
    @Override
    public void draw(Graphics2D g2d) {
        switch (ori) {
            case LEFT:
            case DOWN:
                img = imgsLeft[indexImage];
                break;
            case RIGHT:
            case UP:
                img = imgsRight[indexImage];
                break;
            default:
                break;
        }
        super.draw(g2d);
    }
    public void changeImage(long currentTime) {

        if (currentTime % 100 != 0) {
            return;
        }
        indexImage = (indexImage + 1) % 8;
    }
    public Rectangle getBound(){
        return new Rectangle(x+10,y+10,w-20,h-20);
    }
}
