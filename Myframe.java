package com.sxxt.Java面对对象程序设计课设;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.BasicStroke;


public class Myframe extends JFrame{
    Graphics2D g;
    float width = 2.0f;
    int cx,cy;
    File filePath;
    BufferedImage bufferedImage = null;
    public Color scopeColor = Color.black;//初始化颜色
    private boolean pressed = false;
    private boolean upper = false;
    private boolean moved = false;
    private List<Curve> curveList = new ArrayList<>();
    JButton saveButton = new JButton("保存");
    JButton openButton = new JButton("导入");
    HandDraw panel = new HandDraw();
    JPanel topPanel = new JPanel(); 
    JButton jb7 = new JButton("实圆");
    JButton jb8 = new JButton("实方");
    JButton jb1 = new JButton("矩形");
    JButton jb2 = new JButton("圆形");
    JButton jb3 = new JButton("颜色");
    JButton jb4 = new JButton("直线");
    JButton jb5 = new JButton("曲线");
    JButton jb6 = new JButton("橡皮擦");
    JLabel label = new JLabel("线宽");
    JComboBox<Float> box = new JComboBox<>();
    private String drawFlag = "曲线";
    BasicStroke stokeLine;
    JPopupMenu popmenu = new JPopupMenu();
    JMenuItem a1 = new JMenuItem("清除");
    JMenuItem a2 = new JMenuItem("90°翻转"); 
    Myframe(String Title){
        super(Title);
        JPanel jp = new JPanel();
        this.setContentPane(jp);
        popmenu.add(a1);
        popmenu.add(a2);
        jp.setLayout(new BorderLayout());
        jp.add(topPanel, BorderLayout.NORTH);
        jp.add(panel, BorderLayout.CENTER);
        // System.out.println(topPanel.getWidth()+" "+topPanel.getHeight());
        for(int i = 1;i<=40;i+=1) box.addItem((float) i);
        box.setSelectedIndex(1);
        box.setOpaque(false);   
        box.setBorder(null);
        box.setFocusable(false);
        topPanel.add(jb7);
        topPanel.add(jb8);
        topPanel.add(jb1);
        topPanel.add(jb2);
        topPanel.add(jb3);
        topPanel.add(jb4);
        topPanel.add(jb5);
        topPanel.add(jb6);
        topPanel.add(saveButton);
        topPanel.add(openButton);
        topPanel.add(box);
        topPanel.add(label);
        this.set_backround(jb1, new ImageIcon("src\\com\\sxxt\\Java面对对象程序设计课设\\24gl-rectangle.png"));
        this.set_backround(jb2, new ImageIcon("src\\com\\sxxt\\Java面对对象程序设计课设\\24gl-circle.png"));
        this.set_backround(jb3, new ImageIcon("src\\com\\sxxt\\Java面对对象程序设计课设\\颜色库.png"));
        this.set_backround(jb4, new ImageIcon("src\\com\\sxxt\\Java面对对象程序设计课设\\直线.png"));
        this.set_backround(jb5, new ImageIcon("src\\com\\sxxt\\Java面对对象程序设计课设\\编辑.png"));
        this.set_backround(jb6, new ImageIcon("src\\com\\sxxt\\Java面对对象程序设计课设\\橡皮,擦除,橡皮擦.png"));
        this.set_backround(jb7, new ImageIcon("src\\com\\sxxt\\Java面对对象程序设计课设\\24gf-circle.png"));
        this.set_backround(jb8, new ImageIcon("src\\com\\sxxt\\Java面对对象程序设计课设\\tx-fill-长方形.png"));
        this.set_backround(saveButton, new ImageIcon("src\\com\\sxxt\\Java面对对象程序设计课设\\保存.png"));
        this.set_backround(openButton, new ImageIcon("src\\com\\sxxt\\Java面对对象程序设计课设\\导入.png"));
        // this.set_backround(jb1, null);
        panel.setPreferredSize(new Dimension(500, 400));
        Border border = BorderFactory.createLineBorder(Color.black, 2);
        panel.setBorder(border);
        panel.setLocation(EXIT_ON_CLOSE, ABORT);
        panel.setPreferredSize(getPreferredSize());
        this.box.addActionListener((e)->{
            width = (float) box.getSelectedItem();
            Curve curve = curveList.get(curveList.size()-1);
            Point p2 = curve.points.get(curve.points.size()-1);
            p2.W = width;
        });
        this.openButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    doOpen();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            
        });
        this.a1.addActionListener( new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(curveList.size()>=1)
                curveList.remove(curveList.size()-1);
            }
            
        });
        this.a2.addActionListener( new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Curve curve = curveList.get(curveList.size()-1);
                Point p1 = curve.points.get(0);
                Point p2 = curve.points.get(curve.points.size()-1);
                System.out.println(p1.getX1()+"  "+p2.getX2()+" "+p1.getY1()+"  "+p2.getY2());
                curve.points.set(0, new Point((p1.getX1()+p2.getX2()-p2.getY2()+p1.getY1())/2,(p1.getY1()+p2.getY2()-p2.getX2()+p1.getX1())/2,(p1.getX1()+p2.getX2()-p2.getY2()+p1.getY1())/2,(p1.getY1()+p2.getY2()-p2.getX2()+p1.getX1())/2,p1.getColor(),p1.name,p1.W));
                curve.points.add(new Point((p1.getX1()-p1.getY1()+p2.getX2()+p2.getY2())/2,(p1.getY1()+p2.getY2()+p2.getX2()-p1.getX1())/2,(p1.getX1()-p1.getY1()+p2.getX2()+p2.getY2())/2,(p1.getY1()+p2.getY2()+p2.getX2()-p1.getX1())/2,p2.getColor(),p2.name,p2.W));
                System.out.println(curve.points.get(0).getX1()+" "+curve.points.get(curve.points.size()-1).getX2()+" "+curve.points.get(0).getY1()+" "+curve.points.get(curve.points.size()-1).getY2());
                paint(g);
            }
        });
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                doSave();
            }  
        });
        this.jb1.addMouseListener(new JBMouseListener());
        this.jb2.addMouseListener(new JBMouseListener());
        this.jb3.addMouseListener(new JBMouseListener());
        this.jb4.addMouseListener(new JBMouseListener());
        this.jb5.addMouseListener(new JBMouseListener());
        this.jb6.addMouseListener(new JBMouseListener());
        this.jb7.addMouseListener(new JBMouseListener());
        this.jb8.addMouseListener(new JBMouseListener());
        this.saveButton.addMouseListener(new JBMouseListener());
        this.openButton.addMouseListener(new JBMouseListener());
    }
    //文件打开
    private void doOpen() throws IOException{
        // JFileChooser fileChooser = new JFileChooser("D:\\");
        // fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // int returnVal = fileChooser.showOpenDialog(fileChooser);
        // if(returnVal == JFileChooser.APPROVE_OPTION){       
        // filePath= fileChooser.getSelectedFile().getAbsolutePath();}
        // System.out.println(filePath);
        // bufferedImage = ImageIO.read(new File(filePath));
        JFileChooser chooser = new JFileChooser();
        FileSystemView fsv = FileSystemView.getFileSystemView(); //注意了，这里重要的一句
        //System.out.println(fsv.getHomeDirectory()); //得到桌面路径
        chooser.setCurrentDirectory(fsv.getHomeDirectory());//初始界面为桌面

        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);//用来选择文件
        FileNameExtensionFilter filter = new FileNameExtensionFilter(//设置选择的文件类型
        "png", "jpg", "gif","xlsx");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(chooser);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            filePath = chooser.getSelectedFile();}
        bufferedImage = ImageIO.read(filePath);
        panel.clear();
        // System.out.println(filePath.getPath());
    }
    //文件保存
    private void doSave(){
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("图片文件","jpg");
        chooser.setFileFilter(filter);
        int ret = chooser.showOpenDialog(this);
        if(ret==JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            Unit.snapshot(panel, file);
            panel.clear();
        }
        
    }
    public class HandDraw extends JPanel{
        
        HandDraw(){
            this.setBackground(Color.white);
            MyMouseListener l = new MyMouseListener();
            this.addMouseListener(l);
            this.addMouseMotionListener(l);
        }
        public void clear(){
            curveList.clear();
            paint(g);
            repaint();
        }
        @Override
        protected void paintComponent(Graphics g1) {
            super.paintComponent(g1);
            g = (Graphics2D) g1;
            int width  = this.getWidth();
            int height = this.getHeight();
            g.setColor(Color.black);
            g.drawRect(0, 0, width, height-1); 
            if(bufferedImage != null){
                int w = bufferedImage.getWidth();
                int h = bufferedImage.getHeight();
                // g.drawImage(bufferedImage,);
                Image img = bufferedImage.getScaledInstance(width , height, Image.SCALE_DEFAULT);
                int imgW = bufferedImage.getWidth(null);
                int imgH = bufferedImage.getHeight(null);
                int fitW =w;
                int fitH = w*imgH/imgW;
                if(fitH>fitW){
                    fitH = h;
                    fitW = h*imgW/imgH;
                }
                int fitX = (w-fitW)/2;
                int fitY = (height-fitH)/2;
                g.drawImage(img, fitX,fitY,fitW,fitH,null);//,w/2,h/2
                repaint();
            }
            // if(drawFlag.equals("保存")){
            //     paint(g);
            // }
            for(Curve curve:curveList){
                if(curve.points.size()>1){
                    if(curve.points.get(0).name.equals("曲线")){
                        for(int i = 1;i<curve.points.size();i++){
                            Point p2 = curve.points.get(i);
                            g.setColor(p2.getColor());
                            stokeLine =  new BasicStroke(p2.W);
                            Graphics2D g2 = (Graphics2D) g;
                            g2.setStroke(stokeLine);
                            g2.drawLine(p2.getX1(), p2.getY1(), p2.getX2(),p2.getY2());
                        }
                    }else if(curve.points.get(0).name.equals("矩形")){
                        Point p1 = curve.points.get(0);
                        Point p2 = curve.points.get(curve.points.size()-1);
                        g.setColor(p2.getColor());
                        stokeLine =  new BasicStroke(p2.W);
                        // Graphics2D g2 = (Graphics2D) g;
                        g.setStroke(stokeLine);
                        if(p2.getX2()>p1.getX1() && p2.getY2()>p1.getY1()){
                            g.drawRect(p1.getX1(), p1.getY1(), Math.abs(p2.getX2()-p1.getX1()), Math.abs(p2.getY2()-p1.getY2()));
                        }else if(p1.getX1()>p2.getX2() && p2.getY2()>p1.getY1()){
                            g.drawRect(p2.getX2(),p1.getY1(),Math.abs(p2.getX2()-p1.getX1()),Math.abs(p2.getY2()-p1.getY2()));
                        }else if(p1.getX1()<p2.getX2() && p2.getY2()<p1.getY1()){
                            g.drawRect(p1.getX1(),p2.getY2(),Math.abs(p2.getX2()-p1.getX1()),Math.abs(p2.getY2()-p1.getY2()));
                        }else if(p1.getX1()>p2.getX2() && p2.getY2()<p1.getY1()){
                            g.drawRect(p2.getX2(),p2.getY2(),Math.abs(p2.getX2()-p1.getX1()),Math.abs(p2.getY2()-p1.getY2()));
                        }
                        repaint();
                    }else if(curve.points.get(0).name.equals("直线")){
                        Point p1 = curve.points.get(0);
                        Point p2 = curve.points.get(curve.points.size()-1);
                        g.setColor(p2.getColor());
                        stokeLine =  new BasicStroke(p2.W);
                        g.setStroke(stokeLine);
                        g.drawLine(p1.getX1(), p1.getY1(), p2.getX2(), p2.getY2());
                        repaint();
                    }else if(curve.points.get(0).name.equals("圆形")){
                        Point p1 = curve.points.get(0);
                        Point p2 = curve.points.get(curve.points.size()-1);
                        g.setColor(p2.getColor());
                        stokeLine =  new BasicStroke(p2.W);
                        // Graphics2D g2 = (Graphics2D) g;
                        g.setStroke(stokeLine);
                        if(p2.getX2()>p1.getX1() && p2.getY2()>p1.getY1()){
                            g.drawOval(p1.getX1(), p1.getY1(), p2.getX2()-p1.getX1(), p2.getY2()-p1.getY2());
                        }else if(p1.getX1()>p2.getX2() && p2.getY2()>p1.getY1()){
                            g.drawOval(p2.getX2(),p1.getY1(),Math.abs(p2.getX2()-p1.getX1()),Math.abs(p2.getY2()-p1.getY2()));
                        }else if(p1.getX1()<p2.getX2() && p2.getY2()<p1.getY1()){
                            g.drawOval(p1.getX1(),p2.getY2(),Math.abs(p2.getX2()-p1.getX1()),Math.abs(p2.getY2()-p1.getY2()));
                        }else if(p1.getX1()>p2.getX2() && p2.getY2()<p1.getY1()){
                            g.drawOval(p2.getX2(),p2.getY2(),Math.abs(p2.getX2()-p1.getX1()),Math.abs(p2.getY2()-p1.getY2()));
                        }
                        repaint();
                    }else if(curve.points.get(0).name.equals("实圆")){
                        Point p1 = curve.points.get(0);
                        Point p2 = curve.points.get(curve.points.size()-1);
                        g.setColor(p2.getColor());
                        stokeLine =  new BasicStroke(p2.W);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(stokeLine);
                        if(p2.getX2()>p1.getX1() && p2.getY2()>p1.getY1()){
                            g2.fillOval(p1.getX1(), p1.getY1(), p2.getX2()-p1.getX1(), p2.getY2()-p1.getY2());
                        }else if(p1.getX1()>p2.getX2() && p2.getY2()>p1.getY1()){
                            g2.fillOval(p2.getX2(),p1.getY1(),Math.abs(p2.getX2()-p1.getX1()),Math.abs(p2.getY2()-p1.getY2()));
                        }else if(p1.getX1()<p2.getX2() && p2.getY2()<p1.getY1()){
                            g2.fillOval(p1.getX1(),p2.getY2(),Math.abs(p2.getX2()-p1.getX1()),Math.abs(p2.getY2()-p1.getY2()));
                        }else if(p1.getX1()>p2.getX2() && p2.getY2()<p1.getY1()){
                            g2.fillOval(p2.getX2(),p2.getY2(),Math.abs(p2.getX2()-p1.getX1()),Math.abs(p2.getY2()-p1.getY2()));
                        }
                        repaint();
                    }else if(curve.points.get(0).name.equals("实方")){
                        Point p1 = curve.points.get(0);
                        Point p2 = curve.points.get(curve.points.size()-1);
                        g.setColor(p2.getColor());
                        stokeLine =  new BasicStroke(p2.W);
                        // Graphics2D g2 = (Graphics2D) g;
                        g.setStroke(stokeLine);
                        if(p2.getX2()>p1.getX1() && p2.getY2()>p1.getY1()){
                            g.fillRect(p1.getX1(), p1.getY1(), p2.getX2()-p1.getX1(), p2.getY2()-p1.getY2());
                        }else if(p1.getX1()>p2.getX2() && p2.getY2()>p1.getY1()){
                            g.fillRect(p2.getX2(),p1.getY1(),Math.abs(p2.getX2()-p1.getX1()),Math.abs(p2.getY2()-p1.getY2()));
                        }else if(p1.getX1()<p2.getX2() && p2.getY2()<p1.getY1()){
                            g.fillRect(p1.getX1(),p2.getY2(),Math.abs(p2.getX2()-p1.getX1()),Math.abs(p2.getY2()-p1.getY2()));
                        }else if(p1.getX1()>p2.getX2() && p2.getY2()<p1.getY1()){
                            g.fillRect(p2.getX2(),p2.getY2(),Math.abs(p2.getX2()-p1.getX1()),Math.abs(p2.getY2()-p1.getY2()));
                        }
                        repaint();
                    }else if(curve.points.get(0).name.equals("橡皮")){
                        for(int i = 1;i<curve.points.size();i++){
                            Point p2 = curve.points.get(i);
                            g.setColor(Color.white);
                            stokeLine =  new BasicStroke(p2.W);
                            g.setStroke(stokeLine);
                            g.drawLine(p2.getX1(), p2.getY1(), p2.getX2(),p2.getY2());
                        }
                        repaint();
                    }
                } 
            }
            if(curveList.size()>=1){
                Curve curve = curveList.get(curveList.size()-1);
                Point p1 = curve.points.get(0);
                Point p2 = curve.points.get(curve.points.size()-1);
                if(!(p1.name.equals("曲线") || p1.name.equals("直线") || p1.name.equals("橡皮"))){
                    Stroke dashed = new BasicStroke(0.1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2}, 0);
                    g.setStroke(dashed);
                    if(p1.name.equals("矩形") || p1.name.equals("实方"))g.setColor(Color.WHITE);
                    else g.setColor(Color.BLACK);
                    if(p2.getX2()>p1.getX1() && p2.getY2()>p1.getY1()){
                        g.drawRect(p1.getX1(), p1.getY1(), p2.getX2()-p1.getX1(), p2.getY2()-p1.getY2());
                        g.fillRect(p2.getX2()-(int)p2.W/2,p2.getY2()-(int)p2.W/2,(int)p2.W-1,(int)p2.W-1);
                    }else if(p1.getX1()>p2.getX2() && p2.getY2()>p1.getY1()){
                        g.drawRect(p2.getX2(),p1.getY1(),Math.abs(p2.getX2()-p1.getX1()),Math.abs(p2.getY2()-p1.getY2()));
                        g.fillRect(p1.getX1()-(int)p2.W/2,p2.getY2()-(int)p2.W/2,(int)p2.W-1,(int)p2.W-1);
                    }else if(p1.getX1()<p2.getX2() && p2.getY2()<p1.getY1()){
                        g.drawRect(p1.getX1(),p2.getY2(),Math.abs(p2.getX2()-p1.getX1()),Math.abs(p2.getY2()-p1.getY2()));
                        g.fillRect(p2.getX2()-(int)p2.W/2,p1.getY1()-(int)p2.W/2,(int)p2.W-1,(int)p2.W-1);
                    }else if(p1.getX1()>p2.getX2() && p2.getY2()<p1.getY1()){
                        g.drawRect(p2.getX2(),p2.getY2(),Math.abs(p2.getX2()-p1.getX1()),Math.abs(p2.getY2()-p1.getY2()));
                        g.fillRect(p1.getX1()-(int)p2.W/2,p1.getY1()-(int)p2.W/2,(int)p2.W-1,(int)p2.W-1);
                    }
                }  
            }  
        }
        public class MyMouseListener extends MouseAdapter{
            @Override
            public void mouseDragged(MouseEvent e) {
                if(pressed){
                    // if(e.getButton()==MouseEvent.BUTTON1){
                        Curve curve = curveList.get(curveList.size()-1);
                        if(moved){
                            Point p1 = curve.points.get(0);
                            Point p2 = curve.points.get(curve.points.size()-1);
                            p1.setX1(p1.getX1()+e.getX()-cx);
                            p1.setY1(p1.getY1()+e.getY()-cy);
                            p2.setX2(p2.getX2()+e.getX()-cx);
                            p2.setY2(p2.getY2()+e.getY()-cy);
                            p1.setX2(p1.getX2()+e.getX()-cx);
                            p1.setY2(p1.getY2()+e.getY()-cy);
                            p2.setX1(p2.getX1()+e.getX()-cx);
                            p2.setY1(p2.getY1()+e.getY()-cy);
                            p2.setColor(scopeColor);
                            p2.W = width;

                        }else{
                            curve.points.add(new Point(cx,cy,e.getX(),e.getY(),scopeColor,drawFlag,width));  
                        }
                        cx = e.getX();
                        cy = e.getY(); repaint(); 
                    }
                // }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                cx = e.getX();
                cy = e.getY();
                if(e.getButton() == MouseEvent.BUTTON1) pressed = true;
                if (curveList.size() >=1){
                    Curve curve_end = curveList.get(curveList.size()-1);
                    Point p1 = curve_end.points.get(0);
                    Point p2 = curve_end.points.get(curve_end.points.size()-1);
                    if(p2.getX2()>p1.getX1() && p2.getY2()>p1.getY1()){
                        if(p2.getX2()-10<cx && cy>p2.getY2()-10 && cx<p2.getX2()+10 && cy<p2.getY2()+10){
                            upper = true;
                        }
                        if(p2.getX2()>cx && cx>p1.getX1() && p2.getY2()>cy && cy>p1.getY1()){
                            if(e.getButton() == MouseEvent.BUTTON1){
                                moved = true;
                                p2.setColor(scopeColor);
                                p2.W = width;
                                repaint();
                            }else if(e.getButton() == MouseEvent.BUTTON2){
                            }else if(e.getButton() == MouseEvent.BUTTON3){
                                popmenu.show(e.getComponent(), cx,cy);
                            }
                            
                        }
                    }else if(p1.getX1()>p2.getX2() && p2.getY2()>p1.getY1()){
                        if(p1.getX1()-10<cx && cy>p2.getY2()-10 && cx<p1.getX1()+10 && cy<p2.getY2()+10){
                            upper = true;
                        }
                        if(p1.getX1()>cx && cx>p2.getX2() && p2.getY2()>cy && cy>p1.getY1()){
                            if(e.getButton() == MouseEvent.BUTTON1){
                                moved = true;
                                p2.setColor(scopeColor);
                                p2.W = width;
                                repaint();
                            }else if(e.getButton() == MouseEvent.BUTTON2){
                            }else if(e.getButton() == MouseEvent.BUTTON3){
                                popmenu.show(e.getComponent(), cx,cy);
                            }
                        }
                    }else if(p1.getX1()<p2.getX2() && p2.getY2()<p1.getY1()){
                        if(p2.getX2()-10<cx && cy>p1.getY1()-10 && cx<p2.getX2()+10 && cy<p1.getY1()+10){
                            upper = true;
                        }
                        if(p2.getX2()>cx && cx>p1.getX1() && p2.getY2()<cy && cy<p1.getY1()){
                            if(e.getButton() == MouseEvent.BUTTON1){
                                moved = true;
                                p2.setColor(scopeColor);
                                p2.W = width;
                                repaint();
                            }else if(e.getButton() == MouseEvent.BUTTON2){
                            }else if(e.getButton() == MouseEvent.BUTTON3){
                                popmenu.show(e.getComponent(), cx,cy);
                            }
                        }
                    }else if(p1.getX1()>p2.getX2() && p2.getY2()<p1.getY1()){
                        if(p1.getX1()-10<cx && cy>p1.getY1()-10 && cx<p1.getX1()+10 && cy<p1.getY1()+10){
                            upper = true;
                        }
                        if(p2.getX2()<cx && cx<p1.getX1() && p2.getY2()<cy && cy<p1.getY1()){
                            if(e.getButton() == MouseEvent.BUTTON1){
                                moved = true;
                                p2.setColor(scopeColor);
                                p2.W = width;
                                repaint();
                            }else if(e.getButton() == MouseEvent.BUTTON2){
                            }else if(e.getButton() == MouseEvent.BUTTON3){
                                popmenu.show(e.getComponent(), cx,cy);
                            }
                        }
                    }
                }
                
                if(!upper && !moved && pressed){   
                    Curve curve = new Curve();
                    curveList.add(curve);
                    curve.points.add(new Point(cx,cy,cx,cy,scopeColor,drawFlag,width));
                }
                repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                // System.out.println(e.getComponent());
                // Curve curve = curveList.get(curveList.size()-1);
                // curve.points.add(new Point(cx,cy,e.getX(),e.getY(),scopeColor,drawFlag,width));
                pressed = false;
                upper = false;
                moved = false;
                repaint();
            }
        }   
    }
    public static class Curve{
        public List<Point> points = new ArrayList<>();
    }
    public class JBMouseListener extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getSource()==jb1) {
                drawFlag = "矩形";
                // System.out.println(drawFlag);
            }else if(e.getSource()==jb2) {
                drawFlag = "圆形";
                // System.out.println(drawFlag);
            }else if(e.getSource()==jb3) {
                // drawFlag = "颜色";
                // System.out.println(drawFlag);
                Color color = JColorChooser.showDialog(null, "选择字体颜色", null);
                scopeColor = color;
                Curve curve = curveList.get(curveList.size()-1);
                Point p2 = curve.points.get(curve.points.size()-1);
                p2.setColor(color);
    			// System.out.println(scopeColor);
                // System.out.println(Color.BLACK);
            }else if(e.getSource()==jb4) {
                drawFlag = "直线";
                // System.out.println(drawFlag);
            }else if(e.getSource()==jb5) {
                drawFlag = "曲线";
                // System.out.println(drawFlag);
            }else if(e.getSource()==jb6) {
                drawFlag = "橡皮";
            }else if(e.getSource()==jb7) {
                drawFlag = "实圆";
            }else if(e.getSource()==jb8) {
                drawFlag = "实方";
            }
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            ((JButton) e.getSource()).setBorder(BorderFactory.createRaisedBevelBorder());
            ((JButton) e.getSource()).setOpaque(true);
        }
        @Override
        public void mouseExited(MouseEvent e) {
            ((JButton)e.getSource()).setBorder(null);
            ((JButton) e.getSource()).setOpaque(false);
        }
    }
    class Point{
        private int x1,x2,y1,y2;
        private Color color;
        float W;
        String name;
        public Point(int x1,int y1,int x2,int y2,Color color, String name,float Width) {
            this.x1=x1;
            this.y1=y1;
            this.x2=x2;
            this.y2=y2;
            this.color=color;
            this.name = name;
            this.W = Width;
        }
        public int getX1() {
            return x1;
        }
    
        public void setX1(int x1) {
            this.x1 = x1;
        }
    
        public int getX2() {
            return x2;
        }
    
        public void setX2(int x2) {
            this.x2 = x2;
        }
    
        public int getY1() {
            return y1;
        }
    
        public void setY1(int y1) {
            this.y1 = y1;
        }
    
        public int getY2() {
            return y2;
        }
    
        public void setY2(int y2) {
            this.y2 = y2;
        }
        public Color getColor() {
            return color;
        }
        public void setColor(Color color) {
            this.color = color;
        }
    }
    public void set_backround(JButton jb, ImageIcon icon){
        jb.setOpaque(false);
        //去掉背景点击效果
        jb.setContentAreaFilled(false);  
        //去掉聚焦线
        jb.setFocusPainted(false);
        //去掉边框
        jb.setBorder(null); 
        //设置显示的图片
        // icon.
        jb.setIcon(icon);
    }
}
