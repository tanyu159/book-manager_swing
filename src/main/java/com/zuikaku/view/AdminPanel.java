package com.zuikaku.view;

import javax.swing.*;
import java.awt.*;

/**
 *管理员管理界面：查看所有图书，添加图书，删除图书，修改图书信息，查看用户，删除用户
 */
public class AdminPanel extends JFrame {
    private JLabel tile;
    private JTabbedPane tabbedPane;
    private ManagerBookPanel managerBookPanel;
    private ManagerUserPanel managerUserPanel;

    public AdminPanel(){
        setTitle("后台管理");
        tile=new JLabel("后台管理系统");
        setResizable(false);
        tabbedPane=new JTabbedPane();
        managerBookPanel=new ManagerBookPanel();
        managerUserPanel=new ManagerUserPanel();
        tabbedPane.add("图书管理",managerBookPanel);
        tabbedPane.add("用户管理",managerUserPanel);
        add(tile,BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
        setBounds(500,500,800,600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
