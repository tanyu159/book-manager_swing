package com.zuikaku.controller;

import com.zuikaku.dao.UserDao;
import com.zuikaku.pojo.User;
import com.zuikaku.utils.C3P0DataSource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Date;

public class RegisterListener implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField repeatePasswordField;
    private JTextField sexField;
    private JTextField phoneField;


    public void setSexField(JTextField sexField) {
        this.sexField = sexField;
    }

    public void setPhoneField(JTextField phoneField) {
        this.phoneField = phoneField;
    }

    public void setUsernameField(JTextField usernameField) {
        this.usernameField = usernameField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public void setRepeatePasswordField(JPasswordField repeatePasswordField) {
        this.repeatePasswordField = repeatePasswordField;
    }

    public void actionPerformed(ActionEvent e) {
        //获取数据库连接
        Connection conn= C3P0DataSource.getConnection();
        if(e.getActionCommand().equals("cancel")){
            usernameField.setText("");
            passwordField.setText("");
            repeatePasswordField.setText("");
            sexField.setText("");
            phoneField.setText("");
            JOptionPane.showMessageDialog(null,"用户已取消","操作结果",JOptionPane.INFORMATION_MESSAGE);
        }else if(e.getActionCommand().equals("register"))
        {
            if(! new String(passwordField.getPassword()).equals(new String(repeatePasswordField.getPassword())))
            {
                JOptionPane.showMessageDialog(null,"两次密码不同","操作结果",JOptionPane.INFORMATION_MESSAGE);
            }else{
                User user=new User(usernameField.getText(),new String(passwordField.getPassword()),sexField.getText(),phoneField.getText(),new Date());
                boolean isOk= UserDao.register(conn,user);
                if(isOk)
                {
                    JOptionPane.showMessageDialog(null,"注册成功","操作结果",JOptionPane.INFORMATION_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(null,"注册失败","操作结果",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        //归还数据库连接
        C3P0DataSource.releaseConnection(conn);

    }
}
