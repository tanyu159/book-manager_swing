package com.zuikaku.view;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

/**
 * 进入系统的主面板
 */
public class MainPanel extends JFrame {
    private JLabel tile;
    private JTabbedPane tabbedPane;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;

    public MainPanel(){
        //设置布局
        setResizable(false);
        setTitle("图书管理系统");
        setLayout(new BorderLayout());
        loginPanel=new LoginPanel();
        registerPanel=new RegisterPanel();
        tabbedPane=new JTabbedPane();
        tile=new JLabel("图书管理系统");
        add(tile,BorderLayout.NORTH);
        tabbedPane.add("注册",registerPanel);
        tabbedPane.add("登录",loginPanel);
        add(tabbedPane,BorderLayout.CENTER);
        setVisible(true);
        setResizable(false);
        int windowWidth = getWidth(); //获得窗口宽
        int windowHeight = getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        setBounds((screenWidth/2-windowWidth/2)-150, (screenHeight/2-windowHeight/2)-75,600,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
