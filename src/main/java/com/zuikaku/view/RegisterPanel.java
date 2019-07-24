package com.zuikaku.view;

import com.zuikaku.controller.RegisterListener;

import javax.swing.*;
import java.awt.event.ActionListener;

public class RegisterPanel extends JPanel {
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel repeatepwdLabel;
    private JLabel sexLabel;
    private JLabel phoneLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JPasswordField repeatePwdTextField;
    private JTextField sexTextField;
    private JTextField phoneTextField;
    private JButton registerBtn;
    private JButton cancelBtn;
    private Box userInputBox;//垂直分布box
    private Box usernameBox;//水平分布BOX,用户名的Label与TextField
    private Box passwordBox;//水平分布BOX,密码的Label与TextField
    private Box repeatePasswordBox;//水平分布BOX,重复密码的Label与TextField
    private Box sexBox;
    private Box phoneBox;
    private Box buttonBox;//水平BOX，两个按钮
    //监听器
    private RegisterListener registerListener;

    public RegisterPanel(){
        //GUI部分
        usernameLabel=new JLabel("用户名");
        passwordLabel=new JLabel("密码");
        repeatepwdLabel=new JLabel("再次输入");
        sexLabel=new JLabel("性别");
        phoneLabel=new JLabel("电话号码");
        usernameTextField=new JTextField(20);
        passwordTextField=new JPasswordField(20);
        repeatePwdTextField=new JPasswordField(20);
        sexTextField=new JTextField(5);
        phoneTextField=new JTextField(20);
        registerBtn=new JButton("注册");
        cancelBtn=new JButton("取消");
        registerBtn.setActionCommand("register");//设置command，以便listener分辨具体按钮点击
        cancelBtn.setActionCommand("cancel");//同上
        //创盒子
        userInputBox=Box.createVerticalBox();
        usernameBox=Box.createHorizontalBox();
        passwordBox=Box.createHorizontalBox();
        repeatePasswordBox=Box.createHorizontalBox();
        buttonBox=Box.createHorizontalBox();
        sexBox=Box.createHorizontalBox();
        phoneBox=Box.createHorizontalBox();
        //将这些组件加到对应BOX中
        usernameBox.add(usernameLabel);
        usernameBox.add(Box.createHorizontalStrut(10));
        usernameBox.add(usernameTextField);
        passwordBox.add(passwordLabel);
        passwordBox.add(Box.createHorizontalStrut(15));
        passwordBox.add(passwordTextField);
        repeatePasswordBox.add(repeatepwdLabel);
        repeatePasswordBox.add(Box.createHorizontalStrut(5));
        repeatePasswordBox.add(repeatePwdTextField);
        sexBox.add(sexLabel);
        sexBox.add(Box.createHorizontalStrut(5));
        sexBox.add(sexTextField);
        phoneBox.add(phoneLabel);
        phoneBox.add(Box.createHorizontalStrut(5));
        phoneBox.add(phoneTextField);
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add(registerBtn);
        buttonBox.add(Box.createHorizontalStrut(20));
        buttonBox.add(cancelBtn);
        //把小盒子加到大盒子中
        userInputBox.add(usernameBox);
        userInputBox.add(Box.createVerticalStrut(10));
        userInputBox.add(passwordBox);
        userInputBox.add(Box.createVerticalStrut(10));
        userInputBox.add(repeatePasswordBox);
        userInputBox.add(Box.createVerticalStrut(10));
        userInputBox.add(sexBox);
        userInputBox.add(Box.createVerticalStrut(10));
        userInputBox.add(phoneBox);
        userInputBox.add(Box.createVerticalStrut(10));
        userInputBox.add(buttonBox);
        //大盒子加到页面中
        add(userInputBox);
        setVisible(true);
        validate();

        //设置监听器
        registerListener =new RegisterListener();
        registerListener.setUsernameField(usernameTextField);
        registerListener.setPasswordField(passwordTextField);
        registerListener.setRepeatePasswordField(repeatePwdTextField);
        registerListener.setSexField(sexTextField);
        registerListener.setPhoneField(phoneTextField);
        //添加监听器
        registerBtn.addActionListener(registerListener);
        cancelBtn.addActionListener(registerListener);
    }
}
