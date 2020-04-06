package com.t3h.mygame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Utils {
    public static Image getImage(String path,int w,int h){
        try {
            return ImageIO.read(Utils.class.getResource(path)).getScaledInstance(w,h,Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
