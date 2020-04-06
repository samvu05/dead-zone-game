package com.t3h.mygame.object;

import java.awt.*;

public class Object2D  {
    protected int x,y,w,h;
    protected Image img;
    protected int ori;
    public static final int LEFT = 0;
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;

    public void draw(Graphics2D g2d){
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawImage(img,x,y,w,h,null);
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getOri() {
        return ori;
    }

    public void setOri(int ori) {
        this.ori = ori;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
    public Rectangle getBound(){
        return new Rectangle(x,y,w,h);
    }
}
