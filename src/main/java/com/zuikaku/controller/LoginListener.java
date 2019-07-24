package com.zuikaku.controller;

import com.zuikaku.UserManager;
import com.zuikaku.dao.UserDao;
import com.zuikaku.pojo.User;
import com.zuikaku.utils.C3P0DataSource;
import com.zuikaku.view.AdminPanel;
import com.zuikaku.view.UserPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class LoginListener implements ActionListener {
    private JTextField usernameTextField;
    private JPasswordField passwordField;

    public void setUsernameTextField(JTextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public void actionPerformed(ActionEvent e) {
        Connection conn= C3P0DataSource.getConnection();
        if(e.getActionCommand().equals("login"))
        {
            // 用户登录逻辑
            //前台校验
            if(usernameTextField.getText().equals("")||new String(passwordField.getPassword()).equals(""))
            {
                JOptionPane.showMessageDialog(null,"账号或密码为空","消息",JOptionPane.INFORMATION_MESSAGE);
            }else {
                User user= UserDao.login(conn,usernameTextField.getText(),new String(passwordField.getPassword()));
                if(user!=null)
                {
                    //打开对话框
                    JOptionPane.showMessageDialog(null,"登录成功,欢迎："+user.getName(),"登录结果",JOptionPane.INFORMATION_MESSAGE);
                    UserManager.getInstance().setLoginedUser(user);//设置到UserManager中
                    // 打开用户主页
                    new UserPanel();
                }else{
                    JOptionPane.showMessageDialog(null,"登录失败","登录结构",JOptionPane.ERROR_MESSAGE);
                }
            }

        }else if(e.getActionCommand().equals("admin")) {
            // 管理员管理逻辑
            String input= JOptionPane.showInputDialog(null,"请输入管理员码进行管理图书");
            if(input.equals("admin"))
            {
                // 打开管理员面板
                new AdminPanel();
            }else {
                JOptionPane.showMessageDialog(null,"管理员码错误","结果",JOptionPane.ERROR_MESSAGE);
            }
        }
        //归还数据库连接
        C3P0DataSource.releaseConnection(conn);
    }
}
