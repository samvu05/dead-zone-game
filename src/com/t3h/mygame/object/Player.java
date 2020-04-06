package com.t3h.mygame.object;
import com.t3h.mygame.Constants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends ObjMov2D implements Constants {
    private boolean isLeft, isUp, isRight, isDown;
    private Image[] imgsRight;
    private Image[] imgsLeft;
    private int indexImage = 0;
    private int destinationPosition=-1;
    private int sizePad;
    private int delta = 2;

    public Player(int sizePad) {
        this.sizePad = sizePad;
        imgsRight = new Image[5];
        for (int i = 0; i < 5; i++) {
            imgsRight[i] = new ImageIcon(getClass().getResource("/imgs/playerR" + i + ".png")).getImage();
        }
        imgsLeft = new Image[5];
        for (int i = 0; i < 5; i++) {
            imgsLeft[i] = new ImageIcon(getClass().getResource("/imgs/playerL" + i + ".png")).getImage();
        }
        ori = RIGHT;
    }
    @Override
    public void draw(Graphics2D g2d) {
        switch (ori) {
            case LEFT:
            case UP:
                img = imgsLeft[indexImage];
                break;
            case RIGHT:
            case DOWN:
                img = imgsRight[indexImage];
                break;
            default:
                break;
        }
        super.draw(g2d);
    }

    public void changeImage(long currentTime) {

        if (currentTime % 50 != 0) {
            return;
        }
        indexImage = (indexImage + 1) % 5;
    }

    public void keyPressed(int keyCode) {
        if (isLeft || isRight || isUp || isDown ){
            return;
        }
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                isLeft = true;
                destinationPosition = x-sizePad;
                ori = LEFT;
                break;
            case KeyEvent.VK_UP:
                isUp = true;
                destinationPosition = y-sizePad;
                break;
            case KeyEvent.VK_RIGHT:
                ori =RIGHT;
                isRight = true;
                destinationPosition = x+sizePad;
                break;
            case KeyEvent.VK_DOWN:
                isDown = true;
                destinationPosition = y+sizePad;
                break;
        }
    }

    public void keyRelease(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                break;
            case KeyEvent.VK_UP:
                break;
            case KeyEvent.VK_RIGHT:
                break;
            case KeyEvent.VK_DOWN:
                break;
        }
    }
    public void move() {
        if (isLeft) {
            if (x>destinationPosition){
                x -= delta;
            }else {
                isLeft = false;
            }
            return;
        }
        if (isUp) {
            if (y>destinationPosition){
                y -= delta;
            }else {
                isUp = false;
            }
            return;
        }
        if (isRight) {
            if (x<destinationPosition){
                x += delta;
            }else {
                isRight = false;
            }
            return;
        }
        if (isDown) {
            if (y<destinationPosition){
                y += delta;
            }else {
                isDown = false;
            }
            return;
        }
    }

    public void interactPadding() {
        Rectangle recPlayer = this.getBound();
        Rectangle reLeftPadding = new Rectangle(0, 0, PADDING, H_PLAY);
        Rectangle reTopPadding = new Rectangle(0, 0, W_F, PADDING);
        Rectangle reRightPadding = new Rectangle(W_F - PADDING, 0, PADDING, H_PLAY);
        Rectangle reBotPadding = new Rectangle(0, H_PLAY - PADDING, W_F, PADDING);
        boolean leftCollision = recPlayer.intersects(reLeftPadding);
        boolean topCollision = recPlayer.intersects(reTopPadding);
        boolean rightCollision = recPlayer.intersects(reRightPadding);
        boolean botCollision = recPlayer.intersects(reBotPadding);
        if (leftCollision) {
            x += delta;
            isLeft = false;
        }
        if (topCollision) {
            y += delta;
            isUp = false;
        }
        if (rightCollision) {
            x -= delta;
            isRight = false;
        }
        if (botCollision) {
            y -= delta;
            isDown = false;
        }
    }


}