package com.t3h.mygame;
import com.t3h.mygame.object.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class ManagerPlayer implements Constants{
    private IPlay myAction;
    private ArrayList<Coin> coins;
    private boolean isPlay;
    private Pad[][] pads;
    private Gun[][] guns;
    private Player  player;
    private Coin    coin;
    private int delayBossMove = 10;
    private int bossLocation = 0;
    private int highScore = 0;
    private int sumBoss = 0;
    private int score = 0;
    private int level;
    private int itemSize;
    private int mapSize;
    private int padSize;
    private long lastBossBorn;
    private long lastBossMove;
    private long lastCoinBorn;
    private SoundManager soundPlay;
    private SoundManager eatCoin;
    private Image imgPad;
    public ManagerPlayer(int mapSize, int level) {
//        eatCoin = new SoundManager("/Sound/eatCoin.wav");
        imgPad = new ImageIcon(getClass().getResource("/imgs/imgPad.png")).getImage();
        this.mapSize = mapSize;
        this.level = level;
        padSize = (W_F-(2*PADDING))/ this.mapSize;
        itemSize = (2* padSize)/3;
        pads    = new Pad[this.mapSize][this.mapSize];
        guns    = new Gun[4][this.mapSize];
        coins   = new ArrayList<>();
        isPlay = true;
        initMap();
        initGun();
        initPlayer();
    }
    public void initMap(){
        for(int i = 0; i< mapSize; i++){
            for(int j = 0; j< mapSize; j++){
                Pad pad = new Pad();
                pad.setW(padSize);
                pad.setH(padSize);
                pad.setX(PADDING + (j* padSize));
                pad.setY(PADDING + (i* padSize));
                pad.setImg(imgPad);
                pads[i][j] = pad;
            }
        }
    }
    public void initGun(){
        for(int i=0;i<4;i++){
            for(int j = 0; j< mapSize; j++) {
                Gun gun = new Gun(itemSize);
                gun.setW(itemSize);
                gun.setH(itemSize);
                switch (i){
                    case 0:
                        gun.setOri(Object2D.RIGHT);
                        gun.setX(0);
                        gun.setY(PADDING + j * padSize + (padSize / 2 - itemSize / 2));
                        guns[Gun.THE_LEFT_IN_SCR][j] = gun;
                        break;
                    case 1:
                        gun.setOri(Object2D.DOWN);
                        gun.setX(PADDING + j * padSize + (padSize / 2 - itemSize / 2));
                        gun.setY(0);
                        guns[Gun.THE_TOP_IN_SCR][j] = gun;
                        break;
                    case 2:
                        gun.setOri(Object2D.LEFT);
                        gun.setX(W_F- itemSize);
                        gun.setY(PADDING + j * padSize + (padSize / 2 - itemSize / 2));
                        guns[Gun.THE_RIGHT_IN_SCR][j] = gun;
                        break;
                    case 3:
                        gun.setOri(Object2D.UP);
                        gun.setX(PADDING + j * padSize + (padSize / 2 - itemSize / 2));
                        gun.setY(H_PLAY- itemSize);
                        guns[Gun.THE_BOT_IN_SCR][j] = gun;
                        break;
                    default:break;
                }
            }
        }
    }
    public void initPlayer(){
        player = new Player(padSize);
        player.setX(PADDING);
        player.setY(PADDING);
        player.setW(padSize);
        player.setH(padSize);
    }
    public void initCoin(long currentTime){
        if(getSumBoss() <1){
            return;
        }
        if(currentTime - lastCoinBorn < 100){
            return;
        }
        lastCoinBorn = currentTime;
        if(coins.size()<1) {
            lastCoinBorn = currentTime;
            Random rd = new Random();
            coin = new Coin();
            coin.setH(padSize - 10);
            coin.setW(padSize - 10);
            int xTemp,yTemp;
            do {
                xTemp = rd.nextInt(mapSize) * padSize + PADDING + 5;
            }
            while (xTemp > player.getX()-30 && xTemp < player.getX() + 30);
            do {
                yTemp = rd.nextInt(mapSize) * padSize + PADDING + 5;
            }
            while (yTemp > player.getY()-30 && yTemp < player.getY() + 30);
            coin.setX(xTemp);
            coin.setY(yTemp);
            coin.setImg(new ImageIcon(getClass().getResource("/imgs/coin.png")).getImage());
            coins.add(coin);
        }
    }
    public boolean isNearLeft(){
        if (player.getX()>W_F/2){
            return false;
        }
        return true;
    }
    public boolean isNearTop(){
        if (player.getY()>H_PLAY/2){
            return false;
        }
        return true;
    }
    public int getGunSameRow(){
        return (player.getY()-PADDING)/ padSize;
    }
    public int getGunSameColumn() {
        return (player.getX() - PADDING) / padSize;
    }
    public int getSumBoss(){
        int temp = 0;
        for (int i = 0;i<4;i++){
            for(int j = 0; j<4; j++){
                temp += guns[i][j].getBossesSize();
            }
        }
        sumBoss = temp;
        return sumBoss;
    }
    public void bornAllBoss(long currentTime) {
        Random rd = new Random();
        if (currentTime - lastBossBorn < caculateDelayBossBorn()) {
            return;
        }
        lastBossBorn = currentTime;
        if(level == 1) {
                bossLocation++;
                int b = rd.nextInt(4);
                guns[bossLocation %4][b].bornBoss();
        }
        if(level == 2){
            bossLocation++;
            if (bossLocation %4 == 0) {
                if (isNearLeft()) {
                    guns[Gun.THE_LEFT_IN_SCR][getGunSameRow()].bornBoss();
                }
                else {
                    guns[Gun.THE_LEFT_IN_SCR][rd.nextInt(4)].bornBoss();
                }
            }
            if (bossLocation %4 == 1) {
                guns[Gun.THE_TOP_IN_SCR][rd.nextInt(4)].bornBoss();
            }
            if (bossLocation %4 == 2) {
                if (!isNearLeft()) {
                    guns[Gun.THE_RIGHT_IN_SCR][getGunSameRow()].bornBoss();
                }
                else {
                    guns[Gun.THE_RIGHT_IN_SCR][rd.nextInt(4)].bornBoss();
                }
            }
            if(bossLocation %4 == 3) {
                guns[Gun.THE_BOT_IN_SCR][rd.nextInt(4)].bornBoss();
            }
        }
        if(level == 3){
            bossLocation++;
            if (bossLocation %4 == 0) {
                if (isNearLeft()) {
                    guns[Gun.THE_LEFT_IN_SCR][getGunSameRow()].bornBoss();
                }
                else {
                    guns[Gun.THE_LEFT_IN_SCR][rd.nextInt(4)].bornBoss();
                }
            }
            if (bossLocation %4 == 1) {
                if (isNearTop()) {
                    guns[Gun.THE_TOP_IN_SCR][getGunSameColumn()].bornBoss();
                }
                else {
                    guns[Gun.THE_TOP_IN_SCR][rd.nextInt(4)].bornBoss();
                }
            }
            if (bossLocation %4 == 2) {
                if (!isNearLeft()) {
                    guns[Gun.THE_RIGHT_IN_SCR][getGunSameRow()].bornBoss();
                }
                else {
                    guns[Gun.THE_RIGHT_IN_SCR][rd.nextInt(4)].bornBoss();
                }
            }
            if(bossLocation %4 == 3) {
                if (!isNearTop()) {
                    guns[Gun.THE_BOT_IN_SCR][getGunSameColumn()].bornBoss();
                } else {
                    guns[Gun.THE_BOT_IN_SCR][rd.nextInt(4)].bornBoss();
                }
            }
        }
    }
    public void moveAllBoss(long currentTime){
        if (currentTime - lastBossMove < delayBossMove) {
            return;
        }
        lastBossMove = currentTime;
        for(int i=0;i<4;i++){
            for(int j = 0; j< mapSize; j++){
                guns[i][j].moveAllBoss();
            }
        }
    }
    public int caculateDelayBossBorn(){
        switch (mapSize){
            case 4:
                return (delayBossMove*100+400);
            case 5:
                return (delayBossMove*100+300);
            case 6:
                return (delayBossMove*100+200);
        }
        return 0;
    }
    public void changeDelay(long currentTime) {
        switch (level) {
            case 1:
                if(delayBossMove > 9){
                    delayBossMove -= 1;
                }
                switch (mapSize) {
                    case 4:
                    case 5:
                        if (delayBossMove < 5) {
                            return;
                        }
                    case 6:
                        if (delayBossMove < 4) {
                            return;
                        }
                }
                break;
            case 2:
                if(delayBossMove > 7){
                    delayBossMove -= 1;
                }
                switch (mapSize) {
                    case 4:
                    case 5:
                        if (delayBossMove < 4) {
                            return;
                        }
                    case 6:
                        if (delayBossMove < 3) {
                            return;
                        }
                }
                break;
            case 3:
                if(delayBossMove > 5){
                    delayBossMove -= 1;
                }
                switch (mapSize) {
                    case 4:
                    case 5:
                    case 6:
                        if (delayBossMove < 3) {
                            return;
                        }
                }
                break;
        }
        if (currentTime % 15000 != 0) {
            return;
        }
        delayBossMove -= 1;
    }
    public void initScore(Graphics2D g2d){
        try {
            BufferedReader bf = new BufferedReader(
                    new InputStreamReader(
                            getClass().getResource("/highScore.txt")
                                    .openStream()
                    )
            );
            String line = bf.readLine();
            if(line != null) {
                highScore = Integer.parseInt(line);
            }
            if(score > highScore){
                highScore = score;
                String path = "highScore.txt";
                OutputStream outputStream = new FileOutputStream(path);
                Writer outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write(highScore+"");
                outputStreamWriter.close();
            }
            bf.close();
            g2d.drawString("SCORE : "+score,75,30);
            g2d.drawString("HIGH SCORE : "+highScore,650,30);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void DrawAll(Graphics2D g2d){
        for(int i = 0; i< mapSize; i++){
            for(int j = 0; j< mapSize; j++){
            pads[i][j].draw(g2d);
            }
        }
        for (int i=0;i<4;i++){
            for (int j = 0; j< mapSize; j++){
                guns[i][j].draw(g2d);
            }
        }
        player.draw(g2d);
        for(int i = 0; i< coins.size(); i++){
            coins.get(i).draw(g2d);
        }
        initScore(g2d);
    }
    public void removeCoin(){
        for(int i = 0; i< coins.size(); i++){
            if (coins.get(i).getBound().intersects(player.getBound())){
//                eatCoin.play(false);
                score += 1;
                coins.remove(coins.get(i));
                System.out.println("score : "+score);
                continue;
            }
        }
    }
    public void interactPlayerVsBoss(){
        if(!isPlay){
            return;
        }
        for(int i = 0;i<4;i++){
            for(int j = 0; j < mapSize; j++){
                for (int k = 0 ; k < guns[i][j].getBossesSize() ;k++){
                    if (guns[i][j].getBossBound(k).intersects(player.getBound())){
                        isPlay = false;
                        myAction.showEnd();
                        break;
                    }
                }
            }
        }
    }
    public void keyPressed(int keyCode) {
        player.keyPressed(keyCode);
    }
    public void keyRelease(int keyCode){
        player.keyRelease(keyCode);
    }
    public void stepTheard(long currentTime) {
        changeDelay(currentTime);
        player.changeImage(currentTime);
        player.move();
        player.interactPadding();
        bornAllBoss(currentTime);
        moveAllBoss(currentTime);
        changeImageAllBoss(currentTime);
        initCoin(currentTime);
        removeCoin();
        interactPlayerVsBoss();
    }

    private void changeImageAllBoss(long currentTime) {
        for (int i = 0;i<4;i++){
            for(int j = 0; j<4; j++){
                guns[i][j].changeImageAllBoss(currentTime);
            }
        }
    }
    public void setAction(IPlay action){
        myAction = action;
    }
}
