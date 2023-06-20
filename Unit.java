package com.sxxt.Java面对对象程序设计课设;

import java.awt.Component;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

public class Unit {
    // 提供截屏操作
    public static void snapshot(Component comp, File file){
        int width = comp.getWidth();
        int height = comp.getHeight();
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = image.getGraphics();
        comp.paint(g);
        try{
            ImageIO.write(image, "jpg", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}