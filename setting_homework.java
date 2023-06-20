package com.sxxt.Java面对对象程序设计课设;

import javax.swing.JFrame;

public class setting_homework {
    public static void main(String[] args) {
        Myframe frame = new Myframe("绘图软件");
        frame.setSize(900, 900);
        frame.setTitle("绘图软件");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
