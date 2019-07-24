package com.zuikaku.view;

import com.zuikaku.controller.LoginListener;

import javax.swing.*;

public class LoginPanel extends JPanel {
    private JLabel usernameJLabel;
    private JLabel passwordJLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JButton loginBtn;
    private JButton adminEnterBtn;
    private Box usernameBox;
    private Box passwordBox;
    private Box buttonBox;
    private Box bigBox;

    private LoginListener loginListener;
    public LoginPanel(){
        //GUI部分
        usernameJLabel=new JLabel("用户名");
        passwordJLabel=new JLabel("密码");
        usernameTextField=new JTextField(20);
        passwordTextField=new JPasswordField(20);
        loginBtn =new JButton("登录");
        adminEnterBtn=new JButton("管理员入口");
        loginBtn.setActionCommand("login");
        adminEnterBtn.setActionCommand("admin");
        //创建盒子
        usernameBox=Box.createHorizontalBox();
        passwordBox=Box.createHorizontalBox();
        buttonBox=Box.createHorizontalBox();
        bigBox=Box.createVerticalBox();
        usernameBox.add(usernameJLabel);
        usernameBox.add(Box.createHorizontalStrut(10));
        usernameBox.add(usernameTextField);
        passwordBox.add(passwordJLabel);
        passwordBox.add(Box.createHorizontalStrut(5));
        passwordBox.add(passwordTextField);
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add(loginBtn);
        buttonBox.add(Box.createHorizontalStrut(10));
        buttonBox.add(adminEnterBtn);
        //大盒子加入
        bigBox.add(usernameBox);
        bigBox.add(Box.createVerticalStrut(10));
        bigBox.add(passwordBox);
        bigBox.add(Box.createVerticalStrut(10));
        bigBox.add(buttonBox);
        add(bigBox);
        setVisible(true);

        //设置监听器
        loginListener=new LoginListener();
        loginListener.setUsernameTextField(usernameTextField);
        loginListener.setPasswordField(passwordTextField);
        //添加监听器
        loginBtn.addActionListener(loginListener);
        adminEnterBtn.addActionListener(loginListener);
    }
}
